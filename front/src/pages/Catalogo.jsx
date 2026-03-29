import { useEffect, useState } from 'react'
import * as api from '../api.js'
import FilmeCard from '../components/FilmeCard.jsx'

export default function Catalogo() {
  const [filmes, setFilmes] = useState([])
  const [loading, setLoading] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.getFilmes()
      .then(setFilmes)
      .catch(err => setErro(err.message))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div className="loading">Carregando catálogo...</div>
  if (erro) return <div className="alert alert-error">{erro}</div>

  return (
    <>
      <h2 className="page-title">Catálogo Completo <span className="count">({filmes.length} filmes)</span></h2>
      <div className="filme-grid">
        {filmes.map(f => <FilmeCard key={f.id} filme={f} />)}
      </div>
    </>
  )
}
