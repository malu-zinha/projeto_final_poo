import { useState, useMemo } from 'react';
import { useApp } from '@/contexts/AppContext';
import { FilmeCard } from '@/components/FilmeCard';
import { Genero, GENEROS_LABELS } from '@/data/mock';
import { Input } from '@/components/ui/input';
import { Search, Library } from 'lucide-react';

const ALL_GENEROS: Genero[] = [
  'DRAMA', 'COMEDIA', 'ACAO', 'ANIMACAO', 'TERROR', 'ROMANCE',
  'FICCAO_CIENTIFICA', 'THRILLER', 'DOCUMENTARIO', 'MUSICAL', 'CRIME',
  'SUSPENSE', 'MISTERIO', 'AVENTURA', 'FANTASIA', 'GUERRA', 'HISTORIA',
  'ESPORTE', 'SATIRA', 'FICCAO', 'MUSICA',
];

export default function Catalogo() {
  const { catalogo } = useApp();
  const [busca, setBusca] = useState('');
  const [generoFiltro, setGeneroFiltro] = useState<Genero | null>(null);

  const filtrados = useMemo(() => {
    return catalogo.filter(f => {
      const matchBusca = f.titulo.toLowerCase().includes(busca.toLowerCase());
      const matchGenero = !generoFiltro || f.generos.includes(generoFiltro);
      return matchBusca && matchGenero;
    });
  }, [catalogo, busca, generoFiltro]);

  return (
    <div className="space-y-6 animate-fade-in">
      <div>
        <h1 className="text-2xl md:text-3xl font-heading font-bold flex items-center gap-2">
          <Library className="h-6 w-6 text-primary" />
          Catálogo
        </h1>
        <p className="text-muted-foreground mt-1">Todos os filmes indicados ao Oscar</p>
      </div>

      <div className="relative max-w-sm">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
        <Input
          placeholder="Buscar por título..."
          value={busca}
          onChange={e => setBusca(e.target.value)}
          className="pl-9"
        />
      </div>

      <div className="flex flex-wrap gap-2">
        <button
          onClick={() => setGeneroFiltro(null)}
          className={`chip ${!generoFiltro ? 'chip-active' : 'chip-inactive'}`}
        >
          Todos
        </button>
        {ALL_GENEROS.map(g => (
          <button
            key={g}
            onClick={() => setGeneroFiltro(generoFiltro === g ? null : g)}
            className={`chip ${generoFiltro === g ? 'chip-active' : 'chip-inactive'}`}
          >
            {GENEROS_LABELS[g]}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
        {filtrados.map(f => (
          <FilmeCard key={f.id} filme={f} />
        ))}
      </div>

      {filtrados.length === 0 && (
        <p className="text-center text-muted-foreground py-8">Nenhum filme encontrado.</p>
      )}
    </div>
  );
}
