# Evolution VTT

Plataforma **web-first** de RPG de mesa virtual (*Virtual Tabletop*), concebida inicialmente para o sistema **Evolution RPG** e arquitetada para suportar sistemas de regras configuráveis, conteúdos oficiais e *homebrews* comunitários em experiência *crossplay* (Web/PWA, Desktop e Mobile).

Repositório oficial: https://github.com/Rafalerio/EvolutionVTT

---

## Estado atual do projeto

O repositório encontra-se na **Fase 0 — Fundação do Repositório** (conforme o Blueprint Arquitetural):

- Estrutura de diretórios, governança de desenvolvimento e diretrizes para agentes de IA estabelecidas.
- Protótipo estático inicial de interface e *design assets* preservados integralmente em [`frontend/`](frontend/).
- Blueprint Arquitetural v1.0.0 (`EVTT-BP-001`) incorporado em [`docs/architecture/blueprint/`](docs/architecture/blueprint/).
- Implementação do backend, migração do frontend para React/Vite/TypeScript, banco de dados e pipelines CI/CD ainda **não iniciados** e previstos para as tarefas seguintes do roadmap.

---

## Arquitetura aprovada (resumo)

As decisões arquiteturais oficiais estão consolidadas no [Blueprint Arquitetural v1.0.0](docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md) e registradas nas **ADR-001 a ADR-014** e nos modelos **C4-001 a C4-005**.

| Área | Decisão aprovada | Referência |
|---|---|---|
| Backend | Java + Spring Boot, **monólito modular** | ADR-001 |
| Estilo interno | Clean Architecture + DDD, integração híbrida (contratos síncronos e eventos) | ADR-003 |
| Persistência | PostgreSQL com *schema* por módulo, Spring Data JPA, Hibernate e Flyway | ADR-004 |
| Regras e conteúdo | Motor de regras declarativo progressivo (DSL/AST); versionamento imutável com migração manual aprovada | ADR-005, ADR-006 |
| Tempo real | WebSocket + STOMP com servidor autoritativo | ADR-007 |
| Frontend | React + Vite + TypeScript, TanStack Query, Zustand, React Hook Form, Zod, React Router e OpenAPI | ADR-002 |
| Renderização 2D | PixiJS no cliente, isolado por modelo de cena independente | ADR-008 |
| Segurança | Spring Security, OAuth 2.1 / OIDC (emissora central), Argon2id e autorização contextual | ADR-009 |
| Arquivos | Abstração por adaptadores: Cloudflare R2 (imagens), OCI Object Storage (demais arquivos) e Local (dev/testes) | ADR-010 |
| Desktop e Mobile | Desktop futuro com Tauri; Mobile inicialmente com PWA | ADR-011, ADR-012 |
| Infraestrutura e CI/CD | OCI, Docker / Docker Compose (inicial), Terraform e GitHub Actions | ADR-013 |
| Observabilidade | SLF4J/Logback estruturado, Micrometer, Prometheus, Grafana, OpenTelemetry, Jaeger e Alertmanager | ADR-014 |

---

## Estrutura do repositório

```text
EvolutionVTT/
├── .github/
│   ├── PULL_REQUEST_TEMPLATE.md     # Template padrão de Pull Requests
│   └── workflows/                   # Pipelines CI/CD (a implementar via GitHub Actions)
├── backend/                         # Monólito modular Java + Spring Boot (reservado)
├── frontend/                        # Cliente web (atualmente preserva o protótipo estático inicial)
├── infrastructure/
│   ├── docker/                      # Dockerfiles, Docker Compose e proxy Nginx (reservado)
│   └── terraform/                   # Infraestrutura como código para OCI (reservado)
├── docs/
│   ├── architecture/
│   │   ├── blueprint/               # Blueprint Arquitetural consolidado (EVTT-BP-001)
│   │   ├── adr/                     # Architecture Decision Records (ADR-001 a ADR-014+)
│   │   └── c4/                      # Modelos e diagramas C4 (C4-001 a C4-005)
│   ├── development/                 # Governança de desenvolvimento e guia de contribuição
│   ├── guides/                      # Guias técnicos e operacionais de desenvolvimento
│   └── runbooks/                    # Procedimentos operacionais (deploy, rollback, incidentes)
├── scripts/                         # Scripts utilitários de automação local
├── .editorconfig                    # Padrões básicos de formatação entre editores
├── .gitattributes                   # Normalização de fim de linha (LF) e binários
├── .gitignore                       # Exclusões de versionamento e proteção de segredos
├── AGENTS.md                        # Regras obrigatórias para agentes de IA
└── README.md                        # Este documento
```

---

## Documentação principal

- **Índice geral da documentação:** [`docs/README.md`](docs/README.md)
- **Blueprint Arquitetural (v1.0.0):** [`docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md`](docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md)
- **Índice de Arquitetura (ADRs e C4):** [`docs/architecture/README.md`](docs/architecture/README.md)
- **Governança de Desenvolvimento:** [`docs/development/governance.md`](docs/development/governance.md)
- **Guia de Contribuição:** [`docs/development/contributing.md`](docs/development/contributing.md)
- **Diretrizes para Agentes de IA:** [`AGENTS.md`](AGENTS.md)

---

## Protótipo frontend atual

O diretório [`frontend/`](frontend/) contém o protótipo estático inicial do dashboard (`index.html`, `styles.css`, `app.js` e `assets/`). Ele foi preservado integralmente como referência de identidade visual, layout e *design assets* para a futura implementação em React + Vite + TypeScript.

Para visualizar o protótipo localmente, abra `frontend/index.html` diretamente no navegador ou sirva a pasta `frontend/` com um servidor estático local. Veja mais detalhes em [`frontend/README.md`](frontend/README.md).

---

## Roadmap macro

A evolução planejada do projeto segue as fases descritas na Seção 18 do [Blueprint Arquitetural](docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md):

0. **Fase 0 — Fundação do repositório** *(em andamento)*
1. **Fase 1 — Identidade e fundação de backend**
2. **Fase 2 — Arquivos e conteúdo**
3. **Fase 3 — Regras e personagens**
4. **Fase 4 — Mesas**
5. **Fase 5 — VTT 2D e tempo real**
6. **Fase 6 — Comunidade e refinamento**
7. **Fase 7 — Expansão**
