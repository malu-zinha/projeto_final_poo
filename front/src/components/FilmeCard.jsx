import { STREAMINGS, FAIXAS } from '../constants.js'

export default function FilmeCard({ filme }) {
  return (
    <div className="filme-card">
      <div className="filme-header">
        <h3 className="filme-titulo">{filme.titulo} <span className="filme-ano">({filme.ano})</span></h3>
        <span className={`badge faixa faixa-${filme.faixa_etaria}`}>
          {FAIXAS[filme.faixa_etaria] || filme.faixa_etaria}
        </span>
      </div>

      <div className="filme-badges">
        <span className={`badge genero genero-${filme.genero}`}>{filme.genero_descricao}</span>
        <span className="badge duracao">{filme.duracao_formatada}</span>
      </div>

      <div className="filme-stats">
        <span className="stat imdb">IMDB {filme.nota_imdb}</span>
        <span className="stat oscar">
          {filme.oscar_indicacoes} ind. / {filme.oscar_vitorias} vit.
        </span>
      </div>

      <p className="filme-sinopse">{filme.sinopse}</p>

      <div className="filme-streamings">
        {filme.streamings.map(s => (
          <span key={s} className="badge streaming">{STREAMINGS[s] || s}</span>
        ))}
      </div>
    </div>
  )
}
