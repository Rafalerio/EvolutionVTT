# Architecture Decision Records (ADRs)

Este diretório armazena as **ADRs** (*Architecture Decision Records*) do **Evolution VTT**.

## Estado atual de incorporação

As decisões **ADR-001 a ADR-014** estão **aprovadas** e rastreadas na Seção 21.1 do [Blueprint Arquitetural v1.0.0](../blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md), porém **seus arquivos individuais originais ainda não foram incorporados ao repositório**.

> **Ação pendente do responsável pelo projeto:** incorporar os arquivos oficiais `ADR-001` a `ADR-014` neste diretório preservando integralmente numeração, título, status e conteúdo originais. O conteúdo desses documentos **não foi recriado nem inferido** para preservar fidelidade às decisões originais.

## Índice das ADRs aprovadas (referência do Blueprint)

| Número | Tema resumido (Blueprint §21.1) | Status da decisão | Arquivo no repositório |
|---|---|---|---|
| ADR-001 | Java + Spring Boot; monólito modular | Aprovada | *Aguardando incorporação* |
| ADR-002 | React + Vite + TypeScript e bibliotecas de frontend | Aprovada | *Aguardando incorporação* |
| ADR-003 | Clean Architecture, DDD e integração híbrida | Aprovada | *Aguardando incorporação* |
| ADR-004 | PostgreSQL, *schemas* por módulo, JPA/Hibernate e Flyway | Aprovada | *Aguardando incorporação* |
| ADR-005 | Rules Engine declarativo híbrido e progressivo | Aprovada | *Aguardando incorporação* |
| ADR-006 | Versionamento imutável e migração manual aprovada | Aprovada | *Aguardando incorporação* |
| ADR-007 | WebSocket + STOMP, servidor autoritativo | Aprovada | *Aguardando incorporação* |
| ADR-008 | PixiJS e modelo de cena independente | Aprovada | *Aguardando incorporação* |
| ADR-009 | Spring Security, OAuth 2.1/OIDC, Argon2id e autorização contextual | Aprovada | *Aguardando incorporação* |
| ADR-010 | File Storage com R2, OCI Object Storage e Local | Aprovada | *Aguardando incorporação* |
| ADR-011 | Desktop com Tauri | Aprovada | *Aguardando incorporação* |
| ADR-012 | PWA primeiro para mobile | Aprovada | *Aguardando incorporação* |
| ADR-013 | GitHub Actions, Docker, OCI, Terraform e estratégia de deploy | Aprovada | *Aguardando incorporação* |
| ADR-014 | Logs, métricas, tracing e observabilidade | Aprovada | *Aguardando incorporação* |

## Regras para incorporação e novas ADRs

1. **Preservação integral:** ao incorporar `ADR-001` a `ADR-014`, manter exatamente numeração, título, status e conteúdo originais (ex.: `ADR-001-backend-technology.md`, conforme recomendação da Seção 23 do Blueprint ou nomenclatura original adotada pelo responsável).
2. **Novas decisões (a partir de `ADR-015`):** utilizar o modelo em [`TEMPLATE.md`](TEMPLATE.md) e seguir numeração sequencial com três dígitos (`ADR-NNN-titulo-curto.md`).
3. **Imutabilidade histórica:** uma ADR aprovada não deve ser reescrita para mudar uma decisão; caso uma decisão seja substituída, crie uma nova ADR referenciando a anterior e atualize o status da antiga para `Superseded by ADR-NNN`.
4. **Atualização do índice:** ao incorporar ou aprovar novas ADRs, atualize a tabela acima com os links para os arquivos.
