import { useApp } from '@/contexts/AppContext';
import { FilmeCard } from '@/components/FilmeCard';
import { Link } from 'react-router-dom';
import { Sparkles, Settings } from 'lucide-react';

export default function Recomendados() {
  const { usuarioLogado, recomendacoes } = useApp();

  const hasPrefs = usuarioLogado && (usuarioLogado.generosPreferidos.length > 0 || usuarioLogado.streamingsAssinados.length > 0);

  return (
    <div className="space-y-6 animate-fade-in">
      <div>
        <h1 className="text-2xl md:text-3xl font-heading font-bold flex items-center gap-2">
          <Sparkles className="h-6 w-6 text-primary" />
          Recomendados para você
        </h1>
        {usuarioLogado && (
          <p className="text-muted-foreground mt-1">Olá, {usuarioLogado.nome.split(' ')[0]}!</p>
        )}
      </div>

      {!hasPrefs ? (
        <div className="bg-card border border-border rounded-xl p-8 text-center space-y-3">
          <Settings className="h-10 w-10 text-muted-foreground mx-auto" />
          <p className="text-muted-foreground">Você ainda não configurou suas preferências.</p>
          <Link to="/app/preferencias" className="text-primary hover:underline text-sm font-medium">
            Configurar preferências →
          </Link>
        </div>
      ) : (
        <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
          {recomendacoes.map(f => (
            <FilmeCard key={f.id} filme={f} />
          ))}
        </div>
      )}
    </div>
  );
}
