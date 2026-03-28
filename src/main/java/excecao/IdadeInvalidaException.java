package excecao;

// Exceção customizada — lançada quando idade informada é inválida (<= 0)
public class IdadeInvalidaException extends Exception {

    public IdadeInvalidaException() {
        super("Idade inválida. A idade deve ser maior que zero.");
    }

    public IdadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
