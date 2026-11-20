# Documentação — Evolution VTT

Este diretório centraliza a documentação técnica, arquitetural, de governança e operacional do **Evolution VTT**.

## Estrutura

| Diretório | Finalidade |
|---|---|
| [`architecture/`](architecture/README.md) | Baseline arquitetural oficial: Blueprint (`blueprint/`), ADRs (`adr/`) e modelos C4 (`c4/`). |
| [`development/`](development/README.md) | Governança de desenvolvimento (branches, commits, PRs, revisão, versionamento, dependências, segurança) e guia de contribuição. |
| [`guides/`](guides/README.md) | Guias práticos de desenvolvimento, configuração de ambiente e uso de ferramentas. |
| [`runbooks/`](runbooks/README.md) | Procedimentos operacionais (deploy, rollback, migrations, backup, incidentes e rotação de segredos). |

## Hierarquia de referência

Em caso de divergência entre documentos ou entre documentação e código, aplica-se a hierarquia oficial definida no [Blueprint Arquitetural](architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md):

1. **ADR aprovada mais recente** para a decisão arquitetural específica (`docs/architecture/adr/`).
2. **Modelo C4 aprovado** e seus refinamentos (`docs/architecture/c4/`).
3. **Blueprint Arquitetural consolidado** (`docs/architecture/blueprint/`).
4. **Documentação inicial e requisitos originais**, quando não substituídos por decisões posteriores.
5. **Implementação existente**, que não deve ser considerada automaticamente correta apenas por já existir.

## Regras para agentes de IA

Agentes de IA que atuem neste repositório devem ler obrigatoriamente [`AGENTS.md`](../AGENTS.md) antes de propor ou realizar qualquer alteração.
