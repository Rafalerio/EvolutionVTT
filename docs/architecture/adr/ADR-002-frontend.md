# ADR-002 — Frontend: React + Vite + TypeScript

- **Status:** Aprovada
- **Decisão:** React + Vite + TypeScript.

## Tecnologias aprovadas
- React para interface.
- Vite para desenvolvimento e build.
- TypeScript.
- TanStack Query para estado remoto e cache.
- Zustand para estado local compartilhado.
- React Hook Form + Zod para formulários e validação.
- React Router para roteamento.
- OpenAPI como fonte dos contratos REST consumidos.
- Design System próprio.

## Consequências
- A interface deve separar estado remoto de estado local.
- Contratos de API devem ser tipados e alinhados ao OpenAPI.
- Componentes reutilizáveis devem seguir o Design System.
- A aplicação deve ser responsiva e preparada para clientes web-first.
- A migração do protótipo existente deve preservar funcionalidades e identidade visual aproveitáveis.
