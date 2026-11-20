# ADR-006 — Versionamento e Ativação de Conteúdo

- **Status:** Aprovada
- **Substitui:** interpretação de atualização automática prevista no requisito original RN04.

## Decisão
Conteúdos e manuais publicados possuem versões imutáveis. Uma mesa fixa as versões de conteúdo que utiliza.

## Ciclo de vida
- Conteúdo pode ser elaborado como rascunho.
- Publicação cria uma versão identificável.
- Alterações posteriores geram nova versão; não alteram a versão já publicada.
- O versionamento inicial utiliza SemVer.
- Mesas continuam vinculadas às versões selecionadas.

## Migração
A atualização de conteúdo em uma mesa exige migração explícita, revisada e aprovada pelo responsável da mesa. Não há atualização automática silenciosa das mesas quando um manual muda.

## Consequências
- Reprodutibilidade e previsibilidade das regras em uma mesa.
- Histórico rastreável das versões utilizadas.
- Compatibilidade e migrações precisam ser tratadas explicitamente.
- A decisão substitui a regra original que previa reflexo imediato de ajustes de manuais nas mesas.
