package modelo;

public class FilmeAcao extends Filme {
    public FilmeAcao(int id, String titulo, int duracaoMinutos, FaixaEtaria faixaEtaria, String oscarAno) {
        super(id, titulo, duracaoMinutos, faixaEtaria, oscarAno);
    }

    @Override
    public Genero getGenero() {
        return Genero.ACAO;
    }
}
