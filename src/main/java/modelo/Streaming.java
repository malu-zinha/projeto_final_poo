package modelo;

public enum Streaming {
    NETFLIX("Netflix"),
    AMAZON_PRIME("Amazon Prime Video"),
    HBO_MAX("HBO Max"),
    DISNEY_PLUS("Disney+"),
    APPLE_TV("Apple TV+"),
    MUBI("MUBI"),
    GLOBOPLAY("Globoplay"),
    MERCADO_PLAY("Mercado Play"),
    GOOGLE_PLAY("Google Play Filmes e TV"),
    CLARO_TV_PLUS("Claro TV+"),
    YOUTUBE("Youtube"),
    TELECINE("Telecine");

    private final String nome;

    Streaming(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static Streaming fromNome(String nome) {
        String limpo = nome.replaceAll("\\s*\\(.*\\)\\s*$", "").trim();
        for (Streaming s : values()) {
            if (s.nome.equalsIgnoreCase(limpo)) {
                return s;
            }
        }
        return null;
    }
}
