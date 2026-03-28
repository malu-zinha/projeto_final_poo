package excecao;

// Exceção customizada — lançada ao tentar cadastrar email duplicado
public class UsuarioJaCadastradoException extends Exception {

    public UsuarioJaCadastradoException() {
        super("Usuário já cadastrado com este e-mail.");
    }

    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
