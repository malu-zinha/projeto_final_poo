package servico;

import java.util.ArrayList;
import modelo.Filme;
import modelo.Genero;
import modelo.Usuario;
import excecao.FilmeNaoEncontradoException;
import excecao.IdadeInvalidaException;
import excecao.SenhaInvalidaException;
import excecao.UsuarioJaCadastradoException;

// Fachada do sistema — centraliza todas as operações e delega para serviços especializados
public class PlataformaRecomendacao {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Filme> filmes;
    private SistemaRecomendacao sistemaRecomendacao;
    private int proximoIdUsuario;
    private int proximoIdFilme;

    public PlataformaRecomendacao() {
        this.usuarios = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.sistemaRecomendacao = new SistemaRecomendacao();
        this.proximoIdUsuario = 1;

        ArrayList<Filme> catalogo = CatalogoOscar.getFilmes();
        this.filmes.addAll(catalogo);
        this.proximoIdFilme = catalogo.size() + 1;
    }

    public Usuario cadastrarUsuario(String nome, String email, String senha, int idade)
            throws UsuarioJaCadastradoException, IdadeInvalidaException {
        if (idade <= 0) {
            throw new IdadeInvalidaException();
        }
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new UsuarioJaCadastradoException();
            }
        }
        Usuario novo = new Usuario(proximoIdUsuario++, nome, email, senha, idade);
        usuarios.add(novo);
        return novo;
    }

    public Usuario login(String email, String senha) throws SenhaInvalidaException {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                if (u.getSenha().equals(senha)) {
                    return u;
                }
                throw new SenhaInvalidaException();
            }
        }
        throw new SenhaInvalidaException("Usuário não encontrado ou senha inválida.");
    }

    public void cadastrarFilme(Filme f) {
        f.setId(proximoIdFilme++);
        filmes.add(f);
    }

    public ArrayList<Filme> recomendar(Usuario u) {
        return sistemaRecomendacao.recomendar(u, filmes);
    }

    // Polimorfismo estático (sobrecarga) — busca por título
    public Filme buscarFilme(String titulo) throws FilmeNaoEncontradoException {
        for (Filme f : filmes) {
            if (f.getTitulo().equalsIgnoreCase(titulo)) {
                return f;
            }
        }
        throw new FilmeNaoEncontradoException("Filme não encontrado: " + titulo);
    }

    // Polimorfismo estático (sobrecarga) — busca por id
    public Filme buscarFilme(int id) throws FilmeNaoEncontradoException {
        for (Filme f : filmes) {
            if (f.getId() == id) {
                return f;
            }
        }
        throw new FilmeNaoEncontradoException("Filme não encontrado com ID: " + id);
    }

    public ArrayList<Filme> listarPorGenero(Genero g) {
        ArrayList<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            if (f.getGenero() == g) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    public ArrayList<Filme> listarTodos() {
        return new ArrayList<>(filmes);
    }

    public int getTotalFilmes() { return filmes.size(); }
    public int getTotalUsuarios() { return usuarios.size(); }
}
