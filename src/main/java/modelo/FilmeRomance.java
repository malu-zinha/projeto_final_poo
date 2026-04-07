package modelo;

public class FilmeRomance extends Filme {
    public FilmeRomance(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.ROMANCE;
    }
}
