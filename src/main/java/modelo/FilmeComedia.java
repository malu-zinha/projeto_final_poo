package modelo;

// Herança — FilmeComedia herda todos os atributos e métodos de Filme
public class FilmeComedia extends Filme {
    private String tipoComedia;

    public FilmeComedia(int id, String titulo, int anoLancamento, int duracaoMinutos,
                        String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                        int oscarIndicacoes, int oscarVitorias, String tipoComedia) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.tipoComedia = tipoComedia;
    }

    @Override
    public Genero getGenero() {
        return Genero.COMEDIA;
    }

    public String getTipoComedia() { return tipoComedia; }
    public void setTipoComedia(String tipoComedia) { this.tipoComedia = tipoComedia; }
}
