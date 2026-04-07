package modelo;

public class FilmeFiccaoCientifica extends Filme {
    public FilmeFiccaoCientifica(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.FICCAO_CIENTIFICA;
    }
}
