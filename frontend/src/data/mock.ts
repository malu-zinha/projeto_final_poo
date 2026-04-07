export type Genero =
  | 'DRAMA' | 'COMEDIA' | 'ACAO' | 'ANIMACAO' | 'TERROR'
  | 'ROMANCE' | 'FICCAO_CIENTIFICA' | 'THRILLER' | 'DOCUMENTARIO'
  | 'MUSICAL' | 'CRIME' | 'SUSPENSE' | 'MISTERIO' | 'AVENTURA'
  | 'FANTASIA' | 'GUERRA' | 'HISTORIA' | 'ESPORTE' | 'SATIRA'
  | 'FICCAO' | 'MUSICA';

export type Streaming =
  | 'NETFLIX' | 'AMAZON_PRIME' | 'HBO_MAX' | 'DISNEY_PLUS'
  | 'APPLE_TV' | 'MUBI' | 'GLOBOPLAY'
  | 'MERCADO_PLAY' | 'GOOGLE_PLAY' | 'CLARO_TV_PLUS' | 'YOUTUBE' | 'TELECINE';

export type FaixaEtaria =
  | 'LIVRE' | 'MAIORES_6' | 'MAIORES_10' | 'MAIORES_12'
  | 'MAIORES_14' | 'MAIORES_16' | 'MAIORES_18';

export interface Filme {
  id: number;
  titulo: string;
  duracaoMinutos: number;
  faixaEtaria: FaixaEtaria;
  streamings: Streaming[];
  generos: Genero[];
  oscarAno: string;
}

export interface Usuario {
  id: string;
  nome: string;
  email: string;
  senha: string;
  idade: number;
  generosPreferidos: Genero[];
  streamingsAssinados: Streaming[];
  duracaoMinima: number;
  duracaoMaxima: number;
}

export const GENEROS_LABELS: Record<Genero, string> = {
  DRAMA: 'Drama',
  COMEDIA: 'Comédia',
  ACAO: 'Ação',
  ANIMACAO: 'Animação',
  TERROR: 'Terror',
  ROMANCE: 'Romance',
  FICCAO_CIENTIFICA: 'Ficção Científica',
  THRILLER: 'Thriller',
  DOCUMENTARIO: 'Documentário',
  MUSICAL: 'Musical',
  CRIME: 'Crime',
  SUSPENSE: 'Suspense',
  MISTERIO: 'Mistério',
  AVENTURA: 'Aventura',
  FANTASIA: 'Fantasia',
  GUERRA: 'Guerra',
  HISTORIA: 'História',
  ESPORTE: 'Esporte',
  SATIRA: 'Sátira',
  FICCAO: 'Ficção',
  MUSICA: 'Música',
};

export const STREAMING_LABELS: Record<Streaming, string> = {
  NETFLIX: 'Netflix',
  AMAZON_PRIME: 'Amazon Prime',
  HBO_MAX: 'HBO Max',
  DISNEY_PLUS: 'Disney+',
  APPLE_TV: 'Apple TV+',
  MUBI: 'MUBI',
  GLOBOPLAY: 'Globoplay',
  MERCADO_PLAY: 'Mercado Play',
  GOOGLE_PLAY: 'Google Play',
  CLARO_TV_PLUS: 'Claro TV+',
  YOUTUBE: 'Youtube',
  TELECINE: 'Telecine',
};

export const FAIXA_ETARIA_LABELS: Record<FaixaEtaria, string> = {
  LIVRE: 'L',
  MAIORES_6: '6+',
  MAIORES_10: '10+',
  MAIORES_12: '12+',
  MAIORES_14: '14+',
  MAIORES_16: '16+',
  MAIORES_18: '18+',
};

export const FAIXA_ETARIA_IDADE: Record<FaixaEtaria, number> = {
  LIVRE: 0,
  MAIORES_6: 6,
  MAIORES_10: 10,
  MAIORES_12: 12,
  MAIORES_14: 14,
  MAIORES_16: 16,
  MAIORES_18: 18,
};
