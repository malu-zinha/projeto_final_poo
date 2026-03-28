package modelo;

// Enum com campo idadeMinima — demonstra encapsulamento dentro de enums
public enum FaixaEtaria {
    LIVRE(0),
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

    // Retorna a faixa mais restritiva que o usuário consegue acessar
    public static FaixaEtaria fromIdade(int idade) {
        FaixaEtaria resultado = LIVRE;
        for (FaixaEtaria faixa : values()) {
            if (idade >= faixa.idadeMinima) {
                resultado = faixa;
            }
        }
        return resultado;
    }
}
