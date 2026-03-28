package modelo;

// Herança — FilmeFiccaoCientifica herda todos os atributos e métodos de Filme
public class FilmeFiccaoCientifica extends Filme {
    private String ambientacao;

    public FilmeFiccaoCientifica(int id, String titulo, int anoLancamento, int duracaoMinutos,
                                  String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                                  int oscarIndicacoes, int oscarVitorias, String ambientacao) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.ambientacao = ambientacao;
    }

    @Override
    public Genero getGenero() {
        return Genero.FICCAO_CIENTIFICA;
    }

    public String getAmbientacao() { return ambientacao; }
    public void setAmbientacao(String ambientacao) { this.ambientacao = ambientacao; }
}
