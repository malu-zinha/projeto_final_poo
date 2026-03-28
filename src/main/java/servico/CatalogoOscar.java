package servico;

import java.util.ArrayList;
import modelo.*;

// Seed data — instancia filmes reais indicados ao Oscar (2023–2024)
// Quando o banco de dados for adicionado, esta classe vira o script de população inicial
public class CatalogoOscar {

    public static ArrayList<Filme> getFilmes() {
        ArrayList<Filme> filmes = new ArrayList<>();

        // 1 — Drama: Oppenheimer (Oscar 2024 — 13 indicações, 7 vitórias)
        FilmeDrama oppenheimer = new FilmeDrama(1, "Oppenheimer", 2023, 180,
                "A história de J. Robert Oppenheimer e a criação da bomba atômica.",
                FaixaEtaria.MAIORES_14, 8.3, 13, 7, "guerra e ciência");
        oppenheimer.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(oppenheimer);

        // 2 — Comédia: Pobres Criaturas (Oscar 2024 — 11 indicações, 4 vitórias)
        FilmeComedia pobresCriaturas = new FilmeComedia(2, "Pobres Criaturas", 2023, 141,
                "Uma jovem trazida de volta à vida por um cientista brilhante e não convencional.",
                FaixaEtaria.MAIORES_18, 7.9, 11, 4, "satírica");
        pobresCriaturas.adicionarStreaming(Streaming.DISNEY_PLUS);
        filmes.add(pobresCriaturas);

        // 3 — Ação: Duna: Parte Dois (Oscar 2025 — 5 indicações)
        FilmeAcao duna2 = new FilmeAcao(3, "Duna: Parte Dois", 2024, 166,
                "Paul Atreides se une aos Fremen para vingar a destruição de sua família.",
                FaixaEtaria.MAIORES_14, 8.5, 5, 1, 8);
        duna2.adicionarStreaming(Streaming.HBO_MAX);
        filmes.add(duna2);

        // 4 — Animação: Divertida Mente 2 (Oscar 2025 — 1 indicação)
        FilmeAnimacao divertidaMente2 = new FilmeAnimacao(4, "Divertida Mente 2", 2024, 100,
                "Riley enfrenta novas emoções ao entrar na adolescência.",
                FaixaEtaria.LIVRE, 7.6, 1, 0, "CGI");
        divertidaMente2.adicionarStreaming(Streaming.DISNEY_PLUS);
        filmes.add(divertidaMente2);

        // 5 — Terror: A Substância (Oscar 2025 — 5 indicações, 1 vitória)
        FilmeTerror aSubstancia = new FilmeTerror(5, "A Substância", 2024, 140,
                "Uma celebridade em declínio usa uma droga do mercado negro que cria uma versão mais jovem de si mesma.",
                FaixaEtaria.MAIORES_18, 7.3, 5, 1, "body horror");
        aSubstancia.adicionarStreaming(Streaming.MUBI);
        filmes.add(aSubstancia);

        // 6 — Romance: Ainda Estou Aqui (Oscar 2025 — 3 indicações, 1 vitória)
        FilmeRomance aindaEstouAqui = new FilmeRomance(6, "Ainda Estou Aqui", 2024, 135,
                "Eunice Paiva luta por justiça após o desaparecimento do marido na ditadura militar.",
                FaixaEtaria.MAIORES_14, 7.7, 3, 1, "drama romântico");
        aindaEstouAqui.adicionarStreaming(Streaming.GLOBOPLAY);
        filmes.add(aindaEstouAqui);

        // 7 — Ficção Científica: O Brutalista (Oscar 2025 — 10 indicações, 3 vitórias)
        FilmeFiccaoCientifica brutalista = new FilmeFiccaoCientifica(7, "O Brutalista", 2024, 215,
                "Um arquiteto húngaro judeu emigra para os EUA após a Segunda Guerra Mundial.",
                FaixaEtaria.MAIORES_14, 7.5, 10, 3, "pós-guerra");
        brutalista.adicionarStreaming(Streaming.PARAMOUNT_PLUS);
        filmes.add(brutalista);

        // 8 — Thriller: Zona de Interesse (Oscar 2024 — 5 indicações, 2 vitórias)
        FilmeThriller zonaDeInteresse = new FilmeThriller(8, "Zona de Interesse", 2023, 105,
                "O comandante de Auschwitz e sua família vivem ao lado do campo de concentração.",
                FaixaEtaria.MAIORES_16, 7.4, 5, 2, "psicológico");
        zonaDeInteresse.adicionarStreaming(Streaming.GLOBOPLAY);
        filmes.add(zonaDeInteresse);

        // 9 — Documentário: Sugarcane (Oscar 2025 — 1 indicação)
        FilmeDocumentario sugarcane = new FilmeDocumentario(9, "Sugarcane", 2024, 107,
                "Investigação sobre abusos em um internato para indígenas no Canadá.",
                FaixaEtaria.MAIORES_16, 7.6, 1, 0, "social");
        sugarcane.adicionarStreaming(Streaming.DISNEY_PLUS);
        filmes.add(sugarcane);

        // 10 — Drama: Killers of the Flower Moon (Oscar 2024 — 10 indicações)
        FilmeDrama killersFlowerMoon = new FilmeDrama(10, "Killers of the Flower Moon", 2023, 206,
                "Os assassinatos de membros da nação Osage nos anos 1920 pelo interesse em seus direitos de petróleo.",
                FaixaEtaria.MAIORES_16, 7.6, 10, 0, "crime histórico");
        killersFlowerMoon.adicionarStreaming(Streaming.APPLE_TV);
        filmes.add(killersFlowerMoon);

        // 11 — Comédia: Anora (Oscar 2025 — 6 indicações, 5 vitórias)
        FilmeComedia anora = new FilmeComedia(11, "Anora", 2024, 139,
                "Uma jovem striper de Brooklyn se casa com o filho de um oligarca russo.",
                FaixaEtaria.MAIORES_18, 7.4, 6, 5, "comédia dramática");
        anora.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(anora);

        // 12 — Animação: Robot Selvagem (Oscar 2025 — 3 indicações)
        FilmeAnimacao robotSelvagem = new FilmeAnimacao(12, "Robot Selvagem", 2024, 101,
                "Um robô náufrago em uma ilha selvagem aprende a sobreviver adotando um filhote de ganso.",
                FaixaEtaria.LIVRE, 8.1, 3, 0, "CGI");
        robotSelvagem.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(robotSelvagem);

        // 13 — Drama: Conclave (Oscar 2025 — 8 indicações)
        FilmeDrama conclave = new FilmeDrama(13, "Conclave", 2024, 120,
                "Após a morte do Papa, o decano dos cardeais descobre um segredo que pode abalar a Igreja.",
                FaixaEtaria.MAIORES_12, 7.3, 8, 0, "religião e poder");
        conclave.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(conclave);

        // 14 — Ficção Científica: Interestelar (Oscar 2015 — 5 indicações, 1 vitória — clássico)
        FilmeFiccaoCientifica interestelar = new FilmeFiccaoCientifica(14, "Interestelar", 2014, 169,
                "Exploradores viajam através de um buraco de minhoca no espaço para garantir a sobrevivência da humanidade.",
                FaixaEtaria.MAIORES_12, 8.7, 5, 1, "espaço");
        interestelar.adicionarStreaming(Streaming.AMAZON_PRIME);
        interestelar.adicionarStreaming(Streaming.PARAMOUNT_PLUS);
        filmes.add(interestelar);

        // 15 — Terror: Nosferatu (Oscar 2025 — 4 indicações)
        FilmeTerror nosferatu = new FilmeTerror(15, "Nosferatu", 2024, 132,
                "Uma jovem mulher na Alemanha do século XIX é assombrada por um vampiro obsessivo.",
                FaixaEtaria.MAIORES_16, 7.1, 4, 0, "sobrenatural");
        nosferatu.adicionarStreaming(Streaming.HBO_MAX);
        filmes.add(nosferatu);

        // 16 — Ação: Barbie (Oscar 2024 — 8 indicações, 1 vitória)
        FilmeAcao barbie = new FilmeAcao(16, "Barbie", 2023, 114,
                "Barbie sofre uma crise existencial e embarca em uma aventura no mundo real.",
                FaixaEtaria.MAIORES_12, 6.8, 8, 1, 4);
        barbie.adicionarStreaming(Streaming.HBO_MAX);
        barbie.adicionarStreaming(Streaming.GLOBOPLAY);
        filmes.add(barbie);

        // 17 — Thriller: Emilia Pérez (Oscar 2025 — 13 indicações, 4 vitórias)
        FilmeThriller emiliaPerez = new FilmeThriller(17, "Emilia Pérez", 2024, 132,
                "Um advogado ajuda um líder de cartel a desaparecer e recomeçar como mulher.",
                FaixaEtaria.MAIORES_16, 6.6, 13, 4, "crime");
        emiliaPerez.adicionarStreaming(Streaming.NETFLIX);
        filmes.add(emiliaPerez);

        // 18 — Documentário: Porcelain War (Oscar 2025 — 1 indicação)
        FilmeDocumentario porcelainWar = new FilmeDocumentario(18, "Porcelain War", 2024, 87,
                "Artistas ucranianos criam arte em porcelana em meio à guerra com a Rússia.",
                FaixaEtaria.MAIORES_14, 7.8, 1, 0, "guerra");
        porcelainWar.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(porcelainWar);

        // 19 — Romance: Passadas (Oscar 2024 — 2 indicações)
        FilmeRomance passadas = new FilmeRomance(19, "Passadas", 2023, 106,
                "Dois amigos de infância imigrantes coreanos se reencontram em Nova York décadas depois.",
                FaixaEtaria.MAIORES_12, 7.8, 2, 0, "drama romântico");
        passadas.adicionarStreaming(Streaming.AMAZON_PRIME);
        filmes.add(passadas);

        // 20 — Drama: Um Completo Desconhecido (Oscar 2025 — 8 indicações)
        FilmeDrama completoDesconhecido = new FilmeDrama(20, "Um Completo Desconhecido", 2024, 141,
                "A história dos primeiros anos de Bob Dylan na cena folk de Nova York.",
                FaixaEtaria.MAIORES_14, 7.4, 8, 0, "música e biografia");
        completoDesconhecido.adicionarStreaming(Streaming.DISNEY_PLUS);
        filmes.add(completoDesconhecido);

        return filmes;
    }
}
