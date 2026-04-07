import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { Toaster } from "@/components/ui/toaster";
import { TooltipProvider } from "@/components/ui/tooltip";
import { AppProvider } from "@/contexts/AppContext";
import Cadastro from "./pages/Cadastro";
import LoginPage from "./pages/Login";
import AppLayout from "./pages/AppLayout";
import Recomendados from "./pages/Recomendados";
import Preferencias from "./pages/Preferencias";
import Catalogo from "./pages/Catalogo";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <AppProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Navigate to="/cadastro" replace />} />
            <Route path="/cadastro" element={<Cadastro />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/app" element={<AppLayout />}>
              <Route index element={<Navigate to="recomendados" replace />} />
              <Route path="recomendados" element={<Recomendados />} />
              <Route path="preferencias" element={<Preferencias />} />
              <Route path="catalogo" element={<Catalogo />} />
            </Route>
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </AppProvider>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
