# Runbooks Operacionais — Evolution VTT

Este diretório armazenará os procedimentos operacionais padronizados (*runbooks*) para implantação, manutenção, recuperação e resposta a incidentes do **Evolution VTT**, alinhados às **ADR-013** e **ADR-014** e às Seções 15 e 16 do [Blueprint Arquitetural](../architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md).

## Estado atual

Diretório preparado na **Fase 0 — Fundação do Repositório**. Os procedimentos concretos serão documentados à medida que a infraestrutura, o banco de dados, os pipelines CI/CD e a stack de observabilidade forem implementados.

## Runbooks previstos

- **Deploy e promoção de releases:** publicação de imagens versionadas no OCI Container Registry, deploy em homologação e aprovação para produção.
- **Rollback de aplicação:** reversão controlada para imagem versionada anterior e validação de compatibilidade com estado do banco.
- **Migrations em produção (Flyway):** checklist pré-deploy, execução controlada *Expand/Contract* e contingência para alterações não reversíveis automaticamente.
- **Backup e restauração (PostgreSQL e Object Storage):** rotina de backups, verificação periódica e recuperação de desastre (relacionado à pendência `P-014`).
- **Rotação de segredos e resposta a vazamento:** revogação imediata de credenciais, chaves de assinatura OIDC e tokens de provedores (R2/OCI).
- **Observabilidade e incidentes:** triagem via logs estruturados, métricas Prometheus/Grafana, traces Jaeger e alertas do Alertmanager.

## Estrutura sugerida para novos runbooks

Cada runbook criado neste diretório deve conter, de forma concisa:
1. Objetivo e quando usar.
2. Pré-requisitos e permissões necessárias.
3. Passo a passo verificável (com comandos reproduzíveis e sem credenciais reais).
4. Critérios de validação pós-procedimento.
5. Procedimento de reversão (*rollback*) ou escalonamento.
