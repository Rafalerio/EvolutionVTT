# C4-004 — Modelos e Interações Internas

- **Status:** Aprovado

## Decisões transversais

### Modelo de ficha
Modelo híbrido: núcleo comum estável e extensões configuráveis derivadas do sistema de regras. Rules Engine fornece DSL/AST e extensões controladas.

### Modelo de conteúdo
Modelo híbrido que combina hierarquia, metadados/etiquetas e relacionamentos entre conteúdos.

### Modelo de eventos e integração
Combinação de contratos síncronos, eventos internos e orquestração por casos de uso. Outbox quando a entrega durável for necessária. RTC transporta eventos transitórios e atualizações em tempo real, mas não é dono dos dados de negócio.

### Orquestração
Operações que envolvem mais de um módulo são coordenadas por casos de uso na camada Application. Não criar orquestrador genérico. Definir tratamento de falhas parciais, idempotência e compensações quando aplicável.

## Organização interna padrão
- `Application`
- `Domain`
- `Ports/Contracts`
- `Infrastructure/Adapters`

A estrutura é pragmática e variável conforme a complexidade real do módulo. Não criar classes, interfaces, tabelas ou subcomponentes artificiais. Agregados protegem invariantes. Consultas específicas podem utilizar modelos de leitura quando úteis. Eventos de domínio e eventos de integração são conceitos distintos. Contratos públicos não expõem entidades internas.

## Mapa interno de componentes

1. **Identity & Access:** Account Management, Credential Management, Authentication, Session Management, Global Role Management, Security Management, Identity Contracts.
2. **User & Profiles:** Profile Management, Preferences, Public Identity, Profile Privacy, Avatar, Profile Integration.
3. **Content Management:** Catalog, Organization, Authoring, Homebrew, Relationships, Versioning, Publishing, Review & Moderation, Access, Integration.
4. **Rules Engine:** Definition Model, Parsing, Validation, Compilation, Interpreter, Core Operations, Extension API, Execution Context, Randomness, Execution Safety.
5. **Character Management:** Character Management, Data Model, Sheet Definition, Progression, Versioning, Instances, Instance Modification, History, Calculations, Assets.
6. **Campaigns & Tables:** Table Management, Participant Management, Table Permissions, Table Directory, Directory Organization, Character Associations, Content Associations, Scene Associations, Active Scene Management.
7. **VTT & Scene Management:** Scene Management, Scene Configuration, Map Integration, Token Management, Token Control, Scene Object Management, Drawing & Measurement, Visibility, Scene State, Scene Snapshot, Rendering Integration.
8. **RTC:** Connection Management, Authentication Integration, Channel Management, Subscription Authorization, Event Distribution, State Synchronization, Transient Communication, Connection Recovery.
9. **Community & Messaging:** Direct Messaging, Table Messaging, Forum, Announcements, Community Content, Moderation, Attachment Management, Message History.
10. **File Storage:** File Registration, Metadata, Validation, Provider Selection, Storage Adapters, Upload, Download, Reference Management, Orphan Cleanup, Storage Policies.
11. **Commerce (futuro):** Product Catalog, Pricing, Order, Purchase, Payment, License, Entitlement, Transaction History, Commerce File References.

Subcomponentes só devem virar pacotes quando houver responsabilidade clara e necessidade real.
