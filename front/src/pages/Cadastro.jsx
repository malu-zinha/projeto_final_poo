import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import * as api from '../api.js'

export default function Cadastro() {
  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [idade, setIdade] = useState('')
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState(false)
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()
    setErro('')
    setLoading(true)
    try {
      await api.cadastro(nome, email, senha, parseInt(idade))
      setSucesso(true)
      setTimeout(() => navigate('/login'), 1500)
    } catch (err) {
      setErro(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <form className="auth-card" onSubmit={handleSubmit}>
        <h1 className="auth-logo">CineOscar</h1>
        <p className="auth-subtitle">Criar nova conta</p>

        {erro && <div className="alert alert-error">{erro}</div>}
        {sucesso && <div className="alert alert-success">Conta criada! Redirecionando...</div>}

        <label>
          Nome
          <input type="text" value={nome} onChange={e => setNome(e.target.value)} required />
        </label>

        <label>
          Email
          <input type="text" value={email} onChange={e => setEmail(e.target.value)} required />
        </label>

        <label>
          Senha
          <input type="password" value={senha} onChange={e => setSenha(e.target.value)} required minLength={6} />
        </label>

        <label>
          Idade
          <input type="number" value={idade} onChange={e => setIdade(e.target.value)} required min={1} max={120} />
        </label>

        <button type="submit" className="btn-primary" disabled={loading || sucesso}>
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>

        <p className="auth-link">
          Já tem conta? <Link to="/login">Faça login</Link>
        </p>
      </form>
    </div>
  )
}
