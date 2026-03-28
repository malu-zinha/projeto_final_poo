package modelo;

// Enum que representa os gêneros de filmes disponíveis no sistema (encapsulamento de constantes)
public enum Genero {
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    ACAO("Ação"),
    ANIMACAO("Animação"),
    TERROR("Terror"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    THRILLER("Thriller"),
    DOCUMENTARIO("Documentário");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
