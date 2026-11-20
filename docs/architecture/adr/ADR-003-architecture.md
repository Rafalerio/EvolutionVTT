# ADR-003 — Arquitetura: Monólito Modular, Clean Architecture e DDD

- **Status:** Aprovada

## Decisão
Adotar monólito modular com princípios de Clean Architecture e Domain-Driven Design (DDD).

## Integração entre módulos
- Integração por interfaces/contratos síncronos e eventos de domínio.
- Eventos inicialmente internos ao processo.
- Outbox quando houver necessidade de publicação confiável de eventos duráveis.
- Cada módulo é proprietário de seus dados.
- Operações que atravessam módulos são orquestradas por casos de uso na camada de aplicação.
- Não criar um orquestrador genérico para todas as operações.

## Fronteiras
- Não importar pacotes internos de outro módulo.
- Não acessar tabelas, entidades ou repositórios de outro módulo.
- Contratos públicos não expõem entidades internas.
- Dependências devem apontar para contratos explícitos.
- Testes arquiteturais devem verificar as fronteiras na CI.

## Consequências
O monólito mantém simplicidade operacional inicial sem abrir mão de limites que permitam evolução futura. Falhas parciais devem ser tratadas com idempotência, compensações quando aplicáveis e Outbox para eventos duráveis.
