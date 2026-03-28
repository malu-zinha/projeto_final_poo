package modelo;

// Herança — FilmeAcao herda todos os atributos e métodos de Filme
public class FilmeAcao extends Filme {
    private int nivelIntensidade;

    public FilmeAcao(int id, String titulo, int anoLancamento, int duracaoMinutos,
                     String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                     int oscarIndicacoes, int oscarVitorias, int nivelIntensidade) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.nivelIntensidade = nivelIntensidade;
    }

    @Override
    public Genero getGenero() {
        return Genero.ACAO;
    }

    public int getNivelIntensidade() { return nivelIntensidade; }
    public void setNivelIntensidade(int nivelIntensidade) { this.nivelIntensidade = nivelIntensidade; }
}
