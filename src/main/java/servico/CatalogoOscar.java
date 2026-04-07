package servico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import modelo.*;

// Lê o catálogo de filmes a partir do arquivo filmes.json
// Demonstra polimorfismo: instancia a subclasse correta conforme o 1o gênero de cada filme
public class CatalogoOscar {

    public static ArrayList<Filme> getFilmes() {
        ArrayList<Filme> filmes = new ArrayList<>();
        try {
            String json = lerArquivo("filmes.json");
            parsarFilmes(json, filmes);
        } catch (Exception e) {
            System.err.println("[CatalogoOscar] Erro ao ler filmes.json: " + e.getMessage());
        }
        return filmes;
    }

    private static String lerArquivo(String caminho) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append("\n");
            }
        }
        return sb.toString();
    }

    private static void parsarFilmes(String json, ArrayList<Filme> filmes) {
        int id = 1;
        String oscarAnoAtual = null;
        int pos = 0;

        while (pos < json.length()) {
            int aspas = json.indexOf('"', pos);
            if (aspas < 0) break;
            int fimChave = json.indexOf('"', aspas + 1);
            if (fimChave < 0) break;
            String chave = json.substring(aspas + 1, fimChave);
            pos = fimChave + 1;

            if (chave.startsWith("Oscar ")) {
                oscarAnoAtual = chave;
                continue;
            }

            if (oscarAnoAtual == null) continue;

            // Encontra o bloco { ... } desse filme
            int abreBloco = json.indexOf('{', pos);
            if (abreBloco < 0) break;
            int fechaBloco = encontrarFechamento(json, abreBloco);
            if (fechaBloco < 0) break;
            String bloco = json.substring(abreBloco, fechaBloco + 1);
            pos = fechaBloco + 1;

            // Verifica se é um bloco de filme (contém "genero")
            if (!bloco.contains("\"genero\"")) continue;

            Filme f = parsarFilme(id++, chave, bloco, oscarAnoAtual);
            if (f != null) filmes.add(f);
        }
    }

    private static Filme parsarFilme(int id, String titulo, String bloco, String oscarAno) {
        ArrayList<String> generos = extrairArray(bloco, "genero");
        String classificacao = extrairString(bloco, "classificacao_indicativa");
        String duracaoStr = extrairString(bloco, "duracao");
        ArrayList<String> streamAssinatura = extrairArray(bloco, "streaming_assinatura");
        ArrayList<String> streamCompra = extrairArray(bloco, "streaming_compra_aluguel");

        if (generos.isEmpty()) return null;

        int duracaoMinutos = parsarDuracao(duracaoStr);
        FaixaEtaria faixa = FaixaEtaria.fromTexto(classificacao);

        Filme filme = criarFilmePorGenero(id, titulo, duracaoMinutos, faixa, oscarAno, generos.get(0));
        if (filme == null) return null;

        for (String g : generos) {
            Genero genero = Genero.fromDescricao(g);
            if (genero != null) filme.adicionarGenero(genero);
        }

        for (String s : streamAssinatura) {
            Streaming st = Streaming.fromNome(s);
            if (st != null) filme.adicionarStreaming(st);
        }
        for (String s : streamCompra) {
            Streaming st = Streaming.fromNome(s);
            if (st != null) filme.adicionarStreaming(st);
        }

        return filme;
    }

    // Polimorfismo — instancia a subclasse correta de acordo com o gênero primário
    private static Filme criarFilmePorGenero(int id, String titulo, int duracao,
                                              FaixaEtaria faixa, String oscarAno, String generoStr) {
        Genero g = Genero.fromDescricao(generoStr);
        if (g == null) return null;

        switch (g) {
            case DRAMA:              return new FilmeDrama(id, titulo, duracao, faixa, oscarAno);
            case COMEDIA:            return new FilmeComedia(id, titulo, duracao, faixa, oscarAno);
            case ACAO:               return new FilmeAcao(id, titulo, duracao, faixa, oscarAno);
            case ANIMACAO:           return new FilmeAnimacao(id, titulo, duracao, faixa, oscarAno);
            case TERROR:             return new FilmeTerror(id, titulo, duracao, faixa, oscarAno);
            case ROMANCE:            return new FilmeRomance(id, titulo, duracao, faixa, oscarAno);
            case FICCAO_CIENTIFICA:  return new FilmeFiccaoCientifica(id, titulo, duracao, faixa, oscarAno);
            case THRILLER:           return new FilmeThriller(id, titulo, duracao, faixa, oscarAno);
            case DOCUMENTARIO:       return new FilmeDocumentario(id, titulo, duracao, faixa, oscarAno);
            case MUSICAL:            return new FilmeMusical(id, titulo, duracao, faixa, oscarAno);
            case CRIME:              return new FilmeCrime(id, titulo, duracao, faixa, oscarAno);
            case SUSPENSE:           return new FilmeSuspense(id, titulo, duracao, faixa, oscarAno);
            case GUERRA:             return new FilmeGuerra(id, titulo, duracao, faixa, oscarAno);
            default:                 return new FilmeDrama(id, titulo, duracao, faixa, oscarAno);
        }
    }

    // Converte "2h 17" ou "1h 59" ou "2h" para minutos
    private static int parsarDuracao(String s) {
        if (s == null || s.isEmpty()) return 0;
        int total = 0;
        s = s.trim().toLowerCase();
        if (s.contains("h")) {
            String[] partes = s.split("h");
            total += Integer.parseInt(partes[0].trim()) * 60;
            if (partes.length > 1 && !partes[1].trim().isEmpty()) {
                total += Integer.parseInt(partes[1].trim());
            }
        } else {
            total = Integer.parseInt(s.trim());
        }
        return total;
    }

    // --- Minimal JSON parsing helpers ---

    private static String extrairString(String json, String chave) {
        String padrao = "\"" + chave + "\"";
        int idx = json.indexOf(padrao);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + padrao.length());
        if (colon < 0) return null;
        int inicio = colon + 1;
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) inicio++;
        if (inicio >= json.length()) return null;

        if (json.substring(inicio).trim().startsWith("null")) return null;

        if (json.charAt(inicio) != '"') return null;
        inicio++;
        int fim = json.indexOf('"', inicio);
        if (fim < 0) return null;
        return json.substring(inicio, fim);
    }

    private static ArrayList<String> extrairArray(String json, String chave) {
        ArrayList<String> resultado = new ArrayList<>();
        String padrao = "\"" + chave + "\"";
        int idx = json.indexOf(padrao);
        if (idx < 0) return resultado;
        int abre = json.indexOf('[', idx);
        if (abre < 0) return resultado;
        int fecha = json.indexOf(']', abre);
        if (fecha < 0) return resultado;

        String conteudo = json.substring(abre + 1, fecha);
        int pos = 0;
        while (pos < conteudo.length()) {
            int aspasInicio = conteudo.indexOf('"', pos);
            if (aspasInicio < 0) break;
            int aspasFim = conteudo.indexOf('"', aspasInicio + 1);
            if (aspasFim < 0) break;
            resultado.add(conteudo.substring(aspasInicio + 1, aspasFim));
            pos = aspasFim + 1;
        }
        return resultado;
    }

    private static int encontrarFechamento(String json, int abre) {
        int nivel = 0;
        for (int i = abre; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') nivel++;
            else if (c == '}') {
                nivel--;
                if (nivel == 0) return i;
            }
        }
        return -1;
    }
}
