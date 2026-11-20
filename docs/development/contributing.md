# Guia de Contribuição — Evolution VTT

Este guia resume o fluxo prático para contribuir com código, documentação ou infraestrutura no repositório **Evolution VTT**.

---

## Antes de começar

1. Leia o [`README.md`](../../README.md) na raiz do repositório.
2. Leia o [Blueprint Arquitetural](../architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md) e consulte as [ADRs](../architecture/adr/README.md) e [modelos C4](../architecture/c4/README.md) relacionados à sua tarefa.
3. Revise as convenções em [`governance.md`](governance.md).
4. Se estiver operando como (ou com apoio de) agente de IA, siga integralmente [`AGENTS.md`](../../AGENTS.md).

---

## Fluxo de contribuição

1. **Alinhe o escopo:** confirme que a tarefa está dentro da fase atual do projeto e não depende de pendência arquitetural aberta (`P-001`–`P-017` do Blueprint) ou de nova ADR.
2. **Crie uma branch focada** a partir de `main` (ou `develop`, quando em uso), usando prefixo adequado (`feature/*`, `fix/*`, `docs/*`, `chore/*`).
3. **Implemente de forma incremental:**
   - respeite `.editorconfig` e `.gitattributes` (UTF-8, fim de linha LF);
   - mantenha fronteiras de módulo, separação de camadas e propriedade de dados;
   - nunca adicione credenciais, segredos ou arquivos `.env` reais;
   - preserve arquivos e funcionalidades existentes salvo autorização explícita.
4. **Valide e teste:** crie ou atualize testes coerentes com a alteração (unitários, integração, contrato, autorização ou arquitetura) e execute as verificações aplicáveis.
5. **Atualize a documentação:** reflita mudanças relevantes em `docs/` e nos `README.md` afetados.
6. **Faça commits claros:** siga *Conventional Commits* (`feat`, `fix`, `docs`, `chore`, `refactor`, `test`, etc.).
7. **Abra um Pull Request:** preencha [`.github/PULL_REQUEST_TEMPLATE.md`](../../.github/PULL_REQUEST_TEMPLATE.md) com resumo, referências, evidências de verificação e eventuais riscos/pendências.

---

## Alterações arquiteturais ou de contrato

- **Nova decisão ou mudança arquitetural:** antes de codificar uma mudança que altere ou amplie as ADR-001 a ADR-014, crie uma proposta de ADR em `docs/architecture/adr/` baseada em [`TEMPLATE.md`](../architecture/adr/TEMPLATE.md) para aprovação do responsável.
- **Mudança relevante em contratos públicos** (REST/OpenAPI, STOMP, eventos ou contratos entre módulos): destaque explicitamente no PR, inclua revisão de compatibilidade/versionamento e testes de contrato correspondentes.

---

## Checklist rápido antes de abrir o PR

- [ ] A mudança respeita Blueprint, ADRs e C4 aprovados.
- [ ] Nenhum segredo, token ou credencial está presente no diff.
- [ ] Nenhum arquivo importante existente (como o protótipo em `frontend/`) foi removido ou alterado indevidamente.
- [ ] Testes e documentação relevantes foram atualizados.
- [ ] Commits seguem *Conventional Commits* e o PR está pequeno e focado.
