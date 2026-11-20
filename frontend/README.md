# Frontend — Evolution VTT

Este diretório abriga o cliente web do **Evolution VTT**.

## Estado atual — Protótipo inicial preservado

Atualmente, `frontend/` contém o **protótipo estático inicial** do dashboard da plataforma, preservado integralmente conforme decisão da Fase 0:

- [`index.html`](index.html) — estrutura semântica da tela inicial (*Dashboard — Evolution VTT*), navegação principal, menu *Play Evolution*, barra lateral, recomendações, salas recentes, favoritos e acessibilidade básica (`aria-*`).
- [`styles.css`](styles.css) — folha de estilos com tema escuro, variáveis CSS, tipografia (*Faustina* e *Manrope*), paleta de referência (incluindo `#6e0e0a`), responsividade e suporte a `prefers-reduced-motion`.
- [`app.js`](app.js) — interações leves de pré-visualização (alternância de menu, favoritos, estado ativo da sidebar e avisos *toast*).
- [`assets/`](assets/) — 41 *assets* visuais aproveitáveis (marca `brand-eye.png` e `brand-wordmark.png`, 3 ilustrações de sala `room-*.png` e 36 ícones/divisores SVG).

> **Importante:** Não sobrescreva, apague nem migre este protótipo fora de uma tarefa explicitamente aprovada para a inicialização e migração do frontend.

### Como visualizar o protótipo localmente

Por ser um protótipo estático sem dependências de build, basta abrir `frontend/index.html` no navegador ou servir o diretório com qualquer servidor HTTP estático local.

## Arquitetura alvo aprovada

A implementação definitiva do cliente seguirá as decisões registradas no [Blueprint Arquitetural](../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md) e nas ADRs correspondentes:

- **Base web:** React + Vite + TypeScript (ADR-002).
- **Estado e formulários:** TanStack Query (estado remoto), Zustand (estado global/local compartilhado), React Hook Form + Zod, React Router e geração/tipagem via contratos OpenAPI (ADR-002).
- **Interface e Design System:** Design System próprio reaproveitando a identidade visual e tokens de referência definidos para o projeto.
- **Engine gráfica 2D:** PixiJS encapsulada por camada de abstração e modelo de cena independente da engine (ADR-008).
- **Tempo real:** cliente WebSocket + STOMP sincronizado com o estado autoritativo do backend (ADR-007).
- **Multiplataforma:** Mobile inicialmente via PWA (ADR-012) e Desktop futuro reutilizando a interface em Tauri (ADR-011).
