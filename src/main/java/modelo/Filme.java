package modelo;

import java.util.ArrayList;

// Classe abstrata — base da hierarquia de herança para todos os gêneros de filme
// Demonstra: encapsulamento (atributos private + getters/setters), abstração e polimorfismo dinâmico (getGenero)
public abstract class Filme {
    private int id;
    private String titulo;
    private int anoLancamento;
    private int duracaoMinutos;
    private String sinopse;
    private FaixaEtaria faixaEtaria;
    private ArrayList<Streaming> streamings;
    private double notaIMDB;
    private int oscarIndicacoes;
    private int oscarVitorias;

    public Filme(int id, String titulo, int anoLancamento, int duracaoMinutos,
                 String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                 int oscarIndicacoes, int oscarVitorias) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.duracaoMinutos = duracaoMinutos;
        this.sinopse = sinopse;
        this.faixaEtaria = faixaEtaria;
        this.streamings = new ArrayList<>();
        this.notaIMDB = notaIMDB;
        this.oscarIndicacoes = oscarIndicacoes;
        this.oscarVitorias = oscarVitorias;
    }

    // Polimorfismo dinâmico — cada subclasse retorna seu Genero correspondente
    public abstract Genero getGenero();

    public String getDuracaoFormatada() {
        int horas = duracaoMinutos / 60;
        int minutos = duracaoMinutos % 60;
        if (horas > 0 && minutos > 0) {
            return horas + "h " + minutos + "min";
        } else if (horas > 0) {
            return horas + "h";
        }
        return minutos + "min";
    }

    public boolean isAdequadoParaIdade(int idade) {
        return idade >= faixaEtaria.getIdadeMinima();
    }

    public boolean contemStreaming(Streaming s) {
        return streamings.contains(s);
    }

    public void adicionarStreaming(Streaming s) {
        if (!streamings.contains(s)) {
            streamings.add(s);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append(" (").append(anoLancamento).append(") | ");
        sb.append(getGenero().getDescricao()).append(" | ");
        sb.append(getDuracaoFormatada()).append(" | ");
        sb.append("IMDB: ").append(notaIMDB).append(" | ");
        sb.append("Oscar: ").append(oscarIndicacoes).append(" ind., ");
        sb.append(oscarVitorias).append(" vit. | ");
        sb.append("Streamings: ");
        if (streamings.isEmpty()) {
            sb.append("nenhum");
        } else {
            for (int i = 0; i < streamings.size(); i++) {
                sb.append(streamings.get(i).getNome());
                if (i < streamings.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

    // Getters e setters — encapsulamento
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }

    public int getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(int duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public FaixaEtaria getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(FaixaEtaria faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public ArrayList<Streaming> getStreamings() { return streamings; }
    public void setStreamings(ArrayList<Streaming> streamings) { this.streamings = streamings; }

    public double getNotaIMDB() { return notaIMDB; }
    public void setNotaIMDB(double notaIMDB) { this.notaIMDB = notaIMDB; }

    public int getOscarIndicacoes() { return oscarIndicacoes; }
    public void setOscarIndicacoes(int oscarIndicacoes) { this.oscarIndicacoes = oscarIndicacoes; }

    public int getOscarVitorias() { return oscarVitorias; }
    public void setOscarVitorias(int oscarVitorias) { this.oscarVitorias = oscarVitorias; }
}
