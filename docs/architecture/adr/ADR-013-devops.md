# ADR-013 — DevOps e Entrega

- **Status:** Aprovada

## Decisão
- GitHub Actions para CI/CD.
- Docker para empacotamento.
- OCI Container Registry (OCIR) para imagens.
- OCI Compute para execução inicial.
- Docker Compose para desenvolvimento e ambientes simples.
- Terraform para infraestrutura como código.
- Ambientes separados conforme necessidade.

## Pipeline e implantação
- Deploy automático em homologação.
- Produção exige aprovação.
- Segredos protegidos e fora do repositório.
- Migrations executadas de forma controlada.
- Rollback por imagens versionadas.

## Fora do escopo inicial
Kubernetes não será adotado inicialmente. A arquitetura pode ser reavaliada se escala e operação justificarem.
