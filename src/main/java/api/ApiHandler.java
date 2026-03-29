package api;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import modelo.*;
import servico.PlataformaRecomendacao;

public class ApiHandler {

    private static PlataformaRecomendacao plataforma;

    public static void main(String[] args) {
        if (args.length < 1) {
            out("{\"error\":\"Comando nao fornecido\"}");
            return;
        }

        PrintStream original = System.out;
        System.setOut(new PrintStream(new OutputStream() { public void write(int b) {} }));
        plataforma = new PlataformaRecomendacao();
        System.setOut(original);

        String comando = args[0];
        String json = args.length > 1 ? args[1] : "{}";

        try {
            switch (comando) {
                case "cadastro":       cadastro(json); break;
                case "login":          login(json); break;
                case "filmes":         filmes(); break;
                case "recomendados":   recomendados(json); break;
                case "preferencias_get": preferenciasGet(json); break;
                case "preferencias_set": preferenciasSet(json); break;
                default: out("{\"error\":\"Comando desconhecido\"}");
            }
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Erro interno";
            out("{\"error\":\"" + esc(msg) + "\"}");
        }
    }

    private static void cadastro(String json) throws Exception {
        String nome = str(json, "nome");
        String email = str(json, "email");
        String senha = str(json, "senha");
        int idade = num(json, "idade");

        Usuario u = plataforma.cadastrarUsuario(nome, email, senha, idade);
        out("{\"nome\":\"" + esc(u.getNome()) + "\","
            + "\"email\":\"" + esc(u.getEmail()) + "\","
            + "\"idade\":" + u.getIdade() + "}");
    }

    private static void login(String json) throws Exception {
        String email = str(json, "email");
        String senha = str(json, "senha");

        Usuario u = plataforma.login(email, senha);
        out("{\"nome\":\"" + esc(u.getNome()) + "\","
            + "\"email\":\"" + esc(u.getEmail()) + "\","
            + "\"idade\":" + u.getIdade() + ","
            + "\"duracao_minima\":" + u.getDuracaoMinima() + ","
            + "\"duracao_maxima\":" + u.getDuracaoMaxima() + ","
            + "\"generos\":" + toJsonArray(u.getGenerosPreferidos()) + ","
            + "\"streamings\":" + toStreamingArray(u.getStreamingsAssinados()) + "}");
    }

    private static void filmes() {
        out(filmesToJson(plataforma.listarTodos()));
    }

    private static void recomendados(String json) throws Exception {
        String email = str(json, "email");
        String senha = str(json, "senha");
        Usuario u = plataforma.login(email, senha);
        out(filmesToJson(plataforma.recomendar(u)));
    }

    private static void preferenciasGet(String json) throws Exception {
        String email = str(json, "email");
        String senha = str(json, "senha");
        Usuario u = plataforma.login(email, senha);

        out("{\"generos\":" + toJsonArray(u.getGenerosPreferidos()) + ","
            + "\"streamings\":" + toStreamingArray(u.getStreamingsAssinados()) + ","
            + "\"duracao_minima\":" + u.getDuracaoMinima() + ","
            + "\"duracao_maxima\":" + u.getDuracaoMaxima() + "}");
    }

    private static void preferenciasSet(String json) throws Exception {
        String email = str(json, "email");
        String senha = str(json, "senha");
        Usuario u = plataforma.login(email, senha);

        String generosArr = arr(json, "generos");
        if (generosArr != null) {
            u.getGenerosPreferidos().clear();
            for (Genero g : Genero.values()) {
                if (generosArr.contains("\"" + g.name() + "\"")) {
                    u.adicionarGenero(g);
                }
            }
        }

        String streamingsArr = arr(json, "streamings");
        if (streamingsArr != null) {
            u.getStreamingsAssinados().clear();
            for (Streaming s : Streaming.values()) {
                if (streamingsArr.contains("\"" + s.name() + "\"")) {
                    u.adicionarStreaming(s);
                }
            }
        }

        int durMin = num(json, "duracao_min");
        int durMax = num(json, "duracao_max");
        if (durMin > 0) u.setDuracaoMinima(durMin);
        if (durMax > 0) u.setDuracaoMaxima(durMax);

        plataforma.salvarPreferencias(u);
        out("{\"ok\":true}");
    }

    // --- JSON builders ---

    private static String filmesToJson(ArrayList<Filme> filmes) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < filmes.size(); i++) {
            if (i > 0) sb.append(",");
            Filme f = filmes.get(i);
            sb.append("{\"id\":").append(f.getId())
              .append(",\"titulo\":\"").append(esc(f.getTitulo())).append("\"")
              .append(",\"ano\":").append(f.getAnoLancamento())
              .append(",\"duracao\":").append(f.getDuracaoMinutos())
              .append(",\"duracao_formatada\":\"").append(esc(f.getDuracaoFormatada())).append("\"")
              .append(",\"sinopse\":\"").append(esc(f.getSinopse())).append("\"")
              .append(",\"faixa_etaria\":\"").append(f.getFaixaEtaria().name()).append("\"")
              .append(",\"faixa_etaria_minima\":").append(f.getFaixaEtaria().getIdadeMinima())
              .append(",\"genero\":\"").append(f.getGenero().name()).append("\"")
              .append(",\"genero_descricao\":\"").append(esc(f.getGenero().getDescricao())).append("\"")
              .append(",\"nota_imdb\":").append(f.getNotaIMDB())
              .append(",\"oscar_indicacoes\":").append(f.getOscarIndicacoes())
              .append(",\"oscar_vitorias\":").append(f.getOscarVitorias())
              .append(",\"streamings\":[");
            ArrayList<Streaming> streams = f.getStreamings();
            for (int j = 0; j < streams.size(); j++) {
                if (j > 0) sb.append(",");
                sb.append("\"").append(streams.get(j).name()).append("\"");
            }
            sb.append("]}");
        }
        sb.append("]");
        return sb.toString();
    }

    private static <E extends Enum<E>> String toJsonArray(ArrayList<E> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(list.get(i).name()).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String toStreamingArray(ArrayList<Streaming> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(list.get(i).name()).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    // --- Minimal JSON parsing ---

    private static String str(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return "";
        int colon = json.indexOf(":", idx + pattern.length());
        if (colon < 0) return "";
        int start = colon + 1;
        while (start < json.length() && json.charAt(start) == ' ') start++;
        if (start >= json.length() || json.charAt(start) != '"') return "";
        start++;
        int end = start;
        while (end < json.length() && json.charAt(end) != '"') {
            if (json.charAt(end) == '\\') end++;
            end++;
        }
        return json.substring(start, end);
    }

    private static int num(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return 0;
        int colon = json.indexOf(":", idx + pattern.length());
        if (colon < 0) return 0;
        int start = colon + 1;
        while (start < json.length() && json.charAt(start) == ' ') start++;
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
        if (start >= end) return 0;
        return Integer.parseInt(json.substring(start, end));
    }

    private static String arr(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int open = json.indexOf("[", idx);
        if (open < 0) return null;
        int close = json.indexOf("]", open);
        if (close < 0) return null;
        return json.substring(open, close + 1);
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static void out(String s) {
        System.out.println(s);
    }
}
