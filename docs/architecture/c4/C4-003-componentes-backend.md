# C4-003 — Componentes do Backend

- **Status:** Aprovado integralmente

## 1. Módulos de domínio

1. Identity & Access
2. User & Profiles
3. Content Management
4. Rules Engine
5. Character Management
6. Campaigns & Tables
7. VTT & Scene Management
8. Real-time Communication (RTC)
9. Community & Messaging
10. File Storage
11. Commerce (futuro)

## 2. Responsabilidades e decisões

### Identity & Access
Contas, credenciais, autenticação, sessões, dispositivos, papéis globais e segurança.

### User & Profiles
Perfis públicos, preferências, identidade pública, privacidade, avatar e integrações do perfil.

### Content Management
Catálogo, organização, autoria, homebrew, relacionamentos, versionamento, publicação, revisão/moderação, acesso e integração.

### Rules Engine
Modelo de definição, parsing, validação, compilação, interpretação, operações centrais, extensões confiáveis, contexto de execução, aleatoriedade e segurança.

### Character Management
Fichas, modelo de dados, definição de ficha, progressão, versionamento, instâncias, modificações, histórico, cálculos e ativos.

### Campaigns & Tables
Mesas, participantes, permissões, diretório, organização, associações de personagens/conteúdos/cenas e cena ativa. Uma mesa é uma sala de interação; não exige que exista uma campanha ou aventura como estrutura obrigatória.

### VTT & Scene Management
Cenas, configuração, integração de mapas, tokens, controle de tokens, objetos, desenho/medição, visibilidade, estado, snapshots e integração com renderização.

### RTC
Conexões, autenticação de conexão, canais, autorização de inscrições, distribuição de eventos, sincronização, comunicação transitória e recuperação.

### Community & Messaging
Mensagens diretas, mensagens de mesa, fóruns, avisos, conteúdo comunitário, moderação, anexos e histórico de mensagens.

### File Storage
Registro, metadados, validação, seleção de provedor, adaptadores, upload/download, referências, limpeza de órfãos e políticas de armazenamento.

### Commerce (futuro)
Catálogo de produtos, preços, pedidos, compras, pagamentos, licenças, direitos de acesso, histórico transacional e referências de arquivos.

## 3. Decisões específicas de domínio

### RTC versus Community & Messaging
RTC é transporte, canais, distribuição e sincronização. Community & Messaging é proprietário do domínio das mensagens, fóruns e avisos. RTC integra com VTT e Community por interfaces/eventos internos, sem dependência direta do domínio desses módulos.

### Diretório da mesa
O diretório contém fichas de personagens, fichas de NPCs e mapas. É hierárquico, e cada recurso fica em uma pasta. O mestre administra o diretório e concede permissões. Jogador padrão possui `view_add`: pode visualizar e adicionar/vincular seus próprios recursos, mas não edita recursos compartilhados, organiza pastas, remove recursos ou gerencia permissões.

Recursos podem ser vinculados ou copiados. Uma cópia é logicamente independente, mesmo que haja deduplicação física de armazenamento. NPC criado no diretório pertence à mesa. Mapas podem ser reutilizados em várias cenas.

### Personagens e instâncias
O personagem original pertence à conta criadora. Ao vinculá-lo a uma mesa, é criada uma instância contextual congelada (`frozen`) no momento do vínculo, ou uma instância local (`local`) escolhida pelo mestre. Edições locais não alteram o original nem instâncias em outras mesas. Character Management é dono das fichas, instâncias e histórico; Campaigns & Tables é dono dos vínculos, contexto e autorização.

### Mapas, cenas e objetos
Map Asset é um recurso do diretório. Cena é o ambiente configurado dentro de uma mesa. Scene Objects são elementos da cena. Uma cena pertence a uma mesa; uma mesa pode ter várias cenas; um mapa pode ser reutilizado em várias cenas.

### VTT e tokens
Uma mesa possui várias cenas e uma cena ativa compartilhada por todos. Cena contém tokens e objetos livres. Tokens são instâncias visuais independentes, vinculadas a uma ficha contextual ou independentes. Atributos herdados são snapshot no momento da criação; token mantém estado próprio. No máximo um jogador controla cada token; tokens podem estar sem atribuição; o mestre controla todos.

Jogador, por padrão, pode mover e rotacionar seu token. Mestre administra aparência, tamanho, nome, barras, marcadores, visibilidade e exclusão, salvo permissões futuras. Posição final deve respeitar snap; preview pode ser contínuo. Visibilidade é individual por elemento e conteúdo oculto deve ser filtrado no backend. Fog of War é futuro.

### Cenas e conflitos
Mestre administra cenas e visibilidade. Exclusão pode ser arquivamento ou exclusão definitiva. Grade configurável por cena; grades quadrada e hexagonal. Calibração da grade por cena. Distância segue a política do sistema de regras, com fallback explícito. Coordenadas usam world space independente de pixels, grade e tela. Unidade espacial é abstrata; precisão de ponto fixo é prevista, escala ainda a definir. Limites do mundo por cena devem ser separados dos limites de renderização.

Conflitos simultâneos: prioridade do mestre no servidor, validação de revisão e proteção contra comandos antigos ou duplicados.

### Persistência e eventos
Alterações relevantes são persistidas pelo módulo proprietário. Cursor, seleção, preview e arraste podem ser transitórios. O backend é autoridade do estado oficial; PixiJS é apenas renderização/interação.

## 4. Dependências entre módulos

- User & Profiles → Identity & Access, File Storage
- Content Management → Identity & Access, File Storage, Rules Engine
- Character Management → Content Management, Rules Engine, File Storage
- Campaigns & Tables → Identity & Access, Character Management, Content Management
- VTT & Scene Management → Campaigns & Tables, Character Management, File Storage
- Community & Messaging → Identity & Access, Campaigns & Tables, File Storage
- Commerce → Identity & Access, Content Management, File Storage
- RTC → Identity & Access, Campaigns & Tables
- Identity & Access, Rules Engine e File Storage não possuem dependências de domínio.

As dependências são lógicas e mediadas por contratos, não necessariamente imports diretos ou chamadas REST. Operações multi-módulo são orquestradas por casos de uso da camada de aplicação, com idempotência, tratamento de falhas parciais e compensações quando aplicáveis. Outbox é usada para eventos duráveis quando necessário.

## 5. Regras de fronteira
- Sem imports de pacotes internos de outros módulos.
- Sem acesso a tabelas ou repositórios de outros módulos.
- Sem foreign keys entre schemas como mecanismo de integração.
- Contratos e eventos versionados.
- Testes arquiteturais na CI.
- Domínio não depende de WebSocket/STOMP/PixiJS.
- Módulos de domínio não dependem do RTC.
