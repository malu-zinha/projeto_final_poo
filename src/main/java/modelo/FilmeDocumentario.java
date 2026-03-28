package modelo;

// Herança — FilmeDocumentario herda todos os atributos e métodos de Filme
public class FilmeDocumentario extends Filme {
    private String assunto;

    public FilmeDocumentario(int id, String titulo, int anoLancamento, int duracaoMinutos,
                             String sinopse, FaixaEtaria faixaEtaria, double notaIMDB,
                             int oscarIndicacoes, int oscarVitorias, String assunto) {
        super(id, titulo, anoLancamento, duracaoMinutos, sinopse, faixaEtaria,
              notaIMDB, oscarIndicacoes, oscarVitorias);
        this.assunto = assunto;
    }

    @Override
    public Genero getGenero() {
        return Genero.DOCUMENTARIO;
    }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }
}
