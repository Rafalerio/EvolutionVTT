# C4-002 — Diagrama de Containers

- **Status:** Aprovado

## Containers lógicos

| Container | Responsabilidade | Tecnologia/decisão |
|---|---|---|
| Web/PWA | Interface web e experiência mobile inicial | React, Vite, TypeScript |
| Desktop | Aplicação desktop | Tauri com frontend compartilhado |
| Mobile | Cliente mobile inicial | PWA adaptada |
| Nginx | Servir estáticos e encaminhar API/WebSocket | Nginx |
| Backend | Regras de negócio e autoridade do estado | Java + Spring Boot, monólito modular |
| Banco | Persistência relacional | PostgreSQL, schema por módulo |
| Imagens | Armazenamento de imagens de produção | Cloudflare R2 |
| Arquivos | Armazenamento de demais arquivos | OCI Object Storage |
| Observabilidade | Logs, métricas, traces e alertas | Stack ADR-014 |

## Comunicação
- REST para operações request/response.
- WebSocket + STOMP para tempo real.
- Backend valida permissões e mantém estado oficial.
- PixiJS executa renderização 2D no cliente, isolado por abstração.

## Infraestrutura e entrega
- OCI Compute para execução inicial.
- Docker e Docker Compose.
- OCIR para imagens de containers.
- GitHub Actions para CI/CD.
- Terraform para infraestrutura como código.

## Observação
Containers neste C4 são unidades lógicas de responsabilidade e não implicam que cada unidade deva ser um container Docker independente.
