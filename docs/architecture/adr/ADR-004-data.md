# ADR-004 — Persistência e Dados

- **Status:** Aprovada

## Decisão
- Banco relacional: PostgreSQL.
- Separação lógica: schema por módulo.
- ORM: Spring Data JPA + Hibernate.
- Migrações: Flyway.
- JSONB somente quando justificado pelo modelo.
- Arquivos binários em Object Storage; PostgreSQL guarda metadados e referências.
- Constraints, transações e garantias ACID devem proteger invariantes.

## Migrations
- Migrações centralizadas no projeto e organizadas por módulo.
- Estratégia Expand/Contract para mudanças compatíveis.
- Migrations de produção explícitas e versionadas.
- Não utilizar geração automática do schema pelo Hibernate em produção.

## Fronteiras de dados
- Um módulo é dono de seu schema e de seus repositórios.
- Não criar foreign keys entre schemas como mecanismo de integração.
- Integração entre módulos ocorre por contratos e casos de uso, não por acesso direto ao banco alheio.
