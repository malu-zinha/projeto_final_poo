import { Navigate, Outlet, NavLink } from 'react-router-dom';
import { useApp } from '@/contexts/AppContext';
import { Sparkles, Settings, Library, LogOut, Film } from 'lucide-react';

export default function AppLayout() {
  const { usuarioLogado, logout } = useApp();

  if (!usuarioLogado) return <Navigate to="/cadastro" replace />;

  const navItems = [
    { to: '/app/recomendados', label: 'Recomendados', icon: Sparkles },
    { to: '/app/preferencias', label: 'Preferências', icon: Settings },
    { to: '/app/catalogo', label: 'Catálogo', icon: Library },
  ];

  return (
    <div className="min-h-screen flex flex-col">
      {/* Top navbar */}
      <header className="sticky top-0 z-50 border-b border-border bg-background/80 backdrop-blur-md">
        <div className="container flex h-14 items-center justify-between">
          <div className="flex items-center gap-2">
            <Film className="h-5 w-5 text-primary" />
            <span className="font-heading font-bold text-lg">Oscar Films</span>
          </div>

          <nav className="hidden sm:flex items-center gap-1">
            {navItems.map(item => (
              <NavLink
                key={item.to}
                to={item.to}
                className={({ isActive }) =>
                  `flex items-center gap-1.5 px-3 py-1.5 rounded-md text-sm transition-colors ${
                    isActive ? 'bg-primary/15 text-primary font-medium' : 'text-muted-foreground hover:text-foreground'
                  }`
                }
              >
                <item.icon className="h-4 w-4" />
                {item.label}
              </NavLink>
            ))}
          </nav>

          <button onClick={logout} className="flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors">
            <LogOut className="h-4 w-4" />
            <span className="hidden sm:inline">Sair</span>
          </button>
        </div>
      </header>

      {/* Mobile bottom nav */}
      <nav className="sm:hidden fixed bottom-0 left-0 right-0 z-50 border-t border-border bg-background/90 backdrop-blur-md flex justify-around py-2">
        {navItems.map(item => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) =>
              `flex flex-col items-center gap-0.5 text-xs transition-colors ${
                isActive ? 'text-primary' : 'text-muted-foreground'
              }`
            }
          >
            <item.icon className="h-5 w-5" />
            {item.label}
          </NavLink>
        ))}
      </nav>

      {/* Content */}
      <main className="container flex-1 py-6 pb-20 sm:pb-6">
        <Outlet />
      </main>
    </div>
  );
}
