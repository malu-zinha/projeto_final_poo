package modelo;

public class FilmeSuspense extends Filme {
    public FilmeSuspense(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.SUSPENSE;
    }
}
