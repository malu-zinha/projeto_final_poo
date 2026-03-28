package modelo;

// Herança — FilmeThriller herda todos os atributos e métodos de Filme
public class FilmeThriller extends Filme {
    private String tipoThriller;

    public FilmeThriller(int id, String titulo, int anoLancamento, int duracaoMinutos,
                         String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                         int oscarIndicacoes, int oscarVitorias, String tipoThriller) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.tipoThriller = tipoThriller;
    }

    @Override
    public Genero getGenero() {
        return Genero.THRILLER;
    }

    public String getTipoThriller() { return tipoThriller; }
    public void setTipoThriller(String tipoThriller) { this.tipoThriller = tipoThriller; }
}
