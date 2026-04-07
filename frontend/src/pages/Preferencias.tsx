import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useApp } from '@/contexts/AppContext';
import { Genero, Streaming, GENEROS_LABELS, STREAMING_LABELS } from '@/data/mock';
import { Button } from '@/components/ui/button';
import { Slider } from '@/components/ui/slider';
import { Settings } from 'lucide-react';

const ALL_GENEROS: Genero[] = [
  'DRAMA', 'COMEDIA', 'ACAO', 'ANIMACAO', 'TERROR', 'ROMANCE',
  'FICCAO_CIENTIFICA', 'THRILLER', 'DOCUMENTARIO', 'MUSICAL', 'CRIME',
  'SUSPENSE', 'MISTERIO', 'AVENTURA', 'FANTASIA', 'GUERRA', 'HISTORIA',
  'ESPORTE', 'SATIRA', 'FICCAO', 'MUSICA',
];

const ALL_STREAMINGS: Streaming[] = [
  'NETFLIX', 'AMAZON_PRIME', 'HBO_MAX', 'DISNEY_PLUS', 'APPLE_TV',
  'MUBI', 'GLOBOPLAY', 'MERCADO_PLAY', 'GOOGLE_PLAY', 'CLARO_TV_PLUS',
  'YOUTUBE', 'TELECINE',
];

function formatMin(min: number): string {
  const h = Math.floor(min / 60);
  const m = min % 60;
  return m > 0 ? `${h}h ${m}min` : `${h}h`;
}

export default function Preferencias() {
  const { usuarioLogado, salvarPreferencias } = useApp();
  const navigate = useNavigate();

  const [generos, setGeneros] = useState<Genero[]>(usuarioLogado?.generosPreferidos ?? []);
  const [streamings, setStreamings] = useState<Streaming[]>(usuarioLogado?.streamingsAssinados ?? []);
  const [duracao, setDuracao] = useState<[number, number]>([
    usuarioLogado?.duracaoMinima ?? 60,
    usuarioLogado?.duracaoMaxima ?? 240,
  ]);

  const toggleGenero = (g: Genero) =>
    setGeneros(prev => prev.includes(g) ? prev.filter(x => x !== g) : [...prev, g]);

  const toggleStreaming = (s: Streaming) =>
    setStreamings(prev => prev.includes(s) ? prev.filter(x => x !== s) : [...prev, s]);

  const handleSave = async () => {
    try {
      await salvarPreferencias(generos, streamings, duracao[0], duracao[1]);
    } finally {
      navigate('/app/recomendados');
    }
  };

  return (
    <div className="max-w-2xl space-y-8 animate-fade-in">
      <div>
        <h1 className="text-2xl md:text-3xl font-heading font-bold flex items-center gap-2">
          <Settings className="h-6 w-6 text-primary" />
          Preferências
        </h1>
        <p className="text-muted-foreground mt-1">Personalize suas recomendações</p>
      </div>

      <section className="space-y-3">
        <h2 className="font-heading font-semibold text-lg">Gêneros favoritos</h2>
        <div className="flex flex-wrap gap-2">
          {ALL_GENEROS.map(g => (
            <button
              key={g}
              onClick={() => toggleGenero(g)}
              className={`chip ${generos.includes(g) ? 'chip-active' : 'chip-inactive'}`}
            >
              {GENEROS_LABELS[g]}
            </button>
          ))}
        </div>
      </section>

      <section className="space-y-3">
        <h2 className="font-heading font-semibold text-lg">Streamings que você assina</h2>
        <div className="flex flex-wrap gap-2">
          {ALL_STREAMINGS.map(s => (
            <button
              key={s}
              onClick={() => toggleStreaming(s)}
              className={`chip ${streamings.includes(s) ? 'chip-active' : 'chip-inactive'}`}
            >
              {STREAMING_LABELS[s]}
            </button>
          ))}
        </div>
      </section>

      <section className="space-y-3">
        <h2 className="font-heading font-semibold text-lg">Duração dos filmes</h2>
        <p className="text-sm text-muted-foreground">
          {formatMin(duracao[0])} – {formatMin(duracao[1])}
        </p>
        <Slider
          min={60}
          max={240}
          step={10}
          value={duracao}
          onValueChange={(v) => setDuracao(v as [number, number])}
          className="w-full"
        />
      </section>

      <Button onClick={handleSave} className="w-full sm:w-auto">
        Salvar preferências
      </Button>
    </div>
  );
}
