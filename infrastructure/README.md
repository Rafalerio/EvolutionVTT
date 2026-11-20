# Infraestrutura — Evolution VTT

Este diretório concentra os artefatos de empacotamento, ambiente local e infraestrutura como código (IaC) do **Evolution VTT**, conforme a **ADR-013**, a **ADR-014** e a Seção 15 do [Blueprint Arquitetural](../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md).

## Organização

- [`docker/`](docker/README.md) — Dockerfiles, arquivos Docker Compose (desenvolvimento e implantação inicial) e configurações do proxy reverso Nginx (estáticos, API REST e WebSocket/STOMP).
- [`terraform/`](terraform/README.md) — Provisionamento declarativo (IaC) para Oracle Cloud Infrastructure (OCI) e recursos associados.

## Decisões arquiteturais aplicáveis

- Plataforma alvo: **Oracle Cloud Infrastructure (OCI)** (Compute, Container Registry e Object Storage), além de **Cloudflare R2** para armazenamento de imagens (ADR-010, ADR-013).
- Orquestração inicial com **Docker Compose**, sem adoção inicial de Kubernetes (ADR-013).
- Alterações manuais em produção devem ser evitadas ou reconciliadas com o código de infraestrutura.
- Nenhum segredo, chave privada, arquivo `.tfstate` ou `.tfvars` com credenciais reais pode ser versionado neste diretório.
