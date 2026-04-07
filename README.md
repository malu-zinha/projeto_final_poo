# CineOscar — Sistema de Recomendação de Filmes

Sistema de recomendação de filmes indicados ao Oscar (2025–2026), desenvolvido em Java como projeto final da disciplina de Programação Orientada a Objetos.

O sistema filtra e recomenda filmes com base no perfil do usuário: **idade**, **gêneros preferidos**, **plataformas de streaming assinadas** e **faixa de duração desejada**. Conta com um frontend React e uma API Python que se conectam ao backend Java.

---

## Como executar

**Pré-requisitos:** Java 11+, Python 3+, Node.js 18+.

### 1. Clonar o repositório

```bash
git clone https://github.com/malu-zinha/projeto_final_poo.git
cd projeto_final_poo
```

### 2. Criar o arquivo `.env`

Na **raiz do repositório** (mesmo nível do `src/`), crie um arquivo chamado `.env` com o seguinte conteúdo:

```
SUPABASE_URL=https://itymvyqgujbpmqscfspz.supabase.co
SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Iml0eW12eXFndWpicG1xc2Nmc3B6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzQ3Mjc2MzYsImV4cCI6MjA5MDMwMzYzNn0.M0EBMm1ws6uJRDyNegap_N4NgP_Zbb7OPb20VDGsMEw
```

> Sem o `.env`, o sistema funciona em modo offline (dados ficam apenas em memória).

### 3. Compilar o Java

```bash
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
```

### 4. Rodar apenas o terminal (CLI)

```bash
java -cp out main.Main
```

### 5. Rodar com frontend web

**Terminal 1 — API Python:**
```bash
cd api
pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```

**Terminal 2 — Frontend React:**
```bash
cd frontend
npm install
npm run dev
```

Acesse http://localhost:8080.

---

## Contas de teste

Para facilitar a avaliação, existem 4 contas pré-cadastradas no Supabase com idades diferentes.

| Email                  | Senha    | Idade | Acesso                                   |
|------------------------|----------|-------|------------------------------------------|
| `cliente12@gmail.com`  | `123456` | 12    | Filmes até 12 anos                       |
| `cliente14@gmail.com`  | `123456` | 14    | Filmes até 14 anos                       |
| `cliente16@gmail.com`  | `123456` | 16    | Filmes até 16 anos                       |
| `cliente18@gmail.com`  | `123456` | 18    | Todos os filmes (incluindo 18+)          |

---

## Classificação indicativa

O sistema implementa 7 faixas etárias que restringem quais filmes cada usuário pode ver:

| Faixa     | Idade mínima | Exemplos de filmes                                      |
|-----------|-------------|---------------------------------------------------------|
| **Livre** | 0           | Elio, A pequena Amélie, Flow, Divertida Mente 2        |
| **6+**    | 6           | Zootopia 2                                              |
| **10+**   | 10          | Guerreiras do K-Pop, Arco, Wicked, Wallace & Gromit     |
| **12+**   | 12          | F1                                                      |
| **14+**   | 14          | Hamnet, Avatar: Fogo e Cinzas, Ainda Estou Aqui, Anora  |
| **16+**   | 16          | Pecadores, Blue Moon, Nosferatu, Emília Pérez           |
| **18+**   | 18          | Frankenstein, Bugonia, A Substância, A Meia-Irmã Feia   |

Um usuário **nunca** recebe recomendações de filmes acima da sua faixa etária.

---

## Funcionalidades

### 1. Cadastro de usuário
Cria uma conta com nome, email, senha e idade. A idade determina quais filmes serão visíveis. Os dados são persistidos no Supabase.

### 2. Login
Autentica o usuário e carrega seu perfil completo (gêneros preferidos, streamings assinados e faixa de duração) do banco de dados.

### 3. Configuração de preferências
Após o login, o usuário pode configurar:
- **Gêneros preferidos** — Drama, Comédia, Ação, Animação, Terror, Romance, Ficção Científica, Thriller, Documentário, Musical, Crime, Suspense, Mistério, Aventura, Fantasia, Guerra, História, Esporte, Sátira, Ficção, Música (21 opções)
- **Streamings assinados** — Netflix, Amazon Prime Video, HBO Max, Disney+, Apple TV+, MUBI, Globoplay, Mercado Play, Google Play, Claro TV+, Youtube, Telecine (12 opções)
- **Duração mínima e máxima** — em minutos (padrão: 60–240)

As preferências são salvas no Supabase e carregadas automaticamente no próximo login.

### 4. Recomendações personalizadas
O sistema pontua cada filme com base em 4 critérios:

| Critério           | Peso |
|--------------------|------|
| Gênero preferido   | 3    |
| Streaming assinado | 2    |
| Duração compatível | 2    |
| Faixa etária OK    | 1    |

Os filmes são filtrados (faixa etária → gênero → duração → streaming) e ordenados por pontuação decrescente. Filmes podem ter **múltiplos gêneros** — basta um deles coincidir com os preferidos do usuário.

### 5. Busca por título
Busca um filme específico pelo nome e exibe detalhes: gêneros, duração, classificação indicativa, ano do Oscar e streamings disponíveis.

### 6. Listagem por gênero
Exibe todos os filmes que contenham o gênero escolhido (um filme pode aparecer em mais de um gênero).

### 7. Catálogo completo
Lista todos os 70 filmes do catálogo com título, gêneros, duração, classificação indicativa, ano do Oscar e plataformas de streaming.

---

## Catálogo

O catálogo contém **70 filmes** reais indicados ao Oscar, divididos em duas edições:

- **Oscar 2025** — 35 filmes (Anora, Ainda Estou Aqui, Duna: Parte 2, etc.)
- **Oscar 2026** — 35 filmes (Pecadores, Frankenstein, Avatar: Fogo e Cinzas, etc.)

Os dados são carregados do arquivo `filmes.json` e distribuídos em **21 gêneros** e **12 plataformas de streaming**. Cada filme possui:
- Título
- Gêneros (um ou mais)
- Classificação indicativa (Livre, 6+, 10+, 12+, 14+, 16+, 18+)
- Duração
- Plataformas de streaming disponíveis
- Ano do Oscar (2025 ou 2026)

---

## Arquitetura Java — Fluxo das classes

### Visão geral

```
Main (CLI) ──────┐
ApiHandler (API) ─┤──> PlataformaRecomendacao ──> SistemaRecomendacao
                  │         │                          │
                  │         ├──> CatalogoOscar ────> filmes.json
                  │         ├──> SupabaseClient ───> Supabase (nuvem)
                  │         └──> Usuario / Filme
                  │
                  └──> Modelo (Filme, Usuario, Enums, Exceções)
```

### Package `modelo` — Classes de domínio (18 arquivos)

**`Filme` (abstrata):** Base da hierarquia de herança. Cada filme tem `titulo`, `duracaoMinutos`, `faixaEtaria`, `oscarAno`, uma lista de `generos` (ArrayList) e uma lista de `streamings` (ArrayList). O método abstrato `getGenero()` é implementado por cada subclasse, retornando o gênero principal — isso demonstra **polimorfismo dinâmico**.

**13 subclasses concretas:** `FilmeDrama`, `FilmeComedia`, `FilmeAcao`, `FilmeAnimacao`, `FilmeTerror`, `FilmeRomance`, `FilmeFiccaoCientifica`, `FilmeThriller`, `FilmeDocumentario`, `FilmeMusical`, `FilmeCrime`, `FilmeSuspense`, `FilmeGuerra`. Cada uma sobrescreve `getGenero()` retornando seu respectivo enum. A subclasse é determinada pelo **primeiro gênero** do filme no JSON; os demais gêneros são armazenados na lista `generos` do objeto.

**`Usuario`:** Perfil do usuário com preferências (gêneros preferidos, streamings assinados, faixa de duração). O método `isCompativel(Filme)` verifica se um filme atende às preferências do usuário considerando **todos os gêneros** do filme. Campos `supabaseId` e `accessToken` são usados para integração com o banco.

**`Genero` (enum):** 21 valores (Drama, Comédia, Ação, Musical, Crime, etc.). Cada valor tem uma `descricao` em português. O método `fromDescricao()` converte texto do JSON para o enum correspondente.

**`Streaming` (enum):** 12 valores (Netflix, Amazon Prime Video, HBO Max, etc.). O método `fromNome()` converte o nome da plataforma para o enum, ignorando sufixos como "(indisponível no Brasil)".

**`FaixaEtaria` (enum):** 7 valores de LIVRE a MAIORES_18, cada um com `idadeMinima`. O método `fromTexto()` converte strings como "16 anos" ou "Livre" para o enum.

### Package `servico` — Lógica de negócio (3 arquivos)

**`PlataformaRecomendacao` (Fachada):** Classe central que orquestra todo o sistema. Gerencia usuários e filmes, delega autenticação ao `SupabaseClient`, recomendações ao `SistemaRecomendacao`, e carregamento de dados ao `CatalogoOscar`. No construtor, detecta se o Supabase está configurado (via `.env`) — se não estiver, opera em modo offline. Demonstra o padrão **Facade**.

**`SistemaRecomendacao`:** Implementa o algoritmo de recomendação em 4 etapas: (1) filtra por faixa etária, (2) filtra por gêneros preferidos, (3) filtra por duração, (4) filtra por streamings. Se os filtros eliminam tudo, faz fallback para o catálogo filtrado apenas por idade. Pontua cada filme com pesos (gênero=3, streaming=2, duração=2, idade=1) e ordena decrescente. Demonstra o padrão **Strategy**.

**`CatalogoOscar`:** Lê e parseia o arquivo `filmes.json` sem dependências externas. Para cada filme, instancia a **subclasse correta** baseada no primeiro gênero (Factory pattern). Converte duração textual ("2h 17") para minutos, classificação indicativa para `FaixaEtaria`, e nomes de streaming para o enum `Streaming`.

### Package `api` — Interface para o frontend (1 arquivo)

**`ApiHandler`:** Dispatcher CLI que recebe comandos (`login`, `filmes`, `recomendados`, `preferencias_set`, etc.) via argumentos do processo Java, executa a operação correspondente em `PlataformaRecomendacao`, e retorna JSON via stdout. É invocado como subprocesso pela API Python (`java_bridge.py`).

### Package `excecao` — Exceções customizadas (4 arquivos)

- **`UsuarioJaCadastradoException`** — email já cadastrado
- **`SenhaInvalidaException`** — login com credenciais inválidas
- **`IdadeInvalidaException`** — idade ≤ 0 no cadastro
- **`FilmeNaoEncontradoException`** — busca por título/id sem resultado

Todas estendem `Exception` (checked exceptions) e possuem construtores com e sem mensagem personalizada.

### Package `infra` — Integração com Supabase (2 arquivos)

**`SupabaseConfig`:** Carrega `SUPABASE_URL` e `SUPABASE_ANON_KEY` do arquivo `.env` usando leitura simples de arquivo. O método `isConfigurado()` indica se o sistema deve usar o banco ou operar offline.

**`SupabaseClient`:** Cliente HTTP para comunicação com Supabase usando `java.net.http.HttpClient` (Java 11+). Implementa:
- **Auth:** cadastro e login de usuários
- **Perfil:** criação e carregamento de perfil (nome, email, idade, duração)
- **Preferências:** salvar/carregar gêneros e streamings preferidos

As preferências são armazenadas em 3 tabelas: `usuarios`, `usuario_generos` e `usuario_streamings`.

### Package `main` — Entrada CLI (1 arquivo)

**`Main`:** Menu interativo via `Scanner` com 8 opções (cadastrar, login, preferências, recomendações, busca, listagem por gênero, catálogo completo, sair). Exige login para operações autenticadas.

---

## Frontend e API

### API Python (`api/`)

Servidor FastAPI que atua como ponte entre o frontend React e o backend Java. Cada requisição HTTP é traduzida em uma chamada ao `ApiHandler` Java via subprocesso.

- `POST /auth/login` e `POST /auth/cadastro` — autenticação
- `GET /filmes` — catálogo completo
- `GET /filmes/recomendados` — recomendações (requer token)
- `GET /preferencias` e `PUT /preferencias` — preferências do usuário

### Frontend React (`frontend/`)

Interface web construída com React + Vite + Tailwind CSS + shadcn/ui. Páginas:
- **Login / Cadastro** — formulários de autenticação
- **Recomendados** — filmes recomendados com base nas preferências
- **Preferências** — seleção de gêneros, streamings e faixa de duração
- **Catálogo** — todos os filmes com filtro por gênero e busca por título

---

## Conceitos de POO aplicados

| Conceito                    | Onde no código                                                          |
|-----------------------------|-------------------------------------------------------------------------|
| **Encapsulamento**          | Atributos `private` + getters/setters em todas as classes modelo        |
| **Herança**                 | `Filme` (abstrata) → 13 subclasses concretas por gênero                |
| **Polimorfismo dinâmico**   | Método abstrato `getGenero()` sobrescrito em cada subclasse de Filme    |
| **Polimorfismo estático**   | Sobrecarga de `buscarFilme(String)` e `buscarFilme(int)`               |
| **Abstração**               | Classe `Filme` abstrata define o contrato para todos os gêneros         |
| **Exceções customizadas**   | 4 exceções checked: UsuarioJaCadastrado, SenhaInvalida, etc.           |
| **Enums com atributos**     | `Genero` (21), `Streaming` (12), `FaixaEtaria` (7) com campos próprios |
| **Composição (ArrayList)**  | `Usuario` possui listas de gêneros e streamings; `Filme` possui listas  |
| **try/catch/finally**       | Tratamento de exceções no cadastro e login                              |
| **Factory Method**          | `CatalogoOscar.criarFilmePorGenero()` instancia a subclasse correta    |
| **Facade**                  | `PlataformaRecomendacao` centraliza acesso ao sistema                   |
| **Strategy**                | `SistemaRecomendacao` encapsula o algoritmo de pontuação                |

---

## Estrutura do projeto

```
projeto_final_poo/
├── filmes.json                          # Catálogo de 70 filmes (Oscar 2025–2026)
├── .env                                 # Credenciais do Supabase
├── supabase_setup.sql                   # SQL para criar tabelas no Supabase
├── src/main/java/
│   ├── main/
│   │   └── Main.java                    # Menu interativo CLI
│   ├── api/
│   │   └── ApiHandler.java              # Dispatcher CLI para a API Python
│   ├── modelo/
│   │   ├── Filme.java                   # Classe abstrata base
│   │   ├── FilmeDrama.java              # 13 subclasses por gênero
│   │   ├── FilmeComedia.java
│   │   ├── FilmeAcao.java
│   │   ├── FilmeAnimacao.java
│   │   ├── FilmeTerror.java
│   │   ├── FilmeRomance.java
│   │   ├── FilmeFiccaoCientifica.java
│   │   ├── FilmeThriller.java
│   │   ├── FilmeDocumentario.java
│   │   ├── FilmeMusical.java            # Novas subclasses
│   │   ├── FilmeCrime.java
│   │   ├── FilmeSuspense.java
│   │   ├── FilmeGuerra.java
│   │   ├── Usuario.java                 # Perfil do usuário
│   │   ├── Genero.java                  # Enum (21 gêneros)
│   │   ├── Streaming.java               # Enum (12 plataformas)
│   │   └── FaixaEtaria.java             # Enum (7 faixas etárias)
│   ├── servico/
│   │   ├── PlataformaRecomendacao.java   # Fachada principal
│   │   ├── SistemaRecomendacao.java      # Algoritmo de recomendação
│   │   └── CatalogoOscar.java           # Parser de filmes.json
│   ├── excecao/
│   │   ├── UsuarioJaCadastradoException.java
│   │   ├── SenhaInvalidaException.java
│   │   ├── IdadeInvalidaException.java
│   │   └── FilmeNaoEncontradoException.java
│   └── infra/
│       ├── SupabaseConfig.java           # Carrega .env
│       └── SupabaseClient.java           # Cliente HTTP para Supabase
├── api/
│   ├── main.py                          # FastAPI (ponte Java ↔ frontend)
│   ├── java_bridge.py                   # Executa ApiHandler via subprocess
│   └── requirements.txt                 # fastapi, uvicorn
├── frontend/                            # React + Vite + Tailwind + shadcn/ui
│   ├── src/
│   │   ├── pages/                       # Login, Cadastro, Recomendados, etc.
│   │   ├── components/                  # FilmeCard, UI components
│   │   ├── contexts/AppContext.tsx       # Estado global da aplicação
│   │   ├── services/api.ts              # Cliente HTTP para a API Python
│   │   └── data/mock.ts                 # Tipos TypeScript e labels
│   └── vite.config.ts                   # Proxy /api → localhost:8000
└── UML/
    ├── uml-diagram.html                 # Diagrama UML interativo
    └── UML-visualization.png            # Diagrama UML em imagem
```

---

## Persistência (Supabase)

O sistema usa [Supabase](https://supabase.com) para persistir dados via HTTP nativo do Java (`java.net.http.HttpClient`) sem dependências externas.

### Tabelas

- **`usuarios`** — perfil (nome, email, idade, duracao_minima, duracao_maxima)
- **`usuario_generos`** — gêneros preferidos (relação N:N)
- **`usuario_streamings`** — streamings assinados (relação N:N)

### Serviços

- **Supabase Auth** — cadastro e login de usuários
- **PostgREST** — CRUD de perfis e preferências com RLS (cada usuário só acessa seus dados)

O arquivo `supabase_setup.sql` contém o SQL para criar as tabelas e políticas de segurança.

Sem o `.env` configurado, o sistema funciona em **modo offline** (dados ficam em memória).

---

## Tecnologias

- **Java 11+** — backend e lógica de negócio
- **Python 3 + FastAPI** — API bridge
- **React + Vite + TypeScript** — frontend
- **Tailwind CSS + shadcn/ui** — estilização
- **Supabase** — Auth + PostgreSQL via PostgREST
- Sem dependências externas no Java
