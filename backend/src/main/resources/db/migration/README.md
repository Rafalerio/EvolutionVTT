# Migrations Flyway — Evolution VTT

Este diretório centraliza as *migrations* SQL gerenciadas pelo **Flyway** para o **PostgreSQL**, conforme definido na **ADR-004**.

## Estado atual

Nenhuma tabela de negócio ou *migration* SQL foi criada na tarefa `EVTT-F0-002` (fundação técnica). As *migrations* serão adicionadas incrementalmente a partir da Fase 1 conforme cada módulo de domínio for especificado e implementado.

## Organização por módulo (ADR-004)

As *migrations* permanecem centralizadas neste projeto e organizadas em subdiretórios por módulo (correspondendo ao *schema* proprietário):

```text
db/migration/
├── identity_access/        # schema: identity_access (Fase 1)
├── user_profiles/          # schema a fixar na especificação do módulo (Fase 1)
├── file_storage/           # schema: file_storage (Fase 2)
├── content_management/     # schema a fixar na especificação do módulo (Fase 2)
├── rules_engine/           # schema: rules_engine (Fase 3)
├── character_management/   # schema: character_management (Fase 3)
├── campaigns_tables/       # schema: campaigns_tables (Fase 4)
├── vtt_scene_management/   # schema a fixar na especificação do módulo (Fase 5)
└── community_messaging/    # schema a fixar na especificação do módulo (Fase 6)
```

> Os subdiretórios acima só devem ser criados e registrados em `spring.flyway.locations` quando as primeiras *migrations* do respectivo módulo forem implementadas.

## Regras obrigatórias (ADR-004)

1. **Propriedade exclusiva de *schema*:** cada módulo gerencia apenas seu próprio *schema*; nunca altere tabelas de outro módulo.
2. **Sem *foreign keys* entre *schemas*:** referências a IDs de outros módulos são valores de contrato, nunca chaves estrangeiras inter-schema.
3. **Estratégia *Expand/Contract*:** mudanças de schema devem manter compatibilidade durante deploys progressivos.
4. **Imutabilidade:** *migrations* já aplicadas não devem ser editadas; correções geram nova versão.
5. **Hibernate `ddl-auto=validate`:** nunca utilize geração automática de schema do Hibernate (`update`, `create`, `create-drop`) em desenvolvimento integrado ou produção.
6. **Convenção de nomes:** `VYYYYMMDDHHMM__<modulo>_<descricao_curta>.sql` (timestamp evita conflitos de numeração sequencial entre branches paralelas).
