# CineOscar — Sistema de Recomendação de Filmes do Oscar

**Projeto Final — Programação Orientada a Objetos**

**Integrantes:**
- Maria Luíza Uchoa Cavalcanti
- Larissa Gondim Vilasboas
- Laura Morais Tiné de Oliveira
- Luis Augusto Melo de Queiroz Oliveira

**Repositório:** [github.com/malu-zinha/projeto_final_poo](https://github.com/malu-zinha/projeto_final_poo)

---

## 1. Introdução

O CineOscar é um sistema de recomendação de filmes indicados ao Oscar, desenvolvido como projeto final da disciplina de Programação Orientada a Objetos. O problema abordado é a dificuldade que espectadores enfrentam ao escolher filmes em meio a um catálogo extenso de produções premiadas, distribuídas em diversas plataformas de streaming, com diferentes classificações indicativas e gêneros.

A proposta do sistema é oferecer recomendações personalizadas com base no perfil de cada usuário. Ao se cadastrar, o usuário informa sua idade — que determina quais filmes ele pode acessar de acordo com a classificação indicativa — e depois configura suas preferências: gêneros favoritos, plataformas de streaming que assina e faixa de duração desejada. Com essas informações, o sistema pontua e filtra os filmes do catálogo, apresentando uma lista ordenada por relevância.

O catálogo contém 70 filmes reais indicados ao Oscar nas edições de 2025 e 2026, organizados em 21 gêneros e disponíveis em 12 plataformas de streaming. O sistema implementa 7 faixas de classificação indicativa (Livre, 6+, 10+, 12+, 14+, 16+ e 18+), garantindo que um usuário menor de idade nunca receba recomendações de conteúdo inadequado.

Além da interface de linha de comando (CLI), o projeto conta com uma interface web completa, composta por um frontend React e uma API Python que se conectam ao backend Java, permitindo que toda a lógica de negócio — cadastro, autenticação, recomendações e preferências — seja acessada de forma visual e interativa pelo navegador.

---

## 2. Modelagem do Problema

### 2.1 Diagrama UML

O diagrama UML completo do sistema está disponível nos arquivos `UML/uml-diagram.html` (interativo) e `UML/UML-visualization.png` (imagem estática) no repositório. A seguir, descrevemos a modelagem em termos de classes e relacionamentos.

### 2.2 Classes e Relacionamentos

A hierarquia central do sistema é a classe abstrata **`Filme`**, que define os atributos comuns a todos os filmes: título, duração em minutos, classificação indicativa (`FaixaEtaria`), ano do Oscar, uma lista de gêneros (`ArrayList<Genero>`) e uma lista de streamings (`ArrayList<Streaming>`). O método abstrato `getGenero()` obriga cada subclasse a declarar seu gênero principal — o ponto de **polimorfismo dinâmico** do projeto. Existem **13 subclasses concretas**: `FilmeDrama`, `FilmeComedia`, `FilmeAcao`, `FilmeAnimacao`, `FilmeTerror`, `FilmeRomance`, `FilmeFiccaoCientifica`, `FilmeThriller`, `FilmeDocumentario`, `FilmeMusical`, `FilmeCrime`, `FilmeSuspense` e `FilmeGuerra`, cada uma retornando seu respectivo enum em `getGenero()`.

A classe **`Usuario`** representa o perfil do usuário, contendo nome, email, senha, idade e suas preferências configuráveis: `ArrayList<Genero>` para gêneros preferidos, `ArrayList<Streaming>` para streamings assinados, e duração mínima/máxima desejada. O método `isCompativel(Filme)` verifica se um filme atende a todas as preferências do usuário.

Três **enums com atributos** complementam o modelo: `Genero` (21 valores, cada um com descrição em português), `Streaming` (12 plataformas, cada uma com nome legível) e `FaixaEtaria` (7 faixas, cada uma com idade mínima). Todos possuem métodos estáticos de conversão (`fromDescricao`, `fromNome`, `fromTexto`) para transformar strings do JSON em enums.

### 2.3 Camada de Serviço

**`PlataformaRecomendacao`** atua como **fachada (Facade)** do sistema, centralizando operações de cadastro, login, configuração de preferências e recomendações. Internamente, delega o carregamento de dados ao `CatalogoOscar`, o algoritmo de recomendação ao `SistemaRecomendacao`, e a persistência ao `SupabaseClient`.

**`SistemaRecomendacao`** implementa o algoritmo de pontuação com 4 critérios ponderados (gênero=3, streaming=2, duração=2, faixa etária=1) e 4 filtros encadeados (idade → gênero → duração → streaming), com fallback para o catálogo filtrado apenas por idade caso os filtros eliminem todos os resultados. Esse encapsulamento do algoritmo segue o padrão **Strategy**.

**`CatalogoOscar`** lê o arquivo `filmes.json` e instancia a subclasse correta de `Filme` com base no primeiro gênero de cada entrada, aplicando o padrão **Factory Method** via `criarFilmePorGenero()`.

### 2.4 Conceitos de POO Aplicados

| Conceito | Aplicação no projeto |
|---|---|
| **Encapsulamento** | Todos os atributos são `private` com acesso via getters/setters |
| **Herança** | `Filme` (abstrata) com 13 subclasses concretas |
| **Polimorfismo dinâmico** | Método abstrato `getGenero()` sobrescrito em cada subclasse |
| **Polimorfismo estático** | Sobrecarga de `buscarFilme(String)` e `buscarFilme(int)` |
| **Abstração** | Classe `Filme` define o contrato para todos os gêneros |
| **Exceções customizadas** | 4 exceções checked com hierarquia `extends Exception` |
| **Enums com atributos** | `Genero`, `Streaming` e `FaixaEtaria` com campos e métodos |
| **Collections (ArrayList)** | Usado extensivamente em todas as classes do projeto |
| **try/catch/finally** | Tratamento de erros no cadastro, login e comunicação com o banco |
| **Factory Method** | `CatalogoOscar.criarFilmePorGenero()` instancia a subclasse correta |
| **Facade** | `PlataformaRecomendacao` centraliza todo o acesso ao sistema |

---

## 3. Ferramentas Utilizadas

### 3.1 Linguagem e Ambiente

- **Java 11+** — linguagem principal do backend e toda a lógica de negócio. Utiliza `java.net.http.HttpClient` para comunicação HTTP com o Supabase, sem nenhuma dependência externa.
- **Python 3 + FastAPI** — API intermediária que conecta o frontend ao Java. Cada requisição HTTP é traduzida em uma chamada ao processo Java via `subprocess`.
- **TypeScript + React** — frontend web com tipagem estática e componentes reutilizáveis.

### 3.2 IDE e Ferramentas de Desenvolvimento

- **Cursor (VS Code)** — IDE principal utilizada para todo o desenvolvimento.
- **Git + GitHub** — controle de versão e hospedagem do repositório.
- **Supabase** — plataforma de banco de dados PostgreSQL na nuvem, utilizada para autenticação de usuários (Supabase Auth) e persistência de perfis e preferências (PostgREST com Row Level Security).

### 3.3 Frameworks e Bibliotecas

- **FastAPI + Uvicorn** — servidor web Python para a API bridge.
- **Vite** — build tool para o frontend React.
- **Tailwind CSS + shadcn/ui** — estilização e componentes de interface.

### 3.4 Estrutura de Pacotes

O código Java está organizado em 6 pacotes:

| Pacote | Arquivos | Responsabilidade |
|---|---|---|
| `modelo` | 18 | Classes de domínio: `Filme` (abstrata), 13 subclasses, `Usuario`, 3 enums |
| `servico` | 3 | Lógica de negócio: fachada, recomendação, catálogo |
| `excecao` | 4 | Exceções customizadas: cadastro, login, idade, busca |
| `infra` | 2 | Integração com Supabase: configuração e cliente HTTP |
| `api` | 1 | Interface CLI para comunicação com a API Python |
| `main` | 1 | Ponto de entrada do menu interativo (CLI) |

O projeto completo inclui também a pasta `api/` (FastAPI em Python), `frontend/` (React + Vite) e `UML/` (diagrama de classes).

---

## 4. Resultados e Considerações Finais

### 4.1 Resultados Alcançados

O sistema final atende a todos os requisitos propostos. O catálogo de 70 filmes é carregado do arquivo JSON e transformado em objetos Java que compõem uma hierarquia de herança com 13 subclasses. O algoritmo de recomendação pondera 4 critérios e aplica filtros encadeados para entregar sugestões relevantes ao perfil de cada usuário. A classificação indicativa impede que menores de idade acessem conteúdo inadequado, e as preferências (gêneros, streamings e duração) são persistidas no Supabase, sendo restauradas automaticamente a cada login.

A interface web permite que o usuário realize login, configure preferências com sliders e checkboxes, navegue pelo catálogo completo com filtros por gênero, e visualize suas recomendações personalizadas — tudo conectado ao mesmo backend Java que opera pela CLI.

### 4.2 Dificuldades Encontradas

A principal dificuldade técnica foi a integração entre as três camadas (React → Python → Java). Como o Java é executado como subprocesso a cada requisição, existe um atraso perceptível de alguns segundos por operação, já que a JVM precisa inicializar a cada chamada. Essa limitação arquitetural seria resolvida em produção com um servidor Java persistente, mas para o escopo do projeto a solução via subprocess atendeu bem.

Outro desafio foi o parsing de JSON sem bibliotecas externas no Java. Como o projeto não utiliza dependências externas, foi necessário implementar extratores manuais de strings e arrays JSON tanto no `CatalogoOscar` quanto no `ApiHandler`. Isso exigiu cuidado com caracteres especiais e estruturas aninhadas.

A integração com o Supabase também apresentou particularidades, como a necessidade de tratar valores nulos retornados pelo banco (campos de duração não preenchidos) e a implementação de Row Level Security para que cada usuário só acesse seus próprios dados.

### 4.3 Aprendizagem

O projeto proporcionou uma compreensão prática dos pilares da Programação Orientada a Objetos. A hierarquia `Filme` → 13 subclasses tornou concreto o conceito de herança e polimorfismo: cada subclasse é instanciada pelo Factory Method e tratada uniformemente como `Filme` em todo o sistema, enquanto o método `getGenero()` resolve em tempo de execução qual gênero retornar. O encapsulamento ficou evidente na proteção dos atributos com `private` e no controle de acesso via getters/setters, e as exceções customizadas ensinaram a modelar erros como parte do domínio do problema.

Além do paradigma OO, o projeto exercitou a integração de múltiplas tecnologias em um sistema coeso, a organização de código em pacotes com responsabilidades definidas, e a importância de padrões de projeto (Facade, Strategy, Factory Method) para manter o código legível e extensível.
