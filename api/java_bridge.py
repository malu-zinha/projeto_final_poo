import json
import subprocess
from pathlib import Path

WORKSPACE_ROOT = Path(__file__).resolve().parent.parent.parent
JAVA_CP = str(WORKSPACE_ROOT / "out")


def chamar_java(comando, args=None):
    cmd = ["java", "-cp", JAVA_CP, "api.ApiHandler", comando]
    if args is not None:
        cmd.append(json.dumps(args, ensure_ascii=False))

    result = subprocess.run(
        cmd,
        capture_output=True,
        text=True,
        cwd=str(WORKSPACE_ROOT),
        timeout=30,
    )

    if result.returncode != 0:
        stderr = result.stderr.strip().split("\n")[-1] if result.stderr else "Erro interno"
        return {"error": stderr}

    try:
        return json.loads(result.stdout)
    except json.JSONDecodeError:
        return {"error": f"Resposta invalida do Java: {result.stdout[:200]}"}
