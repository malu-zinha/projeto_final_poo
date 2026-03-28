package modelo;

// Enum que representa as plataformas de streaming disponíveis no Brasil
public enum Streaming {
    NETFLIX("Netflix"),
    AMAZON_PRIME("Amazon Prime Video"),
    HBO_MAX("HBO Max"),
    DISNEY_PLUS("Disney+"),
    APPLE_TV("Apple TV+"),
    PARAMOUNT_PLUS("Paramount+"),
    MUBI("MUBI"),
    GLOBOPLAY("Globoplay");

    private final String nome;

    Streaming(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
