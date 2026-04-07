import type { Filme, Genero, Streaming } from '@/data/mock';

const BASE = '/api';

async function request(url: string, options: RequestInit = {}) {
  const { headers, ...rest } = options;
  const res = await fetch(BASE + url, {
    ...rest,
    headers: { 'Content-Type': 'application/json', ...(headers as Record<string, string>) },
  });
  const data = await res.json();
  if (!res.ok) throw new Error(data.detail || 'Erro na requisição');
  return data;
}

function mapFilme(raw: any): Filme {
  return {
    id: raw.id,
    titulo: raw.titulo,
    duracaoMinutos: raw.duracao,
    faixaEtaria: raw.faixa_etaria,
    streamings: raw.streamings,
    generos: raw.generos,
    oscarAno: raw.oscar_ano,
  };
}

export interface LoginResponse {
  nome: string;
  email: string;
  idade: number;
  token: string;
  generos: Genero[];
  streamings: Streaming[];
  duracao_minima: number;
  duracao_maxima: number;
}

export async function apiLogin(email: string, senha: string): Promise<LoginResponse> {
  return request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, senha }),
  });
}

export async function apiCadastro(nome: string, email: string, senha: string, idade: number) {
  return request('/auth/cadastro', {
    method: 'POST',
    body: JSON.stringify({ nome, email, senha, idade }),
  });
}

export async function apiGetFilmes(): Promise<Filme[]> {
  const raw = await request('/filmes');
  return (raw as any[]).map(mapFilme);
}

export async function apiGetRecomendados(token: string): Promise<Filme[]> {
  const raw = await request('/filmes/recomendados', {
    headers: { Authorization: token },
  });
  return (raw as any[]).map(mapFilme);
}

export async function apiGetPreferencias(token: string) {
  return request('/preferencias', {
    headers: { Authorization: token },
  });
}

export async function apiSalvarPreferencias(
  token: string,
  generos: Genero[],
  streamings: Streaming[],
  duracaoMin: number,
  duracaoMax: number,
) {
  return request('/preferencias', {
    method: 'PUT',
    body: JSON.stringify({ generos, streamings, duracao_min: duracaoMin, duracao_max: duracaoMax }),
    headers: { Authorization: token },
  });
}
