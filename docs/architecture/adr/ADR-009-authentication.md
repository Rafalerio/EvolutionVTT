# ADR-009 — Identidade, Autenticação e Autorização

- **Status:** Aprovada

## Decisão
- Spring Security.
- OAuth 2.1 / OpenID Connect.
- Evolution VTT atua como emissor de identidade (OIDC).
- Login por e-mail e senha.
- Access tokens de curta duração.
- Refresh tokens com rotação, detecção de reutilização e revogação.
- Argon2id para armazenamento de senhas.
- RBAC global separado de permissões contextuais por mesa.
- Autorização também aplicada aos canais e destinos STOMP.

## Modelo de sessão
Contas, credenciais, sessões e dispositivos são conceitos separados. Um dispositivo pode possuir várias sessões. Deve ser possível revogar uma sessão específica ou todas as sessões.

## Consequências
Autenticação não substitui autorização. Cada operação deve validar o contexto e a permissão correspondente. Parâmetros concretos de duração de tokens, política de login e retenção permanecem configuráveis/pendentes de definição.
