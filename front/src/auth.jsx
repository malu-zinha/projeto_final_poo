import { createContext, useContext, useState } from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem('cineoscar_user'))
    } catch {
      return null
    }
  })

  function loginUser(data) {
    setUser(data)
    localStorage.setItem('cineoscar_user', JSON.stringify(data))
  }

  function logout() {
    setUser(null)
    localStorage.removeItem('cineoscar_user')
  }

  return (
    <AuthContext.Provider value={{ user, loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
