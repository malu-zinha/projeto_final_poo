package excecao;

// Exceção customizada — lançada quando busca não encontra nenhum filme
public class FilmeNaoEncontradoException extends Exception {

    public FilmeNaoEncontradoException() {
        super("Filme não encontrado no catálogo.");
    }

    public FilmeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
