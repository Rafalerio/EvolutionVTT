# ADR-015 — Ferramenta de Build, Versões Base de Runtime e Testes Arquiteturais do Backend

| Campo | Valor |
|---|---|
| Identificador | ADR-015 |
| Data | 2026-11-20 |
| Status | Aprovada |
| Responsável | Responsável pelo projeto Evolution VTT |
| Relacionadas | ADR-001, ADR-003, ADR-004, ADR-013, C4-003, EVTT-F0-002 |

---

## 1. Contexto

A [ADR-001](ADR-001-backend.md) definiu Java + Spring Boot em estilo monólito modular com JUnit, mas registrou explicitamente fora de seu escopo:
- a ferramenta de build;
- as versões específicas de Java e Spring Boot;
- a ferramenta complementar de validação arquitetural exigida pela [ADR-003](ADR-003-architecture.md) (*"Testes arquiteturais devem verificar as fronteiras na CI"*).

Na inicialização da fundação técnica do backend (`EVTT-F0-002`), tornou-se necessário fixar essas escolhas para garantir builds reproduzíveis localmente e na futura CI (GitHub Actions), além de proteger as fronteiras do monólito modular desde o primeiro commit de código Java.

## 2. Decisão

1. **Sistema de build:** adotar **Apache Maven 3.x** com **Maven Wrapper (`mvnw` / `mvnw.cmd`)** em modo `only-script` (`wrapperVersion=3.3.4`, distribuição `Apache Maven 3.10.1`), garantindo builds reproduzíveis sem exigir instalação global de Maven.
2. **Versão de Java:** adotar **Java 25 LTS** como baseline de compilação e execução do backend.
3. **Versão de Spring Boot:** adotar a linha estável **Spring Boot 4.1.x** (iniciando em `4.1.3`), visto que a linha Spring Boot 3.5.x atingiu fim do suporte open-source em meados de 2026 e o Spring Boot 4.1.x oferece suporte nativo de primeira classe a Java 25 LTS e logging estruturado nativo.
4. **Organização de pacotes raiz:** adotar `com.evolutionvtt` como pacote raiz:
   - módulos de negócio em `com.evolutionvtt.modules.<modulo>` (com camadas pragmáticas `application`, `domain`, `contracts` e `infrastructure` criadas sob demanda);
   - núcleo técnico estritamente restrito em `com.evolutionvtt.platform` (configuração base, observabilidade/correlação, tratamento global de exceções, metadados de modularidade e endpoints técnicos de sistema), proibindo pacotes genéricos como `common`, `utils` ou `shared`.
5. **Testes arquiteturais:** adotar **ArchUnit** (`com.tngtech.archunit:archunit-junit5`, escopo `test`) integrado à suíte JUnit para validar continuamente:
   - ausência de ciclos entre módulos;
   - isolamento de pacotes internos (`domain`, `application`, `infrastructure`) entre módulos;
   - respeito ao grafo de dependências aprovado no [C4-003](../c4/C4-003-componentes-backend.md);
   - independência das camadas `domain` em relação a frameworks (Spring, JPA/Hibernate, Web/Servlet, WebSocket/STOMP);
   - proibição de dependência de `platform` sobre `modules` e de módulos de domínio sobre `real-time-communication`.

## 3. Alternativas consideradas

- **Gradle (Kotlin DSL) com Gradle Wrapper:** oferece excelente cache incremental para repositórios multi-projeto grandes, porém adiciona maior complexidade acidental de manutenção (scripts imperativos e acoplamento entre versão de Gradle, plugins e JDK 25) sem benefício proporcional para um monólito modular único.
- **Java 21 LTS:** alternativa sólida e madura, mas com menor horizonte de longevidade e sem os ganhos nativos do JDK 25 LTS já disponível no ambiente e suportado de primeira classe pelo Spring Boot 4.1.x.
- **Apenas JUnit sem biblioteca arquitetural na Fase 0:** postergaria a verificação automatizada de fronteiras e aumentaria o risco de acoplamento acidental já na Fase 1.

## 4. Consequências

### Positivas
- Builds determinísticos e reproduzíveis via `./mvnw` / `.\mvnw.cmd` em Windows, Linux, macOS e GitHub Actions.
- Alta legibilidade e previsibilidade do `pom.xml` declarativo para desenvolvedores e agentes de IA.
- Stack em versões ativamente mantidas (Java 25 LTS + Spring Boot 4.1.x), sem dívida técnica inicial de migração de Spring Boot 3 para 4.
- Fronteiras do monólito modular protegidas automaticamente desde o primeiro dia com ArchUnit.

### Negativas / Trade-offs
- `pom.xml` é mais verboso que DSLs de build baseadas em código.
- A adoção de Java 25 exige atenção à compatibilidade de bibliotecas futuras de terceiros (mitigada validando suporte antes de adicionar dependências, conforme governança).

### Impactos em módulos, contratos ou operação
- Pipelines futuros de CI/CD (ADR-013) e Dockerfiles deverão utilizar JDK 25 e invocar `./mvnw`.
- Todos os módulos de backend devem declarar `@ArchitecturalModule` em seu `package-info.java` e manter contratos públicos exclusivamente em `contracts`.

## 5. Referências

- [ADR-001 — Backend: Java + Spring Boot](ADR-001-backend.md)
- [ADR-003 — Arquitetura: Monólito Modular, Clean Architecture e DDD](ADR-003-architecture.md)
- [ADR-004 — Persistência e Dados](ADR-004-data.md)
- [C4-003 — Componentes do Backend](../c4/C4-003-componentes-backend.md)
- [Blueprint Arquitetural v1.0.0](../blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md)
