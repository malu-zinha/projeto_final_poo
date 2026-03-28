package servico;

import java.util.ArrayList;
import java.util.Comparator;
import modelo.Filme;
import modelo.Genero;
import modelo.Streaming;
import modelo.Usuario;

// Classe responsável por calcular a pontuação e recomendar filmes ao usuário
public class SistemaRecomendacao {

    private static final int PESO_GENERO = 3;
    private static final int PESO_STREAMING = 2;
    private static final int PESO_DURACAO = 2;
    private static final int PESO_FAIXA_ETARIA = 1;
    private static final int PESO_NOTA_IMDB = 1;

    // Filtra o catálogo, pontua cada filme e retorna ordenado por score decrescente
    public ArrayList<Filme> recomendar(Usuario u, ArrayList<Filme> catalogo) {
        ArrayList<Filme> filtrados = filtrarPorFaixaEtaria(catalogo, u.getIdade());

        ArrayList<Filme> resultado = filtrarPorGenero(filtrados, u.getGenerosPreferidos());
        resultado = filtrarPorDuracao(resultado, u.getDuracaoMinima(), u.getDuracaoMaxima());
        resultado = filtrarPorStreaming(resultado, u.getStreamingsAssinados());

        // Fallback: se todos os filtros eliminaram tudo, volta só com faixa etária
        if (resultado.isEmpty()) {
            resultado = filtrados;
        }

        resultado.sort(Comparator.comparingInt((Filme f) -> calcularScore(f, u)).reversed());
        return resultado;
    }

    private int calcularScore(Filme f, Usuario u) {
        int score = 0;

        if (u.getGenerosPreferidos().contains(f.getGenero())) {
            score += PESO_GENERO;
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

        if (f.getNotaIMDB() >= 7.5) {
            score += PESO_NOTA_IMDB;
        }

        return score;
    }

    private ArrayList<Filme> filtrarPorGenero(ArrayList<Filme> filmes, ArrayList<Genero> generos) {
        if (generos.isEmpty()) {
            return new ArrayList<>(filmes);
        }
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            if (generos.contains(f.getGenero())) {
                resultado.add(f);
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
        if (streamings.isEmpty()) {
            return new ArrayList<>(filmes);
        }
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
