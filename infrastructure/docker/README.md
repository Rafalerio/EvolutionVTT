# Docker — Evolution VTT

Este diretório armazenará os arquivos de conteinerização e composição de serviços locais e de implantação inicial do Evolution VTT, conforme a **ADR-013** e a Seção 15 do [Blueprint Arquitetural](../../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md).

## Estado atual

Diretório reservado na **Fase 0 — Fundação do Repositório**. Nenhum container, banco de dados ou arquivo `docker-compose.yml` foi configurado nesta tarefa.

## Artefatos previstos

- Dockerfiles multi-stage para backend (Java/Spring Boot) e frontend (build estático servido por Nginx).
- Configurações de **Nginx** com suporte a arquivos estáticos, proxy para API REST e *upgrade* de conexões WebSocket/STOMP.
- Composições **Docker Compose** para desenvolvimento local (ex.: PostgreSQL e serviços auxiliares) e implantação inicial em OCI Compute.
- Arquivos `.env.example` documentando variáveis necessárias sem expor segredos reais.

## Regras

- Nunca inclua arquivos `.env` reais ou senhas fixas em Dockerfiles ou arquivos Compose versionados.
- Imagens publicadas no OCI Container Registry devem ser versionadas explicitamente (evitar depender apenas de `latest` em produção).
