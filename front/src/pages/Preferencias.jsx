import { useEffect, useState } from 'react'
import { useAuth } from '../auth.jsx'
import * as api from '../api.js'
import { GENEROS, STREAMINGS, GENERO_KEYS, STREAMING_KEYS } from '../constants.js'

export default function Preferencias() {
  const { user } = useAuth()
  const [generos, setGeneros] = useState([])
  const [streamings, setStreamings] = useState([])
  const [duracaoMin, setDuracaoMin] = useState(60)
  const [duracaoMax, setDuracaoMax] = useState(240)
  const [loading, setLoading] = useState(true)
  const [salvando, setSalvando] = useState(false)
  const [mensagem, setMensagem] = useState('')

  useEffect(() => {
    api.getPreferencias(user.token)
      .then(data => {
        setGeneros(data.generos || [])
        setStreamings(data.streamings || [])
        setDuracaoMin(data.duracao_minima || 60)
        setDuracaoMax(data.duracao_maxima || 240)
      })
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [user.token])

  function toggle(list, setList, value) {
    setList(prev => prev.includes(value) ? prev.filter(x => x !== value) : [...prev, value])
  }

  async function salvar(e) {
    e.preventDefault()
    setSalvando(true)
    setMensagem('')
    try {
      await api.salvarPreferencias(user.token, {
        generos,
        streamings,
        duracao_min: duracaoMin,
        duracao_max: duracaoMax,
      })
      setMensagem('Preferências salvas!')
      setTimeout(() => setMensagem(''), 3000)
    } catch (err) {
      setMensagem('Erro: ' + err.message)
    } finally {
      setSalvando(false)
    }
  }

  if (loading) return <div className="loading">Carregando preferências...</div>

  return (
    <>
      <h2 className="page-title">Minhas Preferências</h2>

      {mensagem && (
        <div className={`alert ${mensagem.startsWith('Erro') ? 'alert-error' : 'alert-success'}`}>
          {mensagem}
        </div>
      )}

      <form className="prefs-form" onSubmit={salvar}>
        <section className="prefs-section">
          <h3>Gêneros Preferidos</h3>
          <div className="checkbox-grid">
            {GENERO_KEYS.map(key => (
              <label key={key} className={`checkbox-item ${generos.includes(key) ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={generos.includes(key)}
                  onChange={() => toggle(generos, setGeneros, key)}
                />
                {GENEROS[key]}
              </label>
            ))}
          </div>
        </section>

        <section className="prefs-section">
          <h3>Streamings Assinados</h3>
          <div className="checkbox-grid">
            {STREAMING_KEYS.map(key => (
              <label key={key} className={`checkbox-item ${streamings.includes(key) ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={streamings.includes(key)}
                  onChange={() => toggle(streamings, setStreamings, key)}
                />
                {STREAMINGS[key]}
              </label>
            ))}
          </div>
        </section>

        <section className="prefs-section">
          <h3>Duração (minutos)</h3>
          <div className="duracao-inputs">
            <label>
              Mínima
              <input type="number" value={duracaoMin} onChange={e => setDuracaoMin(+e.target.value)} min={0} max={400} />
            </label>
            <label>
              Máxima
              <input type="number" value={duracaoMax} onChange={e => setDuracaoMax(+e.target.value)} min={0} max={400} />
            </label>
          </div>
        </section>

        <button type="submit" className="btn-primary" disabled={salvando}>
          {salvando ? 'Salvando...' : 'Salvar Preferências'}
        </button>
      </form>
    </>
  )
}
