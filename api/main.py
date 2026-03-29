import base64
from fastapi import FastAPI, Header, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from java_bridge import chamar_java

app = FastAPI(title="CineOscar API")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)


class CadastroBody(BaseModel):
    nome: str
    email: str
    senha: str
    idade: int


class LoginBody(BaseModel):
    email: str
    senha: str


class PreferenciasBody(BaseModel):
    generos: list[str]
    streamings: list[str]
    duracao_min: int
    duracao_max: int


def decode_auth(authorization: str):
    try:
        decoded = base64.b64decode(authorization).decode()
        email, senha = decoded.split(":", 1)
        return email, senha
    except Exception:
        raise HTTPException(401, "Token inválido")


def check_error(result):
    if isinstance(result, dict) and "error" in result:
        raise HTTPException(400, result["error"])
    return result


@app.post("/auth/cadastro")
async def cadastro(body: CadastroBody):
    result = chamar_java("cadastro", {
        "nome": body.nome,
        "email": body.email,
        "senha": body.senha,
        "idade": body.idade,
    })
    return check_error(result)


@app.post("/auth/login")
async def login(body: LoginBody):
    result = chamar_java("login", {"email": body.email, "senha": body.senha})
    check_error(result)
    token = base64.b64encode(f"{body.email}:{body.senha}".encode()).decode()
    result["token"] = token
    return result


@app.get("/filmes")
async def listar_filmes():
    return check_error(chamar_java("filmes"))


@app.get("/filmes/recomendados")
async def recomendados(authorization: str = Header()):
    email, senha = decode_auth(authorization)
    return check_error(chamar_java("recomendados", {"email": email, "senha": senha}))


@app.get("/preferencias")
async def get_preferencias(authorization: str = Header()):
    email, senha = decode_auth(authorization)
    return check_error(chamar_java("preferencias_get", {"email": email, "senha": senha}))


@app.put("/preferencias")
async def set_preferencias(body: PreferenciasBody, authorization: str = Header()):
    email, senha = decode_auth(authorization)
    return check_error(chamar_java("preferencias_set", {
        "email": email,
        "senha": senha,
        "generos": body.generos,
        "streamings": body.streamings,
        "duracao_min": body.duracao_min,
        "duracao_max": body.duracao_max,
    }))
