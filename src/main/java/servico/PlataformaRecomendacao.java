package servico;

import java.util.ArrayList;
import modelo.Filme;
import modelo.Genero;
import modelo.Streaming;
import modelo.Usuario;
import excecao.FilmeNaoEncontradoException;
import excecao.IdadeInvalidaException;
import excecao.SenhaInvalidaException;
import excecao.UsuarioJaCadastradoException;
import infra.SupabaseClient;
import infra.SupabaseConfig;

// Fachada do sistema — centraliza todas as operações e delega para serviços especializados
// Quando Supabase está configurado, cadastro/login/preferências persistem no banco
// Quando não está, tudo funciona em memória (modo offline)
public class PlataformaRecomendacao {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Filme> filmes;
    private SistemaRecomendacao sistemaRecomendacao;
    private SupabaseClient supabase;
    private int proximoIdUsuario;
    private int proximoIdFilme;

    public PlataformaRecomendacao() {
        this.usuarios = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.sistemaRecomendacao = new SistemaRecomendacao();
        this.proximoIdUsuario = 1;

        if (SupabaseConfig.isConfigurado()) {
            this.supabase = new SupabaseClient();
            System.out.println("[Supabase] Conectado ao banco de dados.");
        } else {
            System.out.println("[Offline] Arquivo .env não encontrado — dados ficam em memória.");
        }

        ArrayList<Filme> catalogo = CatalogoOscar.getFilmes();
        this.filmes.addAll(catalogo);
        this.proximoIdFilme = catalogo.size() + 1;
    }

    public Usuario cadastrarUsuario(String nome, String email, String senha, int idade)
            throws UsuarioJaCadastradoException, IdadeInvalidaException {
        if (idade <= 0) {
            throw new IdadeInvalidaException();
        }

        if (supabase != null) {
            try {
                // 1. Cria usuário no Supabase Auth
                String[] auth = supabase.cadastrar(email, senha);
                String authId = auth[0];
                String accessToken = auth[1];

                Usuario novo = new Usuario(proximoIdUsuario++, nome, email, senha, idade);
                novo.setAccessToken(accessToken);

                // 2. Cria perfil na tabela usuarios via PostgREST
                if (accessToken != null) {
                    supabase.criarPerfil(accessToken, authId, nome, email, idade);
                    String[] perfil = supabase.carregarPerfil(accessToken);
                    if (perfil != null) {
                        novo.setSupabaseId(perfil[0]);
                    }
                }

                usuarios.add(novo);
                return novo;
            } catch (Exception e) {
                String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                if (msg.contains("already") || msg.contains("registered") || msg.contains("duplicate")) {
                    throw new UsuarioJaCadastradoException();
                }
                throw new UsuarioJaCadastradoException("Erro no cadastro: " + e.getMessage());
            }
        }

        // Modo offline — validação local
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
        if (supabase != null) {
            try {
                String[] auth = supabase.login(email, senha);
                String accessToken = auth[1];

                String[] perfil = supabase.carregarPerfil(accessToken);
                if (perfil == null) {
                    throw new SenhaInvalidaException("Perfil não encontrado no banco.");
                }

                String supabaseId = perfil[0];
                String nome = perfil[1];
                int idade = Integer.parseInt(perfil[3]);
                int durMin = Integer.parseInt(perfil[4]);
                int durMax = Integer.parseInt(perfil[5]);

                Usuario u = new Usuario(proximoIdUsuario++, nome, email, senha, idade);
                u.setSupabaseId(supabaseId);
                u.setAccessToken(accessToken);
                u.setDuracaoMinima(durMin);
                u.setDuracaoMaxima(durMax);

                // Carrega preferências salvas no banco
                for (Genero g : supabase.carregarGeneros(accessToken, supabaseId)) {
                    u.adicionarGenero(g);
                }
                for (Streaming s : supabase.carregarStreamings(accessToken, supabaseId)) {
                    u.adicionarStreaming(s);
                }

                usuarios.add(u);
                return u;
            } catch (SenhaInvalidaException e) {
                throw e;
            } catch (Exception e) {
                throw new SenhaInvalidaException(e.getMessage());
            }
        }

        // Modo offline
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

    // Persiste preferências do usuário no Supabase (gêneros, streamings, duração)
    public void salvarPreferencias(Usuario u) {
        if (supabase == null || u.getAccessToken() == null || u.getSupabaseId() == null) return;

        try {
            String token = u.getAccessToken();
            String id = u.getSupabaseId();
            supabase.salvarGeneros(token, id, u.getGenerosPreferidos());
            supabase.salvarStreamings(token, id, u.getStreamingsAssinados());
            supabase.atualizarDuracao(token, id, u.getDuracaoMinima(), u.getDuracaoMaxima());
        } catch (Exception e) {
            System.err.println("Erro ao salvar preferências no banco: " + e.getMessage());
        }
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
