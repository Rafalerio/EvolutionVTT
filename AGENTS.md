# AGENTS.md — Diretrizes para Agentes de IA no Evolution VTT

Este documento define as regras obrigatórias para qualquer agente de IA (ou automação assistida) que inspecione, documente, projete ou implemente código no repositório **Evolution VTT**.

---

## Hierarquia de referência documental

Em caso de dúvida ou divergência, siga estritamente esta ordem de precedência:

1. **ADR aprovada mais recente** para a decisão arquitetural específica (`docs/architecture/adr/`).
2. **Modelo C4 aprovado** e seus refinamentos (`docs/architecture/c4/`).
3. **Blueprint Arquitetural consolidado** (`docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md`).
4. **Documentação inicial e requisitos originais**, quando não substituídos por decisões posteriores.
5. **Implementação existente**, que nunca deve ser assumida como arquiteturalmente correta apenas por já existir.

---

## Regras obrigatórias

### 1. Leitura prévia obrigatória
Antes de alterar qualquer arquivo ou propor código, leia:
- [`README.md`](README.md);
- o [Blueprint Arquitetural](docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md);
- as ADRs (`docs/architecture/adr/`) e modelos C4 (`docs/architecture/c4/`) pertinentes à tarefa;
- a [Governança de Desenvolvimento](docs/development/governance.md).

### 2. Respeito irrestrito às decisões aprovadas
Respeite integralmente as **ADR-001 a ADR-014** e os modelos **C4-001 a C4-005**.
Não substitua, contradiga, flexibilize ou reinterprete silenciosamente decisões já aprovadas.

### 3. Proibição de novas tecnologias sem justificativa e aprovação
Não introduza frameworks, bibliotecas relevantes, bancos de dados, *brokers*, ferramentas de infraestrutura ou padrões arquiteturais novos que afetem decisões aprovadas sem apresentar justificativa técnica explícita e obter aprovação do responsável pelo projeto (com registro em ADR quando aplicável).

### 4. Respeito às fronteiras e contratos entre módulos
O backend é um **monólito modular** organizado em 11 módulos arquiteturais (`identity-access`, `user-profiles`, `content-management`, `rules-engine`, `character-management`, `campaigns-tables`, `vtt-scene-management`, `real-time-communication`, `community-messaging`, `file-storage` e `commerce` futuro).
- Não crie dependências entre módulos que contrariem o grafo de dependências ou os contratos arquiteturais aprovados.
- Não importe pacotes internos de outro módulo; comunique-se exclusivamente por **contratos públicos (ports/DTOs)** e **eventos**.
- Dependências circulares são proibidas.
- Não transforme `real-time-communication` em proprietário de dados de domínio nem em dependência de domínio dos módulos de negócio.

### 5. Isolamento estrito de dados entre módulos
Cada módulo é o único proprietário de seus dados e de seu *schema* no PostgreSQL.
- **Nunca** acesse diretamente tabelas, *views* internas, entidades JPA ou repositórios pertencentes a outro módulo.
- **Nunca** utilize *foreign keys* entre *schemas* como mecanismo de integração entre módulos.
- Identificadores de outros módulos (como `AccountId`) são valores de contrato, não autorização para consultar o *schema* proprietário.

### 6. Separação de responsabilidades e autoridade do backend
- **Não coloque regras de negócio na camada de apresentação** (componentes React, hooks de UI, *controllers* REST, *handlers* WebSocket/STOMP ou adaptadores PixiJS).
- Mantenha a camada de domínio independente de Spring, WebSocket/STOMP, PixiJS e SDKs de provedores de armazenamento.
- Mantenha o modelo de cena independente da engine gráfica 2D (**PixiJS**) e persista coordenadas em *world space*.
- O **backend é autoridade** sobre regras, permissões contextuais, visibilidade (filtragem de elementos ocultos) e estado oficial das mesas. Não confie em validações apenas no cliente.

### 7. Segurança e proteção absoluta de segredos
- **Nunca** versione, exponha em código, inclua em testes ou escreva em documentação credenciais reais, senhas, tokens, chaves privadas, segredos de API ou arquivos `.env`.
- **Nunca** registre credenciais, tokens ou dados sensíveis em logs.
- Use exclusivamente variáveis de ambiente e arquivos `.env.example` contendo apenas *placeholders* seguros.

### 8. Testes junto às funcionalidades
- Sempre crie ou atualize testes correspondentes ao implementar ou alterar funcionalidades (testes unitários de domínio/aplicação, integração, contrato, autorização e testes arquiteturais de fronteira, conforme aplicável).
- Avalie invariantes de domínio e cenários críticos; não trate percentual de cobertura isolado como garantia de qualidade.

### 9. Documentação de decisões relevantes
- Documente decisões técnicas relevantes no local apropriado (`docs/architecture/`, `docs/development/`, `docs/guides/` ou `docs/runbooks/`).
- Se uma tarefa exigir decisão arquitetural nova ou alteração de decisão existente, **interrompa a implementação dessa parte**, apresente uma proposta clara e registre/proponha a ADR correspondente antes de prosseguir.
- Atualize documentação e diagramas sempre que uma mudança impactar contratos, fronteiras ou comportamento arquitetural.

### 10. Preservação de funcionalidades e artefatos existentes
- Não remova, sobrescreva ou descarte funcionalidades, arquivos ou artefatos existentes sem autorização explícita do responsável pelo projeto.
- Em especial, preserve o protótipo frontend inicial em `frontend/` até que exista tarefa aprovada especificamente para sua migração.

### 11. Controle estrito de escopo e pendências abertas
- **Não presuma** que funcionalidades futuras fazem parte do escopo atual. Permanecem fora do primeiro lançamento (salvo autorização explícita): `commerce`/pagamentos, mapas 3D, scripting arbitrário de usuários, microsserviços, Kubernetes, sincronização offline avançada, Fog of War completo, *broker* externo e app mobile nativo.
- **Não invente** especificações para as pendências técnicas registradas no Blueprint (`P-001` a `P-017`, como TTL de tokens, gramática formal da DSL, escala de ponto fixo, catálogo formal STOMP, limites de upload). Quando esbarrar em uma pendência aberta, sinalize-a explicitamente ou solicite definição.

### 12. Relatório transparente ao concluir tarefas
Ao concluir qualquer tarefa, informe objetivamente:
- o que foi alterado (arquivos criados, modificados e preservados);
- decisões tomadas e suas justificativas;
- verificações e testes executados (ou motivo de não execução);
- limitações conhecidas, riscos identificados e pendências remanescentes.

---

## Módulos arquiteturais e propriedade de dados (referência rápida)

| Módulo | Schema PostgreSQL | Responsabilidade principal |
|---|---|---|
| `identity-access` | `identity_access` | Contas (`AccountId`), credenciais, OAuth 2.1/OIDC, sessões, dispositivos e papéis globais |
| `user-profiles` | *(a fixar no padrão do módulo)* | Perfil público, preferências, privacidade e avatar |
| `content-management` | *(a fixar no padrão do módulo)* | Catálogo, hierarquia, autoria, homebrew, moderação, publicação e versões imutáveis |
| `rules-engine` | `rules_engine` | DSL declarativa, AST, validação, compilação e execução determinística com limites |
| `character-management` | `character_management` | Fichas, definições versionadas, progressão, histórico e instâncias contextuais (`frozen`/`local`) |
| `campaigns-tables` | `campaigns_tables` | Mesas, participantes, convites, permissões contextuais, diretório e cena ativa |
| `vtt-scene-management` | *(a fixar no padrão do módulo)* | Cenas, mapas associados, tokens, objetos, visibilidade, grade e *world space* |
| `real-time-communication` | *(transporte; sem dados de domínio)* | Conexões WebSocket/STOMP, autenticação/autorização de canais, distribuição e recuperação |
| `community-messaging` | *(a fixar no padrão do módulo)* | Mensagens diretas e de mesa, fóruns, avisos, moderação e histórico |
| `file-storage` | `file_storage` | Metadados, validação real de conteúdo, quarentena, URLs assinadas e adaptadores R2/OCI/Local |
| `commerce` *(futuro)* | *(futuro)* | Catálogo comercial, pedidos, pagamentos, licenças e direitos comerciais |

---

## Checklist rápido do agente

Antes de implementar:
- [ ] Li `README.md`, Blueprint, ADRs e C4 relevantes?
- [ ] Identifiquei o módulo proprietário dos dados e das regras?
- [ ] A tarefa respeita todas as fronteiras de módulo e não esbarra em pendência aberta (`P-001`–`P-017`) ou mudança de ADR?

Antes de finalizar:
- [ ] Nenhum segredo, token ou credencial foi adicionado ou logado?
- [ ] Nenhum arquivo existente relevante foi removido ou sobrescrito sem autorização?
- [ ] Testes e documentação foram atualizados conforme o escopo da tarefa?
- [ ] Limitações, riscos e pendências foram reportados claramente?
