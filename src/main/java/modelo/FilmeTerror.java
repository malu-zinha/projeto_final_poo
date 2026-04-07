package modelo;

public class FilmeTerror extends Filme {
    public FilmeTerror(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.TERROR;
    }
}
