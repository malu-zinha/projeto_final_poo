package servico;

import java.util.ArrayList;
import java.util.Comparator;
import modelo.Filme;
import modelo.Genero;
import modelo.Streaming;
import modelo.Usuario;

public class SistemaRecomendacao {

    private static final int PESO_GENERO = 3;
    private static final int PESO_STREAMING = 2;
    private static final int PESO_DURACAO = 2;
    private static final int PESO_FAIXA_ETARIA = 1;

    public ArrayList<Filme> recomendar(Usuario u, ArrayList<Filme> catalogo) {
        ArrayList<Filme> filtrados = filtrarPorFaixaEtaria(catalogo, u.getIdade());

        ArrayList<Filme> resultado = filtrarPorGenero(filtrados, u.getGenerosPreferidos());
        resultado = filtrarPorDuracao(resultado, u.getDuracaoMinima(), u.getDuracaoMaxima());
        resultado = filtrarPorStreaming(resultado, u.getStreamingsAssinados());

        if (resultado.isEmpty()) {
            resultado = filtrados;
        }

        resultado.sort(Comparator.comparingInt((Filme f) -> calcularScore(f, u)).reversed());
        return resultado;
    }

    private int calcularScore(Filme f, Usuario u) {
        int score = 0;

        for (Genero g : f.getGeneros()) {
            if (u.getGenerosPreferidos().contains(g)) {
                score += PESO_GENERO;
                break;
            }
        }

        for (Streaming s : u.getStreamingsAssinados()) {
            if (f.contemStreaming(s)) {
                score += PESO_STREAMING;
                break;
            }
        }

        int duracao = f.getDuracaoMinutos();
        if (duracao >= u.getDuracaoMinima() && duracao <= u.getDuracaoMaxima()) {
            score += PESO_DURACAO;
        }

        if (f.isAdequadoParaIdade(u.getIdade())) {
            score += PESO_FAIXA_ETARIA;
        }

        return score;
    }

    private ArrayList<Filme> filtrarPorGenero(ArrayList<Filme> filmes, ArrayList<Genero> generos) {
        if (generos.isEmpty()) return new ArrayList<>(filmes);
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            for (Genero g : f.getGeneros()) {
                if (generos.contains(g)) {
                    resultado.add(f);
                    break;
                }
            }
        }
        return resultado;
    }

    private ArrayList<Filme> filtrarPorDuracao(ArrayList<Filme> filmes, int min, int max) {
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            if (f.getDuracaoMinutos() >= min && f.getDuracaoMinutos() <= max) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    private ArrayList<Filme> filtrarPorFaixaEtaria(ArrayList<Filme> filmes, int idadeUsuario) {
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            if (f.isAdequadoParaIdade(idadeUsuario)) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    private ArrayList<Filme> filtrarPorStreaming(ArrayList<Filme> filmes, ArrayList<Streaming> streamings) {
        if (streamings.isEmpty()) return new ArrayList<>(filmes);
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            for (Streaming s : streamings) {
                if (f.contemStreaming(s)) {
                    resultado.add(f);
                    break;
                }
            }
        }
        return resultado;
    }
}
