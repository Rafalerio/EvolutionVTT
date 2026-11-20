# Architecture Decision Records (ADRs)

Este diretório armazena as **ADRs** (*Architecture Decision Records*) do **Evolution VTT**.

## Índice das ADRs

| Número | Tema | Status | Arquivo |
|---|---|---|---|
| ADR-001 | Java + Spring Boot; monólito modular | Aprovada | [`ADR-001-backend.md`](ADR-001-backend.md) |
| ADR-002 | React + Vite + TypeScript e bibliotecas de frontend | Aprovada | [`ADR-002-frontend.md`](ADR-002-frontend.md) |
| ADR-003 | Clean Architecture, DDD e integração híbrida | Aprovada | [`ADR-003-architecture.md`](ADR-003-architecture.md) |
| ADR-004 | PostgreSQL, *schemas* por módulo, JPA/Hibernate e Flyway | Aprovada | [`ADR-004-data.md`](ADR-004-data.md) |
| ADR-005 | Rules Engine declarativo híbrido e progressivo | Aprovada | [`ADR-005-rules-engine.md`](ADR-005-rules-engine.md) |
| ADR-006 | Versionamento imutável e migração manual aprovada | Aprovada | [`ADR-006-content-versioning.md`](ADR-006-content-versioning.md) |
| ADR-007 | WebSocket + STOMP, servidor autoritativo | Aprovada | [`ADR-007-realtime.md`](ADR-007-realtime.md) |
| ADR-008 | PixiJS e modelo de cena independente | Aprovada | [`ADR-008-graphics.md`](ADR-008-graphics.md) |
| ADR-009 | Spring Security, OAuth 2.1/OIDC, Argon2id e autorização contextual | Aprovada | [`ADR-009-authentication.md`](ADR-009-authentication.md) |
| ADR-010 | File Storage com R2, OCI Object Storage e Local | Aprovada | [`ADR-010-file-storage.md`](ADR-010-file-storage.md) |
| ADR-011 | Desktop com Tauri | Aprovada | [`ADR-011-desktop.md`](ADR-011-desktop.md) |
| ADR-012 | PWA primeiro para mobile | Aprovada | [`ADR-012-mobile.md`](ADR-012-mobile.md) |
| ADR-013 | GitHub Actions, Docker, OCI, Terraform e estratégia de deploy | Aprovada | [`ADR-013-devops.md`](ADR-013-devops.md) |
| ADR-014 | Logs, métricas, tracing e observabilidade | Aprovada | [`ADR-014-observability.md`](ADR-014-observability.md) |
| ADR-015 | Ferramenta de build (Maven), Java 25, Spring Boot 4.1.x e ArchUnit | Aprovada | [`ADR-015-backend-build-and-runtime.md`](ADR-015-backend-build-and-runtime.md) |

## Regras para novas ADRs

1. **Novas decisões (a partir de `ADR-016`):** utilizar o modelo em [`TEMPLATE.md`](TEMPLATE.md) e seguir numeração sequencial com três dígitos (`ADR-NNN-titulo-curto.md`).
2. **Imutabilidade histórica:** uma ADR aprovada não deve ser reescrita para mudar uma decisão; caso uma decisão seja substituída, crie uma nova ADR referenciando a anterior e atualize o status da antiga para `Superseded by ADR-NNN`.
3. **Atualização do índice:** ao aprovar novas ADRs, atualize esta tabela e [`../README.md`](../README.md).
