package infra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import modelo.Genero;
import modelo.Streaming;

// Cliente HTTP para comunicação com Supabase (Auth + PostgREST)
// Usa java.net.http.HttpClient (Java 11+) — sem dependências externas
public class SupabaseClient {
    private final HttpClient http = HttpClient.newHttpClient();
    private final String baseUrl = SupabaseConfig.getUrl();
    private final String anonKey = SupabaseConfig.getAnonKey();

    // ===================== AUTH =====================

    /**
     * Cadastra via Supabase Auth.
     * @return [0] = authId (UUID), [1] = accessToken (null se confirmação de email estiver ativa)
     */
    public String[] cadastrar(String email, String senha) throws Exception {
        String json = "{\"email\":\"" + esc(email) + "\","
                + "\"password\":\"" + esc(senha) + "\"}";

        System.err.println("[DEBUG] POST " + baseUrl + "/auth/v1/signup");
        System.err.println("[DEBUG] Body: " + json);

        HttpResponse<String> res = enviar("POST", baseUrl + "/auth/v1/signup", json, null);
        validar(res, "Erro no cadastro");

        String body = res.body();
        String token = extrairTexto(body, "access_token");
        String authId = extrairTextoDe(body, "user", "id");
        if (authId == null) {
            authId = extrairTexto(body, "id");
        }
        return new String[]{authId, token};
    }

    /**
     * Cria o perfil na tabela usuarios via PostgREST (substitui o trigger).
     */
    public void criarPerfil(String token, String authId, String nome, String email, int idade) throws Exception {
        String json = "{\"auth_id\":\"" + authId + "\","
                + "\"nome\":\"" + esc(nome) + "\","
                + "\"email\":\"" + esc(email) + "\","
                + "\"idade\":" + idade + "}";

        HttpResponse<String> res = enviar("POST", baseUrl + "/rest/v1/usuarios", json, token);

        if (res.statusCode() >= 400) {
            // Ignora conflito (perfil pode já existir)
            if (res.statusCode() != 409 && !res.body().contains("duplicate")) {
                throw new Exception("Erro ao criar perfil: " + res.body());
            }
        }
    }

    /**
     * Login via Supabase Auth.
     * @return [0] = authId, [1] = accessToken
     */
    public String[] login(String email, String senha) throws Exception {
        String json = "{\"email\":\"" + esc(email) + "\","
                + "\"password\":\"" + esc(senha) + "\"}";

        HttpResponse<String> res = enviar("POST",
                baseUrl + "/auth/v1/token?grant_type=password", json, null);
        validar(res, "Email ou senha inválidos");

        String body = res.body();
        String token = extrairTexto(body, "access_token");
        String authId = extrairTextoDe(body, "user", "id");
        return new String[]{authId, token};
    }

    // ===================== PERFIL =====================

    /**
     * Busca o perfil do usuário autenticado (RLS filtra pelo token).
     * @return [id, nome, email, idade, duracao_minima, duracao_maxima] ou null
     */
    public String[] carregarPerfil(String token) throws Exception {
        HttpResponse<String> res = enviar("GET",
                baseUrl + "/rest/v1/usuarios?select=id,nome,email,idade,duracao_minima,duracao_maxima&limit=1",
                null, token);

        String body = res.body();
        if (res.statusCode() >= 400 || body.equals("[]")) return null;

        return new String[]{
            extrairTexto(body, "id"),
            extrairTexto(body, "nome"),
            extrairTexto(body, "email"),
            extrairNumero(body, "idade"),
            extrairNumero(body, "duracao_minima"),
            extrairNumero(body, "duracao_maxima")
        };
    }

    public void atualizarDuracao(String token, String usuarioId, int min, int max) throws Exception {
        String json = "{\"duracao_minima\":" + min + ",\"duracao_maxima\":" + max + "}";
        enviar("PATCH", baseUrl + "/rest/v1/usuarios?id=eq." + usuarioId, json, token);
    }

    // ===================== PREFERÊNCIAS =====================

    public ArrayList<Genero> carregarGeneros(String token, String usuarioId) throws Exception {
        HttpResponse<String> res = enviar("GET",
                baseUrl + "/rest/v1/usuario_generos?usuario_id=eq." + usuarioId + "&select=genero",
                null, token);

        ArrayList<Genero> lista = new ArrayList<>();
        if (res.statusCode() < 400) {
            String body = res.body();
            for (Genero g : Genero.values()) {
                if (body.contains("\"" + g.name() + "\"")) {
                    lista.add(g);
                }
            }
        }
        return lista;
    }

    public ArrayList<Streaming> carregarStreamings(String token, String usuarioId) throws Exception {
        HttpResponse<String> res = enviar("GET",
                baseUrl + "/rest/v1/usuario_streamings?usuario_id=eq." + usuarioId + "&select=streaming",
                null, token);

        ArrayList<Streaming> lista = new ArrayList<>();
        if (res.statusCode() < 400) {
            String body = res.body();
            for (Streaming s : Streaming.values()) {
                if (body.contains("\"" + s.name() + "\"")) {
                    lista.add(s);
                }
            }
        }
        return lista;
    }

    public void salvarGeneros(String token, String usuarioId, ArrayList<Genero> generos) throws Exception {
        enviar("DELETE",
                baseUrl + "/rest/v1/usuario_generos?usuario_id=eq." + usuarioId, null, token);

        if (!generos.isEmpty()) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < generos.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append("{\"usuario_id\":\"").append(usuarioId)
                  .append("\",\"genero\":\"").append(generos.get(i).name()).append("\"}");
            }
            sb.append("]");
            enviar("POST", baseUrl + "/rest/v1/usuario_generos", sb.toString(), token);
        }
    }

    public void salvarStreamings(String token, String usuarioId, ArrayList<Streaming> streamings) throws Exception {
        enviar("DELETE",
                baseUrl + "/rest/v1/usuario_streamings?usuario_id=eq." + usuarioId, null, token);

        if (!streamings.isEmpty()) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < streamings.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append("{\"usuario_id\":\"").append(usuarioId)
                  .append("\",\"streaming\":\"").append(streamings.get(i).name()).append("\"}");
            }
            sb.append("]");
            enviar("POST", baseUrl + "/rest/v1/usuario_streamings", sb.toString(), token);
        }
    }

    // ===================== HTTP =====================

    private HttpResponse<String> enviar(String method, String url, String body, String token)
            throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", anonKey)
                .header("Content-Type", "application/json");

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }

        switch (method) {
            case "GET":    builder.GET(); break;
            case "DELETE": builder.DELETE(); break;
            case "POST":
                builder.POST(HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
                break;
            case "PATCH":
                builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
                break;
        }

        return http.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private void validar(HttpResponse<String> res, String mensagemPadrao) throws Exception {
        if (res.statusCode() < 400) return;

        // Debug temporário — mostra a resposta completa do Supabase
        System.err.println("[DEBUG] HTTP " + res.statusCode() + ": " + res.body());

        String msg = extrairTexto(res.body(), "msg");
        if (msg == null) msg = extrairTexto(res.body(), "error_description");
        if (msg == null) msg = extrairTexto(res.body(), "message");
        throw new Exception(msg != null ? msg : mensagemPadrao);
    }

    // ===================== JSON (parsing mínimo sem libs) =====================

    private static String extrairTexto(String json, String chave) {
        String padrao = "\"" + chave + "\"";
        int idx = json.indexOf(padrao);
        if (idx < 0) return null;

        int posColon = json.indexOf(":", idx + padrao.length());
        if (posColon < 0) return null;

        int start = posColon + 1;
        while (start < json.length() && json.charAt(start) == ' ') start++;
        if (start >= json.length() || json.charAt(start) != '"') return null;
        start++;

        int end = start;
        while (end < json.length() && json.charAt(end) != '"') {
            if (json.charAt(end) == '\\') end++;
            end++;
        }
        return json.substring(start, end);
    }

    // Extrai campo de dentro de um objeto aninhado: "objeto":{..."chave":"valor"...}
    private static String extrairTextoDe(String json, String objeto, String chave) {
        String padrao = "\"" + objeto + "\"";
        int objIdx = json.indexOf(padrao);
        if (objIdx < 0) return null;

        int braceStart = json.indexOf("{", objIdx + padrao.length());
        if (braceStart < 0) return null;

        int depth = 1;
        int pos = braceStart + 1;
        while (pos < json.length() && depth > 0) {
            char c = json.charAt(pos);
            if (c == '{') depth++;
            else if (c == '}') depth--;
            else if (c == '"') {
                pos++;
                while (pos < json.length() && json.charAt(pos) != '"') {
                    if (json.charAt(pos) == '\\') pos++;
                    pos++;
                }
            }
            pos++;
        }
        return extrairTexto(json.substring(braceStart, pos), chave);
    }

    private static String extrairNumero(String json, String chave) {
        String padrao = "\"" + chave + "\"";
        int idx = json.indexOf(padrao);
        if (idx < 0) return null;

        int posColon = json.indexOf(":", idx + padrao.length());
        if (posColon < 0) return null;

        int start = posColon + 1;
        while (start < json.length() && json.charAt(start) == ' ') start++;

        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end))
                || json.charAt(end) == '-' || json.charAt(end) == '.')) {
            end++;
        }
        return start < end ? json.substring(start, end) : null;
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
