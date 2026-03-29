const BASE = '/api'

async function request(url, options = {}) {
  const res = await fetch(BASE + url, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  })
  const data = await res.json()
  if (!res.ok) throw new Error(data.detail || 'Erro na requisição')
  return data
}

export function login(email, senha) {
  return request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, senha }),
  })
}

export function cadastro(nome, email, senha, idade) {
  return request('/auth/cadastro', {
    method: 'POST',
    body: JSON.stringify({ nome, email, senha, idade }),
  })
}

export function getFilmes() {
  return request('/filmes')
}

export function getRecomendados(token) {
  return request('/filmes/recomendados', {
    headers: { Authorization: token },
  })
}

export function getPreferencias(token) {
  return request('/preferencias', {
    headers: { Authorization: token },
  })
}

export function salvarPreferencias(token, prefs) {
  return request('/preferencias', {
    method: 'PUT',
    body: JSON.stringify(prefs),
    headers: { Authorization: token },
  })
}
