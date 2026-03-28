package modelo;

// Herança — FilmeRomance herda todos os atributos e métodos de Filme
public class FilmeRomance extends Filme {
    private String subgenero;

    public FilmeRomance(int id, String titulo, int anoLancamento, int duracaoMinutos,
                        String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                        int oscarIndicacoes, int oscarVitorias, String subgenero) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.subgenero = subgenero;
    }

    @Override
    public Genero getGenero() {
        return Genero.ROMANCE;
    }

    public String getSubgenero() { return subgenero; }
    public void setSubgenero(String subgenero) { this.subgenero = subgenero; }
}
