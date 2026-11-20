# Backend — Evolution VTT

Este diretório abrigará a aplicação backend do **Evolution VTT**.

## Estado atual

Estrutura reservada na **Fase 0 — Fundação do Repositório**. A inicialização do projeto Java/Spring Boot, configuração do banco de dados e implementação dos módulos ocorrerão nas próximas tarefas do roadmap.

## Arquitetura aprovada

Conforme o [Blueprint Arquitetural](../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md) e as ADRs aprovadas:

- **Stack:** Java + Spring Boot (ADR-001).
- **Estilo:** Monólito modular com Clean Architecture e DDD (ADR-001, ADR-003).
- **Persistência:** PostgreSQL com *schema* por módulo, Spring Data JPA, Hibernate e Flyway centralizado com *migrations* organizadas por módulo (ADR-004).
- **Comunicação:** API REST documentada com OpenAPI e WebSocket + STOMP autoritativo (ADR-002, ADR-007).
- **Segurança:** Spring Security, OAuth 2.1 / OIDC, Argon2id e autorização contextual por módulo proprietário (ADR-009).
- **Arquivos:** Abstração com adaptadores para Cloudflare R2 (imagens), OCI Object Storage (demais arquivos) e armazenamento local (dev/testes) (ADR-010).
- **Observabilidade:** SLF4J + Logback estruturado, Micrometer, Prometheus e OpenTelemetry (ADR-014).

## Módulos arquiteturais previstos

1. `identity-access`
2. `user-profiles`
3. `content-management`
4. `rules-engine`
5. `character-management`
6. `campaigns-tables`
7. `vtt-scene-management`
8. `real-time-communication`
9. `community-messaging`
10. `file-storage`
11. `commerce` *(fase futura)*

## Regras fundamentais de implementação

- Cada módulo é proprietário exclusivo de seus dados e *schema*.
- Integração entre módulos ocorre apenas por contratos públicos (*ports*/DTOs) e eventos.
- É proibido importar pacotes internos ou consultar tabelas/repositórios de outro módulo.
- Dependências circulares são proibidas e devem ser validadas por testes arquiteturais na CI.
- Regras de domínio devem permanecer desacopladas de frameworks, transporte e provedores externos.
