# Workflows CI/CD — GitHub Actions

Este diretório armazenará os pipelines de integração e entrega contínua (CI/CD) do Evolution VTT utilizando **GitHub Actions**, conforme definido na **ADR-013** e na Seção 16.1 do [Blueprint Arquitetural](../../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md).

## Estado atual

Nenhum workflow foi criado nesta etapa (Fase 0 — estrutura inicial). Os pipelines serão implementados nas tarefas dedicadas após a inicialização dos projetos de backend e frontend.

## Fluxo planejado (ADR-013)

1. Validação de formatação e lint.
2. Compilação (backend e frontend).
3. Testes unitários, de integração, de contrato e testes arquiteturais de dependências.
4. Build de imagens Docker versionadas.
5. Publicação no OCI Container Registry.
6. Deploy automático em homologação.
7. Deploy em produção mediante aprovação explícita.

## Diretrizes

- Segredos e credenciais de deploy devem ser configurados exclusivamente em *GitHub Actions Secrets / Environments*, nunca em arquivos versionados.
- Ações de terceiros devem preferencialmente usar versões fixadas (por *tag* imutável ou *commit SHA*).
