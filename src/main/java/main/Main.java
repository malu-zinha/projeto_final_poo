package main;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.*;
import servico.PlataformaRecomendacao;
import excecao.*;

public class Main {

    // constantes para acessar o menu
    private static final int OPCAO_CADASTRAR    = 1;
    private static final int OPCAO_LOGIN        = 2;
    private static final int OPCAO_PREFERENCIAS = 3;
    private static final int OPCAO_RECOMENDAR   = 4;
    private static final int OPCAO_BUSCAR       = 5;
    private static final int OPCAO_POR_GENERO   = 6;
    private static final int OPCAO_TODOS        = 7;
    private static final int OPCAO_SAIR         = 0;
    // inicializando a plataforma
    private static final PlataformaRecomendacao plataforma = new PlataformaRecomendacao();
    private static Usuario usuarioLogado = null;
    private static final Scanner scanner = new Scanner(System.in);

    // ── ponto de entrada ──────────────────────────────────────────────────────
    public static void main(String[] args) {
        exibirCabecalho();
        executarLoop();
        scanner.close();
    }

    // loop principal
    private static void executarLoop() {
        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Opção: ");
            executando = processarOpcao(opcao);
        }
    }

    private static boolean processarOpcao(int opcao) {
        switch (opcao) {
            case OPCAO_CADASTRAR:    cadastrarUsuario();      break;
            case OPCAO_LOGIN:        login();                 break;
            case OPCAO_PREFERENCIAS: configurarPreferencias(); break;
            case OPCAO_RECOMENDAR:   verRecomendacoes();      break;
            case OPCAO_BUSCAR:       buscarFilme();           break;
            case OPCAO_POR_GENERO:   listarPorGenero();       break;
            case OPCAO_TODOS:        listarTodos();           break;
            case OPCAO_SAIR:
                System.out.println("Encerrando o sistema. Até mais!");
                return false;
            default:
                System.out.println("Opção inválida.\n");
        }
        return true;
    }

    // função pra exibir o cabeçalho
    private static void exibirCabecalho() {
        System.out.println("\033[0;1Sistema de Recomendação de Filmes do Oscar\033[0m");
        System.out.println("Catálogo carregado com " + plataforma.getTotalFilmes() + " filmes.\n");
    }

    private static void exibirMenu() {
        System.out.println("\033[0;1mMenu Principal\033[0m");
        if (usuarioLogado != null) {
            System.out.println("Logado como: " + usuarioLogado.getNome());
        }
        System.out.println(OPCAO_CADASTRAR    + ". Cadastrar usuário");
        System.out.println(OPCAO_LOGIN        + ". Login");
        System.out.println(OPCAO_PREFERENCIAS + ". Configurar preferências");
        System.out.println(OPCAO_RECOMENDAR   + ". Ver recomendações");
        System.out.println(OPCAO_BUSCAR       + ". Buscar filme por título");
        System.out.println(OPCAO_POR_GENERO   + ". Listar filmes por gênero");
        System.out.println(OPCAO_TODOS        + ". Ver todos os filmes do catálogo");
        System.out.println(OPCAO_SAIR         + ". Sair");
    }

    // exigindo login para fazer qualquer operação
    private static boolean exigirLogin() {
        if (usuarioLogado == null) {
            System.out.println("Você precisa estar logado para continuar.\n");
            return false;
        }
        return true;
    }
    // função pra realizar cadastro
    private static void cadastrarUsuario() {
        boolean sucesso = false;
        try {
            System.out.print("\033[0;1mNome: \033[0m");
            String nome = scanner.nextLine();
            System.out.print("\033[0;1mEmail: \033[0m");
            String email = scanner.nextLine();
            System.out.print("\033[0;1mSenha: \033[0m");
            String senha = scanner.nextLine();
            int idade = lerInteiro("\033[0;1mIdade: \033[0m");

            Usuario u = plataforma.cadastrarUsuario(nome, email, senha, idade);
            usuarioLogado = u;
            sucesso = true;
            System.out.println("Cadastro realizado com sucesso! Bem-vindo(a), " + u.getNome() + "!\n");
        } catch (UsuarioJaCadastradoException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        } catch (IdadeInvalidaException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        } finally {
            if (!sucesso) {
                System.out.println("O cadastro não foi concluído. Tente novamente.\n");
            }
        }
    }
    // função para login
    private static void login() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuarioLogado = plataforma.login(email, senha);
            System.out.println("Login realizado! Bem-vindo(a), " + usuarioLogado.getNome() + "!\n");
        } catch (SenhaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        }
    }

    private static void configurarPreferencias() {
        if (!exigirLogin()) return;   

        System.out.println("\n\033[0;1m Configurar Preferências \033[0m");

        configurarGeneros();
        configurarStreamings();
        configurarDuracao();

        plataforma.salvarPreferencias(usuarioLogado);
        System.out.println("Preferências atualizadas!\n");
    }

    // blocos de configuração
    private static void configurarGeneros() {
        Genero[] generos = Genero.values();
        System.out.println("Gêneros disponíveis:");
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ". " + generos[i].getDescricao());
        }
        System.out.println("Digite os números dos gêneros preferidos (separados por espaço, 0 para pular):");
        String linha = scanner.nextLine();
        if (linha.trim().equals("0")) return;

        usuarioLogado.getGenerosPreferidos().clear();
        for (String parte : linha.trim().split("\\s+")) {
            int idx = Integer.parseInt(parte) - 1;
            if (idx >= 0 && idx < generos.length) {
                usuarioLogado.adicionarGenero(generos[idx]);
            }
        }
    }

    private static void configurarStreamings() {
        Streaming[] streamings = Streaming.values();
        System.out.println("\n\033[0;1mStreamings disponíveis:\033[0m");
        for (int i = 0; i < streamings.length; i++) {
            System.out.println("  " + (i + 1) + ". " + streamings[i].getNome());
        }
        System.out.println("Digite os números dos streamings assinados (separados por espaço, 0 para pular):");
        String linha = scanner.nextLine();
        if (linha.trim().equals("0")) return;

        usuarioLogado.getStreamingsAssinados().clear();
        for (String parte : linha.trim().split("\\s+")) {
            int idx = Integer.parseInt(parte) - 1;
            if (idx >= 0 && idx < streamings.length) {
                usuarioLogado.adicionarStreaming(streamings[idx]);
            }
        }
    }

    private static void configurarDuracao() {
        System.out.println("\nDuração mínima em minutos (atual: " + usuarioLogado.getDuracaoMinima() + ", 0 para manter):");
        int min = lerInteiro("> ");
        if (min > 0) usuarioLogado.setDuracaoMinima(min);

        System.out.println("Duração máxima em minutos (atual: " + usuarioLogado.getDuracaoMaxima() + ", 0 para manter):");
        int max = lerInteiro("> ");
        if (max > 0) usuarioLogado.setDuracaoMaxima(max);
    }

    private static void verRecomendacoes() {
        if (!exigirLogin()) return;   

        ArrayList<Filme> recomendacoes = plataforma.recomendar(usuarioLogado);
        System.out.println("\n\033[0;1mRecomendações para " + usuarioLogado.getNome() + "\033[0m");
        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhuma recomendação encontrada.\n");
            return;
        }
        imprimirListaFilmes(recomendacoes);
    }

    private static void buscarFilme() {
        try {
            System.out.println("\n\033[0;1mTítulo do filme\033[0m");
            String titulo = scanner.nextLine();
            Filme f = plataforma.buscarFilme(titulo);
            System.out.println("\n\033[0;1mDetalhes\033[0m");
            System.out.println(f);
            System.out.println("\033[0;1mSinopse: \033[0m" + f.getSinopse());
            System.out.println("\033[0;1mFaixa etária: \033[0m" + f.getFaixaEtaria() + "\n");
        } catch (FilmeNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        }
    }

    private static void listarPorGenero() {
        Genero[] generos = Genero.values();
        System.out.println("Gêneros disponíveis:");
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ". " + generos[i].getDescricao());
        }
        int idx = lerInteiro("Escolha o gênero: ") - 1;
        if (idx < 0 || idx >= generos.length) {
            System.out.println("Gênero inválido.\n");
            return;
        }

        ArrayList<Filme> lista = plataforma.listarPorGenero(generos[idx]);
        System.out.println("\n\033[0;1m Filmes de " + generos[idx].getDescricao() + "\033[0m");
        if (lista.isEmpty()) {
            System.out.println("Nenhum filme encontrado.\n");
            return;
        }
        imprimirListaFilmes(lista);
    }

    private static void listarTodos() {
        ArrayList<Filme> todos = plataforma.listarTodos();
        System.out.println("\n\033[0;1m Catálogo Completo (" + todos.size() + " filmes) \033[0m");
        imprimirListaFilmes(todos);
    }

    private static void imprimirListaFilmes(ArrayList<Filme> filmes) {
        for (int i = 0; i < filmes.size(); i++) {
            System.out.println((i + 1) + ". " + filmes.get(i));
        }
        System.out.println();
    }

    private static int lerInteiro(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Por favor, digite um número: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}