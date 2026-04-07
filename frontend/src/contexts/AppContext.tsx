import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import type { Usuario, Filme, Genero, Streaming } from '@/data/mock';
import {
  apiLogin,
  apiCadastro,
  apiGetFilmes,
  apiGetRecomendados,
  apiSalvarPreferencias,
} from '@/services/api';

interface AppContextType {
  usuarioLogado: Usuario | null;
  token: string | null;
  catalogo: Filme[];
  recomendacoes: Filme[];
  cadastrar: (nome: string, email: string, senha: string, idade: number) => Promise<boolean>;
  login: (email: string, senha: string) => Promise<boolean>;
  logout: () => void;
  salvarPreferencias: (generos: Genero[], streamings: Streaming[], duracaoMin: number, duracaoMax: number) => Promise<void>;
}

const AppContext = createContext<AppContextType | null>(null);

function loadSession(): { user: Usuario; token: string; senha: string } | null {
  try {
    const raw = localStorage.getItem('oscar_session');
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

function saveSession(user: Usuario, token: string, senha: string) {
  localStorage.setItem('oscar_session', JSON.stringify({ user, token, senha }));
}

function clearSession() {
  localStorage.removeItem('oscar_session');
}

export function AppProvider({ children }: { children: React.ReactNode }) {
  const saved = loadSession();
  const [usuarioLogado, setUsuarioLogado] = useState<Usuario | null>(saved?.user ?? null);
  const [token, setToken] = useState<string | null>(saved?.token ?? null);
  const [senha, setSenha] = useState<string>(saved?.senha ?? '');
  const [catalogo, setCatalogo] = useState<Filme[]>([]);
  const [recomendacoes, setRecomendacoes] = useState<Filme[]>([]);

  useEffect(() => {
    apiGetFilmes().then(setCatalogo).catch(() => {});
  }, []);

  const fetchRecomendados = useCallback(async (tk: string) => {
    try {
      const recs = await apiGetRecomendados(tk);
      setRecomendacoes(recs);
    } catch {
      setRecomendacoes([]);
    }
  }, []);

  useEffect(() => {
    if (token && usuarioLogado && (usuarioLogado.generosPreferidos.length > 0 || usuarioLogado.streamingsAssinados.length > 0)) {
      fetchRecomendados(token);
    }
  }, [token, usuarioLogado, fetchRecomendados]);

  const login = async (email: string, senhaInput: string): Promise<boolean> => {
    try {
      const data = await apiLogin(email, senhaInput);
      const user: Usuario = {
        id: data.token,
        nome: data.nome,
        email,
        senha: senhaInput,
        idade: data.idade,
        generosPreferidos: data.generos || [],
        streamingsAssinados: data.streamings || [],
        duracaoMinima: data.duracao_minima || 60,
        duracaoMaxima: data.duracao_maxima || 240,
      };
      setUsuarioLogado(user);
      setToken(data.token);
      setSenha(senhaInput);
      saveSession(user, data.token, senhaInput);

      if (user.generosPreferidos.length > 0 || user.streamingsAssinados.length > 0) {
        fetchRecomendados(data.token);
      } else {
        setRecomendacoes([]);
      }
      return true;
    } catch {
      return false;
    }
  };

  const cadastrar = async (nome: string, email: string, senhaInput: string, idade: number): Promise<boolean> => {
    try {
      await apiCadastro(nome, email, senhaInput, idade);
      return await login(email, senhaInput);
    } catch {
      return false;
    }
  };

  const logout = () => {
    setUsuarioLogado(null);
    setToken(null);
    setSenha('');
    setRecomendacoes([]);
    clearSession();
  };

  const salvarPreferencias = async (generos: Genero[], streamings: Streaming[], duracaoMin: number, duracaoMax: number) => {
    if (!token) return;
    await apiSalvarPreferencias(token, generos, streamings, duracaoMin, duracaoMax);

    const updated: Usuario = {
      ...usuarioLogado!,
      generosPreferidos: generos,
      streamingsAssinados: streamings,
      duracaoMinima: duracaoMin,
      duracaoMaxima: duracaoMax,
    };
    setUsuarioLogado(updated);
    saveSession(updated, token, senha);
    await fetchRecomendados(token);
  };

  return (
    <AppContext.Provider value={{ usuarioLogado, token, catalogo, recomendacoes, cadastrar, login, logout, salvarPreferencias }}>
      {children}
    </AppContext.Provider>
  );
}

export function useApp() {
  const ctx = useContext(AppContext);
  if (!ctx) throw new Error('useApp must be used within AppProvider');
  return ctx;
}
