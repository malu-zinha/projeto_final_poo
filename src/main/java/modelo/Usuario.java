package modelo;

import java.util.ArrayList;

// Demonstra encapsulamento (atributos private) e uso de ArrayList para coleções
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private ArrayList<Genero> generosPreferidos;
    private ArrayList<Streaming> streamingsAssinados;
    private int duracaoMinima;
    private int duracaoMaxima;

    // Campos para integração com Supabase (transientes — não existiam no modelo original)
    private String supabaseId;
    private String accessToken;

    public Usuario(int id, String nome, String email, String senha, int idade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.generosPreferidos = new ArrayList<>();
        this.streamingsAssinados = new ArrayList<>();
        this.duracaoMinima = 60;
        this.duracaoMaxima = 240;
    }

    public void adicionarGenero(Genero g) {
        if (!generosPreferidos.contains(g)) {
            generosPreferidos.add(g);
        }
    }

    public void removerGenero(Genero g) {
        generosPreferidos.remove(g);
    }

    public void adicionarStreaming(Streaming s) {
        if (!streamingsAssinados.contains(s)) {
            streamingsAssinados.add(s);
        }
    }

    public void removerStreaming(Streaming s) {
        streamingsAssinados.remove(s);
    }

    // Verifica compatibilidade: gênero, duração, streaming e faixa etária
    public boolean isCompativel(Filme f) {
        boolean generoOk = generosPreferidos.isEmpty();
        if (!generoOk) {
            for (Genero g : f.getGeneros()) {
                if (generosPreferidos.contains(g)) {
                    generoOk = true;
                    break;
                }
            }
        }

        boolean duracaoOk = f.getDuracaoMinutos() >= duracaoMinima
                && f.getDuracaoMinutos() <= duracaoMaxima;

        boolean streamingOk = false;
        if (streamingsAssinados.isEmpty()) {
            streamingOk = true;
        } else {
            for (Streaming s : streamingsAssinados) {
                if (f.contemStreaming(s)) {
                    streamingOk = true;
                    break;
                }
            }
        }

        boolean idadeOk = f.isAdequadoParaIdade(idade);

        return generoOk && duracaoOk && streamingOk && idadeOk;
    }

    @Override
    public String toString() {
        return nome + " (" + email + ") | Idade: " + idade;
    }

    // Getters e setters — encapsulamento
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public ArrayList<Genero> getGenerosPreferidos() { return generosPreferidos; }
    public void setGenerosPreferidos(ArrayList<Genero> generosPreferidos) { this.generosPreferidos = generosPreferidos; }

    public ArrayList<Streaming> getStreamingsAssinados() { return streamingsAssinados; }
    public void setStreamingsAssinados(ArrayList<Streaming> streamingsAssinados) { this.streamingsAssinados = streamingsAssinados; }

    public int getDuracaoMinima() { return duracaoMinima; }
    public void setDuracaoMinima(int duracaoMinima) { this.duracaoMinima = duracaoMinima; }

    public int getDuracaoMaxima() { return duracaoMaxima; }
    public void setDuracaoMaxima(int duracaoMaxima) { this.duracaoMaxima = duracaoMaxima; }

    public String getSupabaseId() { return supabaseId; }
    public void setSupabaseId(String supabaseId) { this.supabaseId = supabaseId; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
