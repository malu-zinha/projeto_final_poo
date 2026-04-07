import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useApp } from '@/contexts/AppContext';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Film } from 'lucide-react';
import { useToast } from '@/hooks/use-toast';

export default function Cadastro() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [idade, setIdade] = useState('');
  const { cadastrar } = useApp();
  const navigate = useNavigate();
  const { toast } = useToast();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const ok = await cadastrar(nome, email, senha, parseInt(idade));
    if (ok) {
      navigate('/app/recomendados');
    } else {
      toast({ title: 'Erro', description: 'E-mail já cadastrado.', variant: 'destructive' });
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4">
      <div className="w-full max-w-md space-y-8 animate-fade-in">
        <div className="text-center space-y-2">
          <div className="flex justify-center">
            <div className="h-12 w-12 rounded-xl bg-primary/20 flex items-center justify-center">
              <Film className="h-6 w-6 text-primary" />
            </div>
          </div>
          <h1 className="text-3xl font-heading font-bold">Oscar Films</h1>
          <p className="text-muted-foreground">Crie sua conta e descubra filmes premiados</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4 bg-card border border-border rounded-xl p-6">
          <div className="space-y-2">
            <Label htmlFor="nome">Nome completo</Label>
            <Input id="nome" value={nome} onChange={e => setNome(e.target.value)} required placeholder="Seu nome" />
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">E-mail</Label>
            <Input id="email" type="email" value={email} onChange={e => setEmail(e.target.value)} required placeholder="email@exemplo.com" />
          </div>
          <div className="space-y-2">
            <Label htmlFor="senha">Senha</Label>
            <Input id="senha" type="password" value={senha} onChange={e => setSenha(e.target.value)} required placeholder="••••••" />
          </div>
          <div className="space-y-2">
            <Label htmlFor="idade">Idade</Label>
            <Input id="idade" type="number" min={1} max={120} value={idade} onChange={e => setIdade(e.target.value)} required placeholder="25" />
          </div>
          <Button type="submit" className="w-full">Criar conta</Button>
        </form>

        <p className="text-center text-sm text-muted-foreground">
          Já tem conta?{' '}
          <Link to="/login" className="text-primary hover:underline">Entrar</Link>
        </p>
      </div>
    </div>
  );
}
