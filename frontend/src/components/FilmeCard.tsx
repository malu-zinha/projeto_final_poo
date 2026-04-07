import { Filme, GENEROS_LABELS, STREAMING_LABELS, FAIXA_ETARIA_LABELS } from '@/data/mock';
import { Clock, Award } from 'lucide-react';

function formatDuracao(min: number): string {
  const h = Math.floor(min / 60);
  const m = min % 60;
  return m > 0 ? `${h}h ${m}min` : `${h}h`;
}

export function FilmeCard({ filme }: { filme: Filme }) {
  return (
    <div className="group rounded-lg border border-border bg-card p-4 card-hover flex flex-col gap-3">
      <div className="aspect-[2/3] w-full rounded-md bg-secondary flex items-center justify-center">
        <span className="text-4xl font-heading font-bold text-muted-foreground/50">
          {filme.titulo.charAt(0)}
        </span>
      </div>

      <div>
        <h3 className="font-heading font-semibold text-foreground leading-tight line-clamp-2">{filme.titulo}</h3>
        <p className="text-xs text-muted-foreground flex items-center gap-1 mt-0.5">
          <Award className="h-3 w-3" />
          {filme.oscarAno}
        </p>
      </div>

      <div className="flex flex-wrap gap-1">
        {filme.generos.map(g => (
          <span key={g} className="chip chip-active text-xs">
            {GENEROS_LABELS[g]}
          </span>
        ))}
      </div>

      <div className="flex items-center gap-3 text-sm text-muted-foreground">
        <span className="flex items-center gap-1">
          <Clock className="h-3.5 w-3.5" />
          {formatDuracao(filme.duracaoMinutos)}
        </span>
        <span className="text-xs bg-secondary px-2 py-0.5 rounded font-semibold">
          {FAIXA_ETARIA_LABELS[filme.faixaEtaria]}
        </span>
      </div>

      <div className="flex flex-wrap gap-1.5">
        {filme.streamings.map(s => (
          <span key={s} className="text-xs bg-secondary px-2 py-0.5 rounded-full text-muted-foreground">
            {STREAMING_LABELS[s]}
          </span>
        ))}
      </div>
    </div>
  );
}
