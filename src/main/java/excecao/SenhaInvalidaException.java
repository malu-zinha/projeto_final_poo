package excecao;

// Exceção customizada — lançada quando a senha não confere no login
public class SenhaInvalidaException extends Exception {

    public SenhaInvalidaException() {
        super("Senha inválida.");
    }

    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
