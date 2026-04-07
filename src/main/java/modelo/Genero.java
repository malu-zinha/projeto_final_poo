package modelo;

public enum Genero {
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    ACAO("Ação"),
    ANIMACAO("Animação"),
    TERROR("Terror"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    THRILLER("Thriller"),
    DOCUMENTARIO("Documentário"),
    MUSICAL("Musical"),
    CRIME("Crime"),
    SUSPENSE("Suspense"),
    MISTERIO("Mistério"),
    AVENTURA("Aventura"),
    FANTASIA("Fantasia"),
    GUERRA("Guerra"),
    HISTORIA("História"),
    ESPORTE("Esporte"),
    SATIRA("Sátira"),
    FICCAO("Ficção"),
    MUSICA("Música");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Genero fromDescricao(String desc) {
        for (Genero g : values()) {
            if (g.descricao.equalsIgnoreCase(desc.trim())) {
                return g;
            }
        }
        return null;
    }
}
