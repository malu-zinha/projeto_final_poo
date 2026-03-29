import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth.jsx'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/login')
  }

  return (
    <nav className="navbar">
      <span className="navbar-logo">CineOscar</span>

      <div className="navbar-links">
        <NavLink to="/recomendados">Recomendados</NavLink>
        <NavLink to="/catalogo">Catálogo</NavLink>
        <NavLink to="/preferencias">Preferências</NavLink>
      </div>

      <div className="navbar-user">
        <span className="navbar-nome">{user?.nome}</span>
        <button className="btn-logout" onClick={handleLogout}>Sair</button>
      </div>
    </nav>
  )
}
