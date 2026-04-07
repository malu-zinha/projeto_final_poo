-- ============================================================
-- Tabelas para persistir preferências de usuários no Supabase
-- Rodar no SQL Editor do Supabase (https://supabase.com/dashboard)
-- ============================================================

-- 1. Tabela de perfis de usuários
create table if not exists usuarios (
  id uuid default gen_random_uuid() primary key,
  auth_id uuid references auth.users(id) on delete cascade unique not null,
  nome text not null,
  email text not null,
  idade int not null default 18,
  duracao_minima int not null default 60,
  duracao_maxima int not null default 240,
  created_at timestamptz default now()
);

-- 2. Tabela de gêneros preferidos (relação N:N)
create table if not exists usuario_generos (
  usuario_id uuid references usuarios(id) on delete cascade not null,
  genero text not null,
  primary key (usuario_id, genero)
);

-- 3. Tabela de streamings assinados (relação N:N)
create table if not exists usuario_streamings (
  usuario_id uuid references usuarios(id) on delete cascade not null,
  streaming text not null,
  primary key (usuario_id, streaming)
);

-- ============================================================
-- RLS (Row Level Security) — cada usuário só acessa seus dados
-- ============================================================

alter table usuarios enable row level security;
alter table usuario_generos enable row level security;
alter table usuario_streamings enable row level security;

-- Políticas para "usuarios"
create policy "usuarios_select" on usuarios
  for select using (auth.uid() = auth_id);

create policy "usuarios_insert" on usuarios
  for insert with check (auth.uid() = auth_id);

create policy "usuarios_update" on usuarios
  for update using (auth.uid() = auth_id);

create policy "usuarios_delete" on usuarios
  for delete using (auth.uid() = auth_id);

-- Políticas para "usuario_generos"
create policy "generos_select" on usuario_generos
  for select using (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );

create policy "generos_insert" on usuario_generos
  for insert with check (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );

create policy "generos_delete" on usuario_generos
  for delete using (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );

-- Políticas para "usuario_streamings"
create policy "streamings_select" on usuario_streamings
  for select using (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );

create policy "streamings_insert" on usuario_streamings
  for insert with check (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );

create policy "streamings_delete" on usuario_streamings
  for delete using (
    usuario_id in (select id from usuarios where auth_id = auth.uid())
  );
