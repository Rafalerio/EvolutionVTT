# Governança de Desenvolvimento — Evolution VTT

Este documento estabelece as convenções iniciais de engenharia e colaboração do **Evolution VTT**. As regras foram desenhadas para manter qualidade, rastreabilidade e conformidade arquitetural sem introduzir burocracia desnecessária.

---

## 1. Estratégia de branches

- `main` — branch principal, sempre em estado íntegro, revisado e coerente com o estágio atual do projeto.
- `develop` *(opcional / quando necessário)* — pode ser adotada quando houver integração paralela de múltiplas entregas antes de consolidar uma release em `main`. Enquanto o fluxo for enxuto, trabalhe com branches curtas a partir de `main`.
- Branches de trabalho devem ser curtas, focadas e seguir prefixos semânticos:
  - `feature/<escopo-ou-tarefa>` — novas funcionalidades ou entregas estruturais (ex.: `feature/evtt-f0-001-repo-governance`);
  - `fix/<escopo-ou-problema>` — correções de defeitos;
  - `docs/<escopo>` — alterações exclusivas de documentação;
  - `chore/<escopo>` — manutenção, configuração, atualização controlada de dependências ou ajustes internos;
  - `refactor/<escopo>` ou `test/<escopo>` — opcionais quando a mudança for puramente de refatoração ou testes.

**Boas práticas:**
- Use letras minúsculas e hífens (`kebab-case`) nos nomes de branch.
- Evite commits diretos em `main` após a ativação de fluxo por Pull Requests.
- Remova branches temporárias após o merge.

---

## 2. Padrão de commits

O repositório adota **Conventional Commits** (`https://www.conventionalcommits.org/`):

```text
<tipo>(<escopo opcional>): <descrição curta no imperativo>

[corpo opcional explicando o porquê da mudança]

[rodapé opcional: BREAKING CHANGE:, Refs:, ADR-NNN]
```

**Tipos recomendados:**
- `feat` — nova funcionalidade;
- `fix` — correção de erro;
- `docs` — documentação;
- `chore` — tarefas de manutenção e configuração;
- `refactor` — refatoração sem alteração de comportamento externo;
- `test` — adição ou ajuste de testes;
- `build` — mudanças em build ou dependências;
- `ci` — alterações em pipelines CI/CD;
- `perf` — melhoria de desempenho;
- `style` — formatação que não altera lógica.

**Exemplos:**
- `chore(repo): organiza estrutura inicial e governanca (EVTT-F0-001)`
- `docs(architecture): incorpora ADR-001 a ADR-014`
- `feat(identity-access): adiciona emissao de refresh token rotativo`
- `feat(api)!: altera payload do endpoint de sessoes` *(indica quebra de compatibilidade)*

---

## 3. Pull Requests

Todo Pull Request deve:
- manter escopo coeso e revisável (prefira PRs menores e incrementais a mudanças gigantes);
- preencher o template [`.github/PULL_REQUEST_TEMPLATE.md`](../../.github/PULL_REQUEST_TEMPLATE.md);
- referenciar a tarefa, *issue* ou ADR correspondente quando aplicável;
- incluir evidências de verificação (comandos/testes executados) e sinalizar explicitamente riscos, limitações ou pendências;
- manter o histórico limpo e livre de arquivos gerados, temporários ou credenciais.

---

## 4. Revisão de código

A revisão de código deve ser objetiva, construtiva e focada em risco real — não em preferências estilísticas triviais cobertas por formatadores/linters.

**Critérios principais de revisão:**
1. **Conformidade arquitetural:** aderência ao [Blueprint Arquitetural](../architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md), ADRs e modelos C4.
2. **Fronteiras de módulo:** ausência de importação de pacotes internos, dependências circulares ou acesso direto a tabelas/repositórios de outro módulo.
3. **Separação de camadas:** domínio livre de frameworks/transporte; nenhuma regra de negócio na camada de apresentação; modelo de cena independente de PixiJS.
4. **Segurança e autorização:** validação de permissões no backend (REST e WebSocket/STOMP) e ausência de vazamento de dados sensíveis ou segredos.
5. **Contratos e compatibilidade:** alterações relevantes de contratos públicos (REST/OpenAPI, eventos internos, STOMP ou contratos entre módulos) revisadas e versionadas explicitamente.
6. **Testes e verificabilidade:** invariantes críticos, fluxos de erro e autorização cobertos por testes compatíveis com o escopo.
7. **Preservação do escopo:** nenhuma remoção indevida de funcionalidade/artefato existente nem antecipação não autorizada de escopo futuro.

---

## 5. Versionamento

O projeto adota **Versionamento Semântico (`MAJOR.MINOR.PATCH`)**:

- **Releases da plataforma / imagens Docker:** tags versionadas `vMAJOR.MINOR.PATCH` (evitando dependência de `latest` em produção, conforme ADR-013).
- **Contratos de API, eventos e integrações entre módulos:**
  - mudanças compatíveis incrementam `MINOR` ou `PATCH`;
  - mudanças incompatíveis (*breaking changes*) exigem revisão explícita, comunicação clara, estratégia de versionamento/migração e incremento de `MAJOR`.
- **Conteúdos publicados (manuais, regras e definições de ficha):** seguem `MAJOR.MINOR.PATCH` com imutabilidade após publicação e migração explícita aprovada pelo responsável, conforme **ADR-006**.
- **Migrations de banco de dados:** seguem a estratégia *Expand/Contract* com Flyway (ADR-004), mantendo compatibilidade durante implantações progressivas.

---

## 6. Organização da documentação

A documentação é parte da entrega, não um artefato secundário:

- `docs/architecture/blueprint/` — Blueprint Arquitetural consolidado.
- `docs/architecture/adr/` — ADRs aprovadas e propostas.
- `docs/architecture/c4/` — Modelos e diagramas C4.
- `docs/development/` — Governança de engenharia e guia de contribuição.
- `docs/guides/` — Guias técnicos e tutoriais práticos de ambiente/desenvolvimento.
- `docs/runbooks/` — Procedimentos operacionais (deploy, rollback, backup, incidentes).
- `README.md` (raiz e por área principal) — visão geral concisa e navegação rápida.

**Regra de manutenção:** sempre que um PR alterar comportamento arquitetural, contratos públicos, fluxos de setup ou procedimentos operacionais, atualize a documentação correspondente no mesmo PR (ou registre explicitamente a pendência).

---

## 7. Registro de decisões arquiteturais (ADRs)

Crie ou proponha uma ADR em [`docs/architecture/adr/`](../architecture/adr/README.md) usando [`TEMPLATE.md`](../architecture/adr/TEMPLATE.md) sempre que uma mudança:
- introduzir nova tecnologia relevante, framework, banco, *broker* ou provedor externo;
- alterar fronteiras de módulos, estilo arquitetural, estratégia de persistência, autenticação/autorização ou protocolo de tempo real;
- resolver uma pendência técnica arquiteturalmente relevante do Blueprint (`P-001` a `P-017`);
- revisar ou substituir qualquer decisão registrada nas ADR-001 a ADR-014.

**Regra de ouro:** decisões aprovadas nunca são alteradas silenciosamente. Uma nova decisão substitui a anterior de forma explícita (`Superseded by ADR-NNN`).

---

## 8. Tratamento de dependências

Antes de adicionar ou atualizar uma dependência relevante (Maven/Gradle, npm, imagens Docker, módulos Terraform):
1. Verifique se a dependência respeita a stack aprovada nas ADRs (não introduza biblioteca concorrente sem justificativa e aprovação).
2. Avalie necessidade real, manutenção ativa, licença compatível e histórico de vulnerabilidades conhecidas.
3. Prefira bibliotecas enxutas e estáveis; evite dependências redundantes.
4. Mantenha *lockfiles* versionados quando os projetos de backend/frontend forem inicializados para garantir builds reproduzíveis.
5. Evite atualizações massivas misturadas a entregas de funcionalidades; isole atualizações de dependências em PRs dedicados (`chore(deps): ...` ou `build(deps): ...`).

---

## 9. Segurança de credenciais

- Credenciais, chaves de API, senhas de banco, chaves de assinatura JWT/OIDC, chaves R2/OCI e tokens devem ser fornecidos **exclusivamente via variáveis de ambiente** ou gerenciador de segredos do ambiente (GitHub Actions Secrets, cofre/variáveis do servidor OCI).
- Todo serviço que exigir configuração sensível deve disponibilizar um arquivo `.env.example` (ou `*.tfvars.example`) versionado contendo **apenas nomes de variáveis e placeholders fictícios**.
- Utilize credenciais distintas por ambiente (local, homologação e produção) e aplique o princípio do menor privilégio.
- **Nunca registre** senhas, tokens, cabeçalhos `Authorization`, URLs assinadas sensíveis ou dados pessoais críticos em logs ou mensagens de erro.

---

## 10. Proibição de segredos no repositório

É **estritamente proibido** commitar ou manter versionados no repositório:
- arquivos `.env` reais (`.env`, `.env.local`, `.env.prod`, etc.);
- senhas, chaves privadas (`*.pem`, `*.key`), certificados privados (`*.p12`, `*.jks`, `*.keystore`);
- tokens de acesso, segredos OAuth/OIDC, chaves de API ou strings de conexão com credenciais reais;
- arquivos de estado Terraform (`*.tfstate`) ou `*.tfvars` com valores reais;
- dumps reais de banco de dados ou arquivos com dados sensíveis de usuários.

**Em caso de exposição acidental:**
1. **Revogue e rotacione imediatamente** a credencial comprometida (considere-a exposta assim que atingir o repositório remoto).
2. Remova o segredo dos arquivos e, se aplicável, limpe o histórico Git com coordenação do responsável pelo projeto.
3. Registre o incidente e valide que o `.gitignore` cobre o padrão para evitar recorrência.
