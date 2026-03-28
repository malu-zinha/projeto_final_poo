package modelo;

// Herança — FilmeTerror herda todos os atributos e métodos de Filme
public class FilmeTerror extends Filme {
    private String tipoTerror;

    public FilmeTerror(int id, String titulo, int anoLancamento, int duracaoMinutos,
                       String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                       int oscarIndicacoes, int oscarVitorias, String tipoTerror) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.tipoTerror = tipoTerror;
    }

    @Override
    public Genero getGenero() {
        return Genero.TERROR;
    }

    public String getTipoTerror() { return tipoTerror; }
    public void setTipoTerror(String tipoTerror) { this.tipoTerror = tipoTerror; }
}
