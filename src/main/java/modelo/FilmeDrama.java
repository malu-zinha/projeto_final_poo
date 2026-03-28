package modelo;

// Herança — FilmeDrama herda todos os atributos e métodos de Filme
public class FilmeDrama extends Filme {
    private String temaCentral;

    public FilmeDrama(int id, String titulo, int anoLancamento, int duracaoMinutos,
                      String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                      int oscarIndicacoes, int oscarVitorias, String temaCentral) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.temaCentral = temaCentral;
    }

    // Polimorfismo dinâmico — sobrescreve método abstrato da classe pai
    @Override
    public Genero getGenero() {
        return Genero.DRAMA;
    }

    public String getTemaCentral() { return temaCentral; }
    public void setTemaCentral(String temaCentral) { this.temaCentral = temaCentral; }
}
