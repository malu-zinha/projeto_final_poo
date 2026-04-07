package modelo;

public class FilmeGuerra extends Filme {
    public FilmeGuerra(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.GUERRA;
    }
}
