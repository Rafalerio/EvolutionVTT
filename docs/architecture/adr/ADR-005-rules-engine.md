# ADR-005 — Rules Engine

- **Status:** Aprovada

## Decisão
Criar um núcleo declarativo de regras, com extensões confiáveis e controladas. Scripting arbitrário por usuários não faz parte do escopo inicial.

## Representação e execução
- DSL própria.
- AST canônica independente da sintaxe de entrada.
- Validação estrutural e semântica antes da publicação/execução.
- Compilação para representação intermediária (IR) quando aplicável.
- Execução determinística dado o mesmo contexto e os mesmos registros de aleatoriedade.
- Registrar a aleatoriedade utilizada para permitir auditoria/reprodução.
- Aplicar limites de profundidade, operações, tempo, memória, iterações e referências.
- Sem acesso à rede, arquivos ou sistema operacional durante a execução.

## Extensões
Extensões são confiáveis, implementadas e controladas pela plataforma. Scripting fornecido por usuários fica fora do escopo e exige ADR futura.

## Evolução progressiva
- **Nível A:** aritmética, dados, variáveis, referências, comparações e funções.
- **Nível B:** regras compostas, efeitos, dependências e extensões controladas.
- **Nível C:** scripting futuro, somente após decisão arquitetural específica.

## Responsabilidades
Rules Engine valida e executa definições. Content Management é responsável pelo ciclo editorial/publicação do conteúdo. O módulo solicitante mantém o histórico de negócio; o motor mantém o histórico técnico e os registros de aleatoriedade.
