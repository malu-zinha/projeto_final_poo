import { useEffect, useState } from 'react'
import { useAuth } from '../auth.jsx'
import * as api from '../api.js'
import FilmeCard from '../components/FilmeCard.jsx'

export default function Recomendados() {
  const { user } = useAuth()
  const [filmes, setFilmes] = useState([])
  const [loading, setLoading] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.getRecomendados(user.token)
      .then(setFilmes)
      .catch(err => setErro(err.message))
      .finally(() => setLoading(false))
  }, [user.token])

  if (loading) return <div className="loading">Carregando recomendações...</div>
  if (erro) return <div className="alert alert-error">{erro}</div>

  return (
    <>
      <h2 className="page-title">Recomendados para você</h2>
      {filmes.length === 0 ? (
        <p className="empty-state">
          Nenhuma recomendação ainda. Configure suas preferências para receber sugestões personalizadas.
        </p>
      ) : (
        <div className="filme-grid">
          {filmes.map(f => <FilmeCard key={f.id} filme={f} />)}
        </div>
      )}
    </>
  )
}
