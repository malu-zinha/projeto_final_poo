package modelo;

public enum FaixaEtaria {
    LIVRE(0),
    MAIORES_6(6),
    MAIORES_10(10),
    MAIORES_12(12),
    MAIORES_14(14),
    MAIORES_16(16),
    MAIORES_18(18);

    private final int idadeMinima;

    FaixaEtaria(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }

    public static FaixaEtaria fromIdade(int idade) {
        FaixaEtaria resultado = LIVRE;
        for (FaixaEtaria faixa : values()) {
            if (idade >= faixa.idadeMinima) {
                resultado = faixa;
            }
        }
        return resultado;
    }

    public static FaixaEtaria fromTexto(String texto) {
        if (texto == null || texto.equals("null") || texto.equalsIgnoreCase("Livre")) {
            return LIVRE;
        }
        String numeros = texto.replaceAll("[^0-9]", "");
        if (numeros.isEmpty()) return LIVRE;
        int idade = Integer.parseInt(numeros);
        for (FaixaEtaria f : values()) {
            if (f.idadeMinima == idade) return f;
        }
        return LIVRE;
    }
}
