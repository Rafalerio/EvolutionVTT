# C4-001 — Diagrama de Contexto

- **Status:** Aprovado

## Objetivo
Delimitar o Evolution VTT, seus usuários e sistemas externos relevantes.

## Atores
- **Visitante:** consulta conteúdos públicos e gratuitos sem necessidade de conta.
- **Jogador:** participa de mesas, gerencia personagens e pode criar/publicar homebrews sujeitos à moderação.
- **Mestre:** cria e administra mesas, participantes, permissões, conteúdo e cenas.
- **Moderador:** avalia e modera conteúdos comunitários.
- **Administrador:** administra funções globais da plataforma e conteúdo oficial conforme permissões.

Não existe um perfil global separado de “criador”: jogadores podem criar e publicar homebrews sob as regras de moderação.

## Sistema
Evolution VTT oferece clientes web, desktop e mobile que utilizam a mesma plataforma e backend. O backend é autoridade para regras, permissões e estado das mesas.

## Conteúdo e comércio
- Manual Base gratuito e público.
- Conteúdos licenciados sujeitos aos controles de acesso definidos pela plataforma.
- Comércio está previsto arquiteturalmente, mas não é requisito do primeiro lançamento.

## Armazenamento externo
- Cloudflare R2 para imagens em produção.
- OCI Object Storage para outros arquivos.

## Relações principais
Os clientes interagem com o backend por REST e WebSocket. O backend coordena identidade, conteúdo, regras, personagens, mesas, VTT, comunicação e armazenamento.
