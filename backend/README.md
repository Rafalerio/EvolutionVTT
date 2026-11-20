# Backend — Evolution VTT

Aplicação backend do **Evolution VTT**, construída como **monólito modular** em **Java + Spring Boot** seguindo **Clean Architecture** e **DDD**, conforme o [Blueprint Arquitetural](../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md), [ADR-001](../docs/architecture/adr/ADR-001-backend.md), [ADR-003](../docs/architecture/adr/ADR-003-architecture.md), [ADR-004](../docs/architecture/adr/ADR-004-data.md), [ADR-014](../docs/architecture/adr/ADR-014-observability.md), [ADR-015](../docs/architecture/adr/ADR-015-backend-build-and-runtime.md) e [C4-003](../docs/architecture/c4/C4-003-componentes-backend.md).

---

## 1. Runtime, Framework e Sistema de Build

| Item | Definição aprovada | Referência |
|---|---|---|
| **Java** | **Java 25 LTS** | ADR-015 |
| **Spring Boot** | **Spring Boot 4.1.3** (linha `4.1.x`, Spring Framework 7) | ADR-001, ADR-015 |
| **Sistema de build** | **Apache Maven 3.10.1** via **Maven Wrapper (`mvnw` / `mvnw.cmd`, v3.3.4)** | ADR-015 |
| **Persistência** | PostgreSQL + Spring Data JPA + Hibernate (`ddl-auto=validate`) + Flyway | ADR-004 |
| **Testes** | JUnit (Spring Boot 4 modular test starters) + **ArchUnit 1.5.1** | ADR-001, ADR-003, ADR-015 |
| **Logging estruturado** | SLF4J + Logback nativo do Spring Boot (`ecs` JSON + `correlationId` via MDC) | ADR-014 |

### Por que Apache Maven com Maven Wrapper?

- **Simplicidade e manutenção:** `pom.xml` declarativo, ciclo de vida padronizado e previsível para desenvolvedores e agentes de IA, sem complexidade acidental de scripts imperativos.
- **Alinhamento ao Spring Boot:** suporte oficial de primeira classe (`spring-boot-starter-parent`, BOM e ecossistema Flyway/OpenAPI/Docker maduro).
- **Reprodutibilidade:** o Maven Wrapper em modo `only-script` fixa a distribuição do Maven sem exigir instalação global e sem versionar binários `.jar` no repositório.
- **Decisão formal:** registrada na [ADR-015](../docs/architecture/adr/ADR-015-backend-build-and-runtime.md).

---

## 2. Pré-requisitos e Configuração do Ambiente

### Pré-requisitos
- **JDK 25 LTS** instalado e disponível no `PATH` (ou `JAVA_HOME` apontando para o JDK 25).
- Não é necessário instalar Maven globalmente — use `./mvnw` (Linux/macOS) ou `.\mvnw.cmd` (Windows).
- **PostgreSQL** *(necessário apenas ao executar com o profile `dev` ou `prod`; na fundação atual da Fase 0, o profile `local` inicia sem banco de dados)*.

### Variáveis de ambiente

Consulte o modelo seguro em [`.env.example`](.env.example). Nunca versione arquivos `.env` reais.

| Variável | Padrão | Descrição |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | *(nenhum / base)* | Profile ativo: `local` (sem banco na Fase 0), `dev` (com PostgreSQL) ou `prod`. |
| `SERVER_PORT` | `8080` | Porta HTTP da aplicação. |
| `APP_VERSION` | `0.0.1-SNAPSHOT` | Versão reportada em `/actuator/info` e `/api/v1/system/info`. |
| `APP_ENVIRONMENT` | `default` *(sobrescrito pelo profile)* | Identificador textual não sensível do ambiente. |
| `DB_URL` | *(vazio)* | URL JDBC do PostgreSQL (obrigatória em `dev` e `prod`). |
| `DB_USERNAME` | *(vazio)* | Usuário do PostgreSQL (obrigatório em `dev` e `prod`). |
| `DB_PASSWORD` | *(vazio)* | Senha do PostgreSQL (obrigatória em `dev` e `prod`). |
| `LOG_STRUCTURED_FORMAT` | `ecs` | Formato de log estruturado no console (`ecs`, `logstash`, `gelf` ou vazio para texto legível). |
| `LOG_LEVEL_ROOT` | `INFO` | Nível de log raiz. |
| `LOG_LEVEL_APP` | `INFO` (`DEBUG` em `local`/`dev`) | Nível de log para o pacote `com.evolutionvtt`. |

---

## 3. Como Executar Localmente e Rodar Testes

Todos os comandos abaixo devem ser executados a partir do diretório `backend/`.

### Compilar e executar todos os testes (unitários, integração web e arquiteturais)

- **Windows (PowerShell):**
  ```powershell
  .\mvnw.cmd test
  ```
- **Linux / macOS:**
  ```bash
  ./mvnw test
  ```

### Iniciar a aplicação localmente (Fase 0 — sem PostgreSQL provisionado)

O profile **`local`** isola explicitamente a autoconfiguração de DataSource/JPA/Flyway para permitir subir a fundação localmente sem credenciais externas, sem ocultar falhas de configuração nos profiles `dev` e `prod` (onde PostgreSQL + Flyway permanecem obrigatórios):

- **Windows (PowerShell):**
  ```powershell
  .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
  ```
- **Linux / macOS:**
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
  ```

### Iniciar a aplicação integrada ao PostgreSQL (profile `dev`)

Quando o banco PostgreSQL estiver disponível (ex.: após a configuração de Docker Compose nas próximas tarefas):

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/evolutionvtt"
$env:DB_USERNAME="seu_usuario"
$env:DB_PASSWORD="sua_senha"
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

### Verificar endpoints técnicos disponíveis

- **Health check operacional:** `GET http://localhost:8080/actuator/health` (retorna `{"status":"UP"}`, sem detalhes sensíveis).
- **Probes Kubernetes/Container:** `GET /actuator/health/liveness` e `GET /actuator/health/readiness`.
- **Informações operacionais Actuator:** `GET http://localhost:8080/actuator/info`.
- **Informações básicas da API:** `GET http://localhost:8080/api/v1/system/info`.
- **Correlação:** todas as respostas HTTP incluem o cabeçalho `X-Correlation-Id` (propagado se válido ou gerado como UUID).

---

## 4. Estrutura Modular e Convenções de Pacotes

Pacote raiz: **`com.evolutionvtt`** (minúsculas, padrão Java, sem pacotes genéricos como `common`, `utils` ou `shared`).

```text
backend/
├── .mvn/wrapper/maven-wrapper.properties
├── mvnw / mvnw.cmd
├── pom.xml
├── .env.example
└── src/
    ├── main/
    │   ├── java/com/evolutionvtt/
    │   │   ├── EvolutionVttApplication.java          # Classe principal Spring Boot
    │   │   ├── platform/                             # Núcleo técnico estritamente limitado (zero regra de negócio)
    │   │   │   ├── config/                           # Propriedades externas validadas
    │   │   │   ├── modularity/                       # @ArchitecturalModule (metadados de fronteira)
    │   │   │   ├── observability/                    # CorrelationIdFilter (MDC / ADR-014)
    │   │   │   └── web/                              # GlobalExceptionHandler (RFC 9457) e SystemInfoController
    │   │   └── modules/                              # Fronteiras dos módulos arquiteturais (C4-003)
    │   │       ├── identityaccess/package-info.java
    │   │       ├── userprofiles/package-info.java
    │   │       ├── contentmanagement/package-info.java
    │   │       ├── rulesengine/package-info.java
    │   │       ├── charactermanagement/package-info.java
    │   │       ├── campaignstables/package-info.java
    │   │       ├── vttscenemanagement/package-info.java
    │   │       ├── realtimecommunication/package-info.java
    │   │       ├── communitymessaging/package-info.java
    │   │       └── filestorage/package-info.java
    │   └── resources/
    │       ├── application.yml                       # Configuração base segura (PostgreSQL + Flyway ativos)
    │       ├── application-dev.yml                   # Desenvolvimento com PostgreSQL obrigatório
    │       ├── application-local.yml                 # Inicialização local sem banco (Fase 0)
    │       ├── application-prod.yml                  # Hardening de produção
    │       └── db/migration/README.md                # Convenção modular de migrations Flyway (ADR-004)
    └── test/
        ├── java/com/evolutionvtt/
        │   ├── EvolutionVttApplicationTest.java
        │   ├── architecture/ModularArchitectureTest.java
        │   └── platform/
        │       ├── config/ProfilesConfigurationTest.java
        │       └── web/
        │           ├── GlobalExceptionHandlerTest.java
        │           └── HealthAndSystemInfoIntegrationTest.java
        └── resources/application-test.yml
```

### Módulos arquiteturais e propriedade de dados

| Módulo (C4-003) | Pacote Java | Schema PostgreSQL | Dependências permitidas (via `contracts`) |
|---|---|---|---|
| `identity-access` | `com.evolutionvtt.modules.identityaccess` | `identity_access` | *(nenhuma)* |
| `user-profiles` | `com.evolutionvtt.modules.userprofiles` | *(a fixar)* | `identity-access`, `file-storage` |
| `content-management` | `com.evolutionvtt.modules.contentmanagement` | *(a fixar)* | `identity-access`, `file-storage`, `rules-engine` |
| `rules-engine` | `com.evolutionvtt.modules.rulesengine` | `rules_engine` | *(nenhuma)* |
| `character-management` | `com.evolutionvtt.modules.charactermanagement` | `character_management` | `content-management`, `rules-engine`, `file-storage` |
| `campaigns-tables` | `com.evolutionvtt.modules.campaignstables` | `campaigns_tables` | `identity-access`, `character-management`, `content-management` |
| `vtt-scene-management` | `com.evolutionvtt.modules.vttscenemanagement` | *(a fixar)* | `campaigns-tables`, `character-management`, `file-storage` |
| `real-time-communication` | `com.evolutionvtt.modules.realtimecommunication` | *(transporte; sem domínio)* | `identity-access`, `campaigns-tables` |
| `community-messaging` | `com.evolutionvtt.modules.communitymessaging` | *(a fixar)* | `identity-access`, `campaigns-tables`, `file-storage` |
| `file-storage` | `com.evolutionvtt.modules.filestorage` | `file_storage` | *(nenhuma)* |
| `commerce` *(futuro)* | *(não criado na Fase 0)* | *(futuro)* | `identity-access`, `content-management`, `file-storage` |

### Organização interna pragmática (ADR-003 / C4-004)

Ao implementar cada módulo nas fases seguintes, utilize os pacotes internos apenas conforme a necessidade real (sem diretórios vazios ou classes artificiais):
- `domain` — entidades, agregados, objetos de valor, políticas, invariantes e eventos de domínio (**sem dependência** de Spring, JPA/Hibernate, Servlet ou WebSocket).
- `application` — casos de uso, comandos, consultas e orquestração.
- `contracts` — interfaces públicas, DTOs de contrato e eventos de integração (único ponto de entrada permitido a outros módulos).
- `infrastructure` — persistência JPA, adaptadores externos, controllers HTTP e integrações técnicas.

Todas essas fronteiras já são verificadas automaticamente por [`ModularArchitectureTest`](src/test/java/com/evolutionvtt/architecture/ModularArchitectureTest.java) com ArchUnit.

---

## 5. Limitações Atuais (Escopo da Fase 0)

- **Sem infraestrutura PostgreSQL local ainda:** não há `docker-compose.yml` configurado nesta tarefa; por isso o profile `local` e o profile `test` isolam DataSource/JPA/Flyway sem recorrer a banco em memória (H2).
- **Sem funcionalidades de negócio, segurança/autenticação ou WebSocket/STOMP:** serão introduzidos nas respectivas fases do roadmap (Spring Security/OIDC na Fase 1; WebSocket/STOMP na Fase 5).
- **Sem *migrations* SQL iniciais:** nenhuma tabela foi criada prematuramente (`db/migration/` contém apenas as convenções em `README.md`).
- **Sem OpenAPI/Swagger UI ainda:** será integrado quando os primeiros contratos de API da Fase 1 forem definidos.

---

## 6. Próximos Passos

1. Configurar ambiente local reproduzível com **Docker Compose + PostgreSQL** e validar inicialização integrada no profile `dev` + execução real do Flyway.
2. Configurar pipeline inicial de **CI com GitHub Actions** executando `./mvnw test` (incluindo ArchUnit) e validações de qualidade.
3. Inicializar o projeto **frontend** (React + Vite + TypeScript) preservando o protótipo e *assets* atuais conforme tarefa dedicada da Fase 0.
4. Iniciar a **Fase 1** (`identity-access` e `user-profiles`, Spring Security/OAuth 2.1/OIDC e documentação OpenAPI).
