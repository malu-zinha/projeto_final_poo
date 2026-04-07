package modelo;

import java.util.ArrayList;

// Classe abstrata — base da hierarquia de herança para todos os gêneros de filme
// Demonstra: encapsulamento (atributos private + getters/setters), abstração e polimorfismo dinâmico (getGenero)
public abstract class Filme {
    private int id;
    private String titulo;
    private int duracaoMinutos;
    private FaixaEtaria faixaEtaria;
    private ArrayList<Streaming> streamings;
    private ArrayList<Genero> generos;
    private String oscarAno;

    public Filme(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        this.id = id;
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
        this.faixaEtaria = faixaEtaria;
        this.streamings = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.oscarAno = oscarAno;
    }

    // Polimorfismo dinâmico — cada subclasse retorna seu Genero principal
    public abstract Genero getGenero();

    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    public void adicionarGenero(Genero g) {
        if (!generos.contains(g)) {
            generos.add(g);
        }
    }

    public String getDuracaoFormatada() {
        int horas = duracaoMinutos / 60;
        int minutos = duracaoMinutos % 60;
        if (horas > 0 && minutos > 0) return horas + "h " + minutos + "min";
        if (horas > 0) return horas + "h";
        return minutos + "min";
    }

    public boolean isAdequadoParaIdade(int idade) {
        return idade >= faixaEtaria.getIdadeMinima();
    }

    public boolean contemStreaming(Streaming s) {
        return streamings.contains(s);
    }

    public void adicionarStreaming(Streaming s) {
        if (s != null && !streamings.contains(s)) {
            streamings.add(s);
        }
    }

    public boolean contemGenero(Genero g) {
        return generos.contains(g);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append(" | ");
        sb.append(oscarAno).append(" | ");
        for (int i = 0; i < generos.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(generos.get(i).getDescricao());
        }
        sb.append(" | ").append(getDuracaoFormatada());
        sb.append(" | Streamings: ");
        if (streamings.isEmpty()) {
            sb.append("nenhum");
        } else {
            for (int i = 0; i < streamings.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(streamings.get(i).getNome());
            }
        }
        return sb.toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public FaixaEtaria getFaixaEtaria() { return faixaEtaria; }
    public ArrayList<Streaming> getStreamings() { return streamings; }
    public String getOscarAno() { return oscarAno; }
}
