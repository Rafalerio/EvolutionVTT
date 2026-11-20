# Documentação Arquitetural — Evolution VTT

Este diretório reúne os documentos normativos que governam a arquitetura do **Evolution VTT**.

## Estrutura atual

- [`blueprint/`](blueprint/README.md) — Blueprint Arquitetural consolidado (`EVTT-BP-001`).
- [`adr/`](adr/README.md) — *Architecture Decision Records* (ADR-001 a ADR-014 aprovadas e futuras ADRs).
- [`c4/`](c4/README.md) — Modelos e diagramas C4 (C4-001 a C4-005 aprovados).

> Subdiretórios especializados previstos na Seção 23 do Blueprint (`api/`, `data-model/`, `security/`, `infrastructure/`) serão criados sob demanda quando os respectivos artefatos técnicos forem produzidos, evitando pastas vazias prematuras.

## Status dos artefatos oficiais

| Artefato | Status arquitetural | Presença no repositório | Local |
|---|---|---|---|
| Blueprint Arquitetural (`EVTT-BP-001` v1.0.0) | Baseline consolidada | **Incorporado** | [`blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md`](blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md) |
| ADR-001 a ADR-014 | Aprovadas | **Pendente de incorporação dos arquivos originais pelo responsável** | [`adr/`](adr/README.md) |
| C4-001 a C4-005 | Aprovados | **Pendente de incorporação dos arquivos/diagramas originais pelo responsável** | [`c4/`](c4/README.md) |

## Hierarquia de referência

Em caso de conflito entre artefatos, prevalece:

1. ADR aprovada mais recente para a decisão específica.
2. Modelo C4 aprovado e seus refinamentos aprovados.
3. Blueprint Arquitetural consolidado.
4. Documentação inicial e requisitos originais, quando não substituídos.
5. Implementação existente.

## Regra de evolução

Decisões arquiteturais aprovadas **não podem ser alteradas silenciosamente**. Toda decisão arquitetural nova ou revisão de decisão existente exige criação ou substituição explícita via ADR em [`adr/`](adr/README.md).
