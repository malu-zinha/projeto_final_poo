import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './auth.jsx'
import Login from './pages/Login.jsx'
import Cadastro from './pages/Cadastro.jsx'
import Recomendados from './pages/Recomendados.jsx'
import Catalogo from './pages/Catalogo.jsx'
import Preferencias from './pages/Preferencias.jsx'
import Navbar from './components/Navbar.jsx'

function ProtectedRoute({ children }) {
  const { user } = useAuth()
  if (!user) return <Navigate to="/login" replace />
  return (
    <>
      <Navbar />
      <main className="page-container">{children}</main>
    </>
  )
}

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/cadastro" element={<Cadastro />} />
      <Route path="/recomendados" element={<ProtectedRoute><Recomendados /></ProtectedRoute>} />
      <Route path="/catalogo" element={<ProtectedRoute><Catalogo /></ProtectedRoute>} />
      <Route path="/preferencias" element={<ProtectedRoute><Preferencias /></ProtectedRoute>} />
      <Route path="*" element={<Navigate to="/recomendados" replace />} />
    </Routes>
  )
}
