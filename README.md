# CineOscar — Sistema de Recomendação de Filmes

Sistema de recomendação de filmes indicados ao Oscar, desenvolvido em Java como projeto final da disciplina de Programação Orientada a Objetos.

O sistema filtra e recomenda filmes com base no perfil do usuário: **idade**, **gêneros preferidos**, **plataformas de streaming assinadas** e **faixa de duração desejada**.

---

## Como executar

**Pré-requisito:** Java 11 ou superior.

```bash
cd projeto-final-poo
mkdir -p out
javac -d out $(find projeto_final_poo/src/main/java -name "*.java")
java -cp out main.Main
```

---

## Contas de teste

Para facilitar a avaliação, existem 4 contas pré-cadastradas no Supabase com idades diferentes. Em produção, cada usuário cadastra sua própria conta com sua idade real.

| Email                  | Senha    | Idade | Filmes acessíveis      |
|------------------------|----------|-------|------------------------|
| `cliente12@gmail.com`  | `123456` | 12    | 6 de 20 (até 12 anos)  |
| `cliente14@gmail.com`  | `123456` | 14    | 12 de 20 (até 14 anos) |
| `cliente16@gmail.com`  | `123456` | 16    | 17 de 20 (até 16 anos) |
| `cliente18@gmail.com`  | `123456` | 18    | 20 de 20 (todos)       |

Use a **opção 2 (Login)** no menu para entrar com uma dessas contas.

---

## Classificação indicativa e filtragem por idade

O sistema implementa faixas etárias que restringem quais filmes cada usuário pode ver. Um usuário **nunca** recebe recomendações de filmes acima da sua faixa etária.

### Filmes por faixa etária

**Livre** — acessível a todos:
- Divertida Mente 2, Robot Selvagem

**12 anos** — acessível a partir de 12 anos:
- Conclave, Interestelar, Barbie, Passadas

**14 anos** — acessível a partir de 14 anos:
- Oppenheimer, Duna: Parte Dois, Ainda Estou Aqui, O Brutalista, Porcelain War, Um Completo Desconhecido

**16 anos** — acessível a partir de 16 anos:
- Zona de Interesse, Sugarcane, Killers of the Flower Moon, Nosferatu, Emilia Pérez

**18 anos** — acessível a partir de 18 anos:
- Pobres Criaturas, A Substância, Anora

### Exemplo prático

- **cliente12** vê apenas 6 filmes (Livre + 12 anos)
- **cliente14** vê 12 filmes (+ 14 anos)
- **cliente16** vê 17 filmes (+ 16 anos)
- **cliente18** vê todos os 20 filmes do catálogo

---

## Funcionalidades

### 1. Cadastro de usuário
Cria uma conta com nome, email, senha e idade. A idade determina quais filmes serão visíveis. Os dados são persistidos no Supabase.

### 2. Login
Autentica o usuário e carrega seu perfil completo (preferências de gênero, streamings e faixa de duração) do banco de dados.

### 3. Configuração de preferências
Após o login, o usuário pode configurar:
- **Gêneros preferidos** — Drama, Comédia, Ação, Animação, Terror, Romance, Ficção Científica, Thriller, Documentário
- **Streamings assinados** — Netflix, Amazon Prime Video, HBO Max, Disney+, Apple TV+, Paramount+, MUBI, Globoplay
- **Duração mínima e máxima** — em minutos (padrão: 60–240)

As preferências são salvas no Supabase e carregadas automaticamente no próximo login.

### 4. Recomendações personalizadas
O sistema de recomendação pontua cada filme com base em 5 critérios:

| Critério          | Peso |
|-------------------|------|
| Gênero preferido  | 3    |
| Streaming assinado| 2    |
| Duração compatível| 2    |
| Faixa etária OK   | 1    |
| Nota IMDB ≥ 7.5   | 1    |

Os filmes são filtrados (faixa etária, gênero, duração, streaming) e ordenados por pontuação decrescente.

### 5. Busca por título
Busca um filme específico pelo nome e exibe seus detalhes completos: sinopse, duração, nota IMDB, indicações e vitórias no Oscar, e classificação indicativa.

### 6. Listagem por gênero
Exibe todos os filmes de um gênero escolhido que sejam acessíveis à faixa etária do catálogo.

### 7. Catálogo completo
Lista todos os 20 filmes do catálogo com título, ano, gênero, duração, nota IMDB, indicações/vitórias do Oscar e plataformas de streaming.

---

## Catálogo

O catálogo contém 20 filmes reais indicados ao Oscar (2015–2025), distribuídos em 9 gêneros:

| #  | Filme                       | Gênero           | Ano  | Duração | IMDB | Oscar           | Faixa  | Streaming         |
|----|-----------------------------|------------------|------|---------|------|-----------------|--------|-------------------|
| 1  | Oppenheimer                 | Drama            | 2023 | 3h      | 8.3  | 13 ind. / 7 vit | 14+    | Amazon Prime      |
| 2  | Pobres Criaturas            | Comédia          | 2023 | 2h21    | 7.9  | 11 ind. / 4 vit | 18+    | Disney+           |
| 3  | Duna: Parte Dois            | Ação             | 2024 | 2h46    | 8.5  | 5 ind. / 1 vit  | 14+    | HBO Max           |
| 4  | Divertida Mente 2           | Animação         | 2024 | 1h40    | 7.6  | 1 ind. / 0 vit  | Livre  | Disney+           |
| 5  | A Substância                | Terror           | 2024 | 2h20    | 7.3  | 5 ind. / 1 vit  | 18+    | MUBI              |
| 6  | Ainda Estou Aqui            | Romance          | 2024 | 2h15    | 7.7  | 3 ind. / 1 vit  | 14+    | Globoplay         |
| 7  | O Brutalista                | Ficção Científica| 2024 | 3h35    | 7.5  | 10 ind. / 3 vit | 14+    | Paramount+        |
| 8  | Zona de Interesse           | Thriller         | 2023 | 1h45    | 7.4  | 5 ind. / 2 vit  | 16+    | Globoplay         |
| 9  | Sugarcane                   | Documentário     | 2024 | 1h47    | 7.6  | 1 ind. / 0 vit  | 16+    | Disney+           |
| 10 | Killers of the Flower Moon  | Drama            | 2023 | 3h26    | 7.6  | 10 ind. / 0 vit | 16+    | Apple TV+         |
| 11 | Anora                       | Comédia          | 2024 | 2h19    | 7.4  | 6 ind. / 5 vit  | 18+    | Amazon Prime      |
| 12 | Robot Selvagem              | Animação         | 2024 | 1h41    | 8.1  | 3 ind. / 0 vit  | Livre  | Amazon Prime      |
| 13 | Conclave                    | Drama            | 2024 | 2h      | 7.3  | 8 ind. / 0 vit  | 12+    | Amazon Prime      |
| 14 | Interestelar                | Ficção Científica| 2014 | 2h49    | 8.7  | 5 ind. / 1 vit  | 12+    | Amazon Prime, Paramount+ |
| 15 | Nosferatu                   | Terror           | 2024 | 2h12    | 7.1  | 4 ind. / 0 vit  | 16+    | HBO Max           |
| 16 | Barbie                      | Ação             | 2023 | 1h54    | 6.8  | 8 ind. / 1 vit  | 12+    | HBO Max, Globoplay|
| 17 | Emilia Pérez                | Thriller         | 2024 | 2h12    | 6.6  | 13 ind. / 4 vit | 16+    | Netflix           |
| 18 | Porcelain War               | Documentário     | 2024 | 1h27    | 7.8  | 1 ind. / 0 vit  | 14+    | Amazon Prime      |
| 19 | Passadas                    | Romance          | 2023 | 1h46    | 7.8  | 2 ind. / 0 vit  | 12+    | Amazon Prime      |
| 20 | Um Completo Desconhecido    | Drama            | 2024 | 2h21    | 7.4  | 8 ind. / 0 vit  | 14+    | Disney+           |

---

## Conceitos de POO aplicados

| Conceito                    | Onde no código                                                        |
|-----------------------------|-----------------------------------------------------------------------|
| **Encapsulamento**          | Atributos `private` + getters/setters em todas as classes modelo      |
| **Herança**                 | `Filme` (abstrata) → `FilmeDrama`, `FilmeComedia`, `FilmeAcao`, etc.  |
| **Polimorfismo dinâmico**   | Método abstrato `getGenero()` sobrescrito em cada subclasse de Filme  |
| **Polimorfismo estático**   | Sobrecarga de `buscarFilme(String)` e `buscarFilme(int)`              |
| **Abstração**               | Classe `Filme` abstrata define o contrato para todos os gêneros       |
| **Exceções customizadas**   | `UsuarioJaCadastradoException`, `SenhaInvalidaException`, etc.        |
| **Enums com atributos**     | `Genero`, `Streaming`, `FaixaEtaria` com campos e métodos próprios    |
| **Composição (ArrayList)**  | `Usuario` possui listas de gêneros e streamings preferidos            |
| **try/catch/finally**       | Tratamento de exceções no cadastro e login com bloco finally          |

---

## Estrutura do projeto

```
projeto_final_poo/src/main/java/
├── main/
│   └── Main.java                    # Menu interativo (Scanner)
├── modelo/
│   ├── Filme.java                   # Classe abstrata base
│   ├── FilmeDrama.java              # Subclasses por gênero
│   ├── FilmeComedia.java
│   ├── FilmeAcao.java
│   ├── FilmeAnimacao.java
│   ├── FilmeTerror.java
│   ├── FilmeRomance.java
│   ├── FilmeFiccaoCientifica.java
│   ├── FilmeThriller.java
│   ├── FilmeDocumentario.java
│   ├── Usuario.java                 # Perfil do usuário
│   ├── Genero.java                  # Enum de gêneros
│   ├── Streaming.java               # Enum de plataformas
│   └── FaixaEtaria.java             # Enum de classificação indicativa
├── servico/
│   ├── PlataformaRecomendacao.java   # Fachada principal do sistema
│   ├── SistemaRecomendacao.java      # Algoritmo de pontuação e filtros
│   └── CatalogoOscar.java           # Dados dos 20 filmes
├── excecao/
│   ├── UsuarioJaCadastradoException.java
│   ├── SenhaInvalidaException.java
│   ├── IdadeInvalidaException.java
│   └── FilmeNaoEncontradoException.java
└── infra/
    ├── SupabaseConfig.java           # Carrega credenciais do .env
    └── SupabaseClient.java           # Cliente HTTP para Supabase
```

---

## Persistência (Supabase)

O sistema usa [Supabase](https://supabase.com) para persistir dados de usuários e preferências. A comunicação é feita via HTTP nativo do Java (`java.net.http.HttpClient`) sem dependências externas.

- **Supabase Auth** — cadastro e login de usuários
- **PostgREST** — CRUD de perfis, gêneros preferidos e streamings assinados

Sem o arquivo `.env` configurado, o sistema funciona em **modo offline** (dados ficam em memória durante a execução).

---

## Tecnologias

- Java 11+
- Supabase (Auth + PostgreSQL via PostgREST)
- Sem dependências externas
