# ADR-001 — Backend: Java + Spring Boot

- **Status:** Aprovada
- **Decisão:** Utilizar Java com Spring Boot no backend.
- **Estilo arquitetural:** Monólito modular.
- **Interfaces:** API REST e WebSockets.
- **Documentação de API:** OpenAPI.
- **Testes:** JUnit.

## Contexto
O Evolution VTT precisa de um backend central que seja extensível, sustentável e responsável pela autoridade do estado do sistema e das mesas.

## Decisão
Adotar Java + Spring Boot em uma única aplicação organizada por módulos de domínio. A aplicação expõe REST para operações request/response e WebSockets para comunicação em tempo real. Os contratos REST devem ser documentados com OpenAPI.

## Consequências
- Um único deploy inicial, com fronteiras modulares internas.
- Não adotar microsserviços nesta etapa.
- Os módulos devem manter suas responsabilidades e contratos explícitos.
- Testes automatizados são parte da implementação.
- A decisão sobre ferramenta de build deve ser registrada separadamente caso ainda não esteja definida.

## Fora do escopo
Esta ADR não define versões específicas de Java/Spring Boot, ferramenta de build, hospedagem ou detalhes de cada endpoint.
