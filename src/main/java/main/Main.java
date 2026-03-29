package main;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.*;
import servico.PlataformaRecomendacao;
import excecao.*;

// Classe principal — menu interativo via Scanner para testar o sistema
public class Main {

    private static PlataformaRecomendacao plataforma = new PlataformaRecomendacao();
    private static Usuario usuarioLogado = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Sistema de Recomendação de Filmes — Oscar ===");
        System.out.println("Catálogo carregado com " + plataforma.getTotalFilmes() + " filmes.\n");

        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1: cadastrarUsuario(); break;
                case 2: login(); break;
                case 3: configurarPreferencias(); break;
                case 4: verRecomendacoes(); break;
                case 5: buscarFilme(); break;
                case 6: listarPorGenero(); break;
                case 7: listarTodos(); break;
                case 0:
                    executando = false;
                    System.out.println("Encerrando o sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("--- Menu Principal ---");
        if (usuarioLogado != null) {
            System.out.println("Logado como: " + usuarioLogado.getNome());
        }
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Login");
        System.out.println("3. Configurar preferências");
        System.out.println("4. Ver recomendações");
        System.out.println("5. Buscar filme por título");
        System.out.println("6. Listar filmes por gênero");
        System.out.println("7. Ver todos os filmes do catálogo");
        System.out.println("0. Sair");
    }

    private static void cadastrarUsuario() {
        // Uso de try/catch/finally para demonstrar tratamento de exceções
        boolean sucesso = false;
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            int idade = lerInteiro("Idade: ");

            Usuario u = plataforma.cadastrarUsuario(nome, email, senha, idade);
            usuarioLogado = u;
            sucesso = true;
            System.out.println("Cadastro realizado com sucesso! Bem-vindo(a), " + u.getNome() + "!\n");
        } catch (UsuarioJaCadastradoException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        } catch (IdadeInvalidaException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        } finally {
            // Demonstração do bloco finally — executa independente de exceção
            if (!sucesso) {
                System.out.println("O cadastro não foi concluído. Tente novamente.\n");
            }
        }
    }

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
        if (usuarioLogado == null) {
            System.out.println("Você precisa estar logado para configurar preferências.\n");
            return;
        }

        System.out.println("\n--- Configurar Preferências ---");

        // Gêneros
        System.out.println("Gêneros disponíveis:");
        Genero[] generos = Genero.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ". " + generos[i].getDescricao());
        }
        System.out.println("Digite os números dos gêneros preferidos (separados por espaço, 0 para pular):");
        String linhaGeneros = scanner.nextLine();
        if (!linhaGeneros.trim().equals("0")) {
            usuarioLogado.getGenerosPreferidos().clear();
            for (String parte : linhaGeneros.trim().split("\\s+")) {
                int idx = Integer.parseInt(parte) - 1;
                if (idx >= 0 && idx < generos.length) {
                    usuarioLogado.adicionarGenero(generos[idx]);
                }
            }
        }

        // Streamings
        System.out.println("\nStreamings disponíveis:");
        Streaming[] streamings = Streaming.values();
        for (int i = 0; i < streamings.length; i++) {
            System.out.println("  " + (i + 1) + ". " + streamings[i].getNome());
        }
        System.out.println("Digite os números dos streamings assinados (separados por espaço, 0 para pular):");
        String linhaStreamings = scanner.nextLine();
        if (!linhaStreamings.trim().equals("0")) {
            usuarioLogado.getStreamingsAssinados().clear();
            for (String parte : linhaStreamings.trim().split("\\s+")) {
                int idx = Integer.parseInt(parte) - 1;
                if (idx >= 0 && idx < streamings.length) {
                    usuarioLogado.adicionarStreaming(streamings[idx]);
                }
            }
        }

        // Duração
        System.out.println("\nDuração mínima em minutos (atual: " + usuarioLogado.getDuracaoMinima() + ", 0 para manter):");
        int min = lerInteiro("> ");
        if (min > 0) {
            usuarioLogado.setDuracaoMinima(min);
        }
        System.out.println("Duração máxima em minutos (atual: " + usuarioLogado.getDuracaoMaxima() + ", 0 para manter):");
        int max = lerInteiro("> ");
        if (max > 0) {
            usuarioLogado.setDuracaoMaxima(max);
        }

        // Persiste no Supabase (se configurado)
        plataforma.salvarPreferencias(usuarioLogado);
        System.out.println("Preferências atualizadas!\n");
    }

    private static void verRecomendacoes() {
        if (usuarioLogado == null) {
            System.out.println("Você precisa estar logado para ver recomendações.\n");
            return;
        }

        ArrayList<Filme> recomendacoes = plataforma.recomendar(usuarioLogado);
        System.out.println("\n--- Recomendações para " + usuarioLogado.getNome() + " ---");
        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhuma recomendação encontrada.\n");
            return;
        }
        for (int i = 0; i < recomendacoes.size(); i++) {
            System.out.println((i + 1) + ". " + recomendacoes.get(i));
        }
        System.out.println();
    }

    private static void buscarFilme() {
        try {
            System.out.print("Título do filme: ");
            String titulo = scanner.nextLine();
            Filme f = plataforma.buscarFilme(titulo);
            System.out.println("\n--- Detalhes ---");
            System.out.println(f);
            System.out.println("Sinopse: " + f.getSinopse());
            System.out.println("Faixa etária: " + f.getFaixaEtaria() + "\n");
        } catch (FilmeNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage() + "\n");
        }
    }

    private static void listarPorGenero() {
        System.out.println("Gêneros disponíveis:");
        Genero[] generos = Genero.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ". " + generos[i].getDescricao());
        }
        int idx = lerInteiro("Escolha o gênero: ") - 1;
        if (idx < 0 || idx >= generos.length) {
            System.out.println("Gênero inválido.\n");
            return;
        }

        ArrayList<Filme> lista = plataforma.listarPorGenero(generos[idx]);
        System.out.println("\n--- Filmes de " + generos[idx].getDescricao() + " ---");
        if (lista.isEmpty()) {
            System.out.println("Nenhum filme encontrado.\n");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        System.out.println();
    }

    private static void listarTodos() {
        ArrayList<Filme> todos = plataforma.listarTodos();
        System.out.println("\n--- Catálogo Completo (" + todos.size() + " filmes) ---");
        for (int i = 0; i < todos.size(); i++) {
            System.out.println((i + 1) + ". " + todos.get(i));
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
