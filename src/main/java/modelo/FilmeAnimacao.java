package modelo;

// Herança — FilmeAnimacao herda todos os atributos e métodos de Filme
public class FilmeAnimacao extends Filme {
    private String tecnicaAnimacao;

    public FilmeAnimacao(int id, String titulo, int anoLancamento, int duracaoMinutos,
                         String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                         int oscarIndicacoes, int oscarVitorias, String tecnicaAnimacao) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.tecnicaAnimacao = tecnicaAnimacao;
    }

    @Override
    public Genero getGenero() {
        return Genero.ANIMACAO;
    }

    public String getTecnicaAnimacao() { return tecnicaAnimacao; }
    public void setTecnicaAnimacao(String tecnicaAnimacao) { this.tecnicaAnimacao = tecnicaAnimacao; }
}
