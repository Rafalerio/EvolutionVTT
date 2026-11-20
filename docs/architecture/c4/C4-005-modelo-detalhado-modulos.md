# C4-005 — Modelo Detalhado dos Módulos

- **Status:** Aprovado integralmente
- **Escopo:** C4-005.01 a C4-005.05

Este documento consolida os modelos e contratos aprovados para os módulos Identity & Access, File Storage, Rules Engine, Character Management e Campaigns & Tables.

## C4-005.01 — Identity & Access

### Decisões
- `AccountId` imutável.
- E-mail pode ser alterado e deve ser verificado.
- Conta, credenciais, sessões e dispositivos são conceitos separados.
- Um dispositivo pode possuir várias sessões.
- Deve ser possível revogar uma sessão ou todas as sessões.
- Evolution VTT é emissor OIDC.
- Senhas protegidas com Argon2id.
- Access token curto; refresh token rotativo e revogável.
- Detecção de reutilização de refresh token.
- RBAC global separado de permissões contextuais.

### Modelos conceituais
Account, Credentials, Session, Device, GlobalRole e tokens de recuperação/reset de uso único.

### Eventos
`AccountRegistered`, `AccountActivated`, `AccountSuspended`, `AccountReactivated`, `CredentialsChanged`, `AuthenticationRevoked`, `SessionCreated`, `SessionRevoked`, `GlobalRoleAssigned`, `GlobalRoleRemoved`.

### Persistência e contratos
Schema: `identity_access`. Contratos previstos para autenticação, sessão, consulta de identidade e papéis globais. Parâmetros de duração de tokens, política de login e retenção permanecem pendentes.

## C4-005.02 — File Storage

### Decisões
- Upload direto por URL assinada.
- Deduplicação física permitida, preservando independência lógica.
- Exclusão lógica seguida de exclusão física assíncrona.
- Quarentena obrigatória antes de disponibilizar arquivo.
- R2 para imagens em produção; OCI Object Storage para demais arquivos; Local em dev/test.
- Interfaces e adaptadores para provedores.
- PostgreSQL guarda metadados.
- Autorização de negócio pertence aos módulos de domínio consumidores.

### Ciclo de vida
`PENDING_UPLOAD` → `UPLOADED` → `VALIDATING` → `AVAILABLE` ou `QUARANTINED`.
Exclusão: `PENDING_DELETION` → `DELETED`.

### Eventos
`FileUploadInitiated`, `FileUploaded`, `FileValidated`, `FileAvailable`, `FileQuarantined`, `FileDeletionRequested`, `FileDeleted`, `FileIntegrityCheckFailed`.

### Persistência
Schema: `file_storage`. Modelos conceituais incluem registro, metadados, validação, provedor, referência e política de armazenamento.

## C4-005.03 — Rules Engine

### Decisões
- DSL própria e AST canônica.
- AST + representação intermediária (IR).
- Histórico técnico e aleatoriedade pertencem ao motor; histórico de negócio pertence ao módulo solicitante.
- Extensões confiáveis e controladas.
- Scripting fornecido por usuários fora do escopo inicial.
- Validação estrutural, semântica e compilação.
- Determinismo dado contexto e aleatoriedade registrada.
- Limites de profundidade, operações, tempo, memória, iterações e referências.
- Sem rede, arquivos ou acesso ao sistema operacional durante execução.

### Modelos conceituais
`RuleSystemDefinition`, `RuleSystemVersion`, `RuleDefinition`, `Expression/AST`, `CompiledRule`, `ExecutionRequest`, `ExecutionContext`, `ExecutionResult`, `RandomnessRecord`, `RuleExtension`, `ExecutionLimits`.

### Contratos
Query, Validation, Compilation, Execution, SystemQuery, Compatibility e ExtensionRegistry.

### Eventos
Eventos de definição, validação, publicação, depreciação, compilação e execução.

### Responsabilidade de publicação
Content Management é dono da publicação do conteúdo; Rules Engine é dono da validação, compilação e execução técnica.

### Níveis funcionais
- **A:** aritmética, dados, variáveis, referências, comparações e funções.
- **B:** regras compostas, efeitos, dependências e extensões controladas.
- **C:** scripting futuro, sujeito a ADR específica.

### Persistência
Schema: `rules_engine`.

## C4-005.04 — Character Management

### Decisões
- Personagem pertence à conta criadora.
- `AccountId` é estável.
- Transferência de propriedade, se implementada no futuro, será explícita.
- Instância de mesa pode ser `frozen` (snapshot no vínculo) ou `local` (edição contextual escolhida pelo mestre).
- Alterações relevantes geram versão lógica.
- Núcleo tipado + extensões JSONB validadas pela definição da ficha.
- Histórico imutável; restauração cria nova operação.
- Ficha referencia definição imutável.
- Rules Engine realiza cálculos.
- Content fornece definição; Campaigns & Tables é dono do vínculo/contexto/autorização; Character Management é dono da ficha, instâncias e histórico; File Storage armazena ativos.

### Histórico
Registrar autor, instante, personagem/instância, mesa quando aplicável, campo, valores anterior/novo, origem e correlação.

### Eventos
`CharacterCreated`, `CharacterUpdated`, `CharacterVersionCreated`, `CharacterProgressionApplied`, `CharacterInstanceCreated`, `CharacterInstanceModified`, `CharacterInstanceDetached`, `CharacterAssetAssociated`, `CharacterAssetRemoved`, `CharacterCalculationCompleted`.

### Persistência
Schema: `character_management`.

## C4-005.05 — Campaigns & Tables

### Escopo
Mesas, participantes, convites, permissões, diretório, vínculos/cópias de recursos, associações de cenas e cena ativa.

### Decisões de domínio
- Mesa é sala de interação e não exige entidade campanha/aventura.
- Mestre administra mesa, participantes, convites e permissões.
- Diretório contém fichas de personagens, fichas de NPCs e mapas.
- Diretório hierárquico; cada recurso pertence a uma pasta.
- Jogador padrão recebe `view_add`: visualiza e adiciona/vincula recursos próprios; não edita compartilhados, organiza pastas, remove recursos ou gerencia permissões.
- Recurso pode ser vinculado ou copiado. Cópia é independente logicamente.
- NPC criado no diretório pertence à mesa.
- Mapas podem ser associados a várias cenas.
- Personagem original vinculado gera instância contextual congelada (`frozen`) ou local (`local`), conforme escolha do mestre.
- Campaigns & Tables é dono dos vínculos, contexto e autorização; Character Management é dono dos dados e histórico das fichas/instâncias.
- Uma mesa pode ter várias cenas; uma cena pertence a uma mesa; a cena ativa é compartilhada.
- Mestre administra cenas e visibilidade.
- Exclusão de cena pode ser arquivamento ou exclusão definitiva.

### Persistência e integração
Schema por módulo, sem acesso direto a tabelas de outros módulos. Integrações via contratos e casos de uso, respeitando C4-003 e C4-004.

### Pendências
Detalhes de convites, expiração, estados de participação, granularidade completa de permissões e políticas de retenção devem ser definidos na especificação de implementação, sem contrariar as decisões aprovadas.
