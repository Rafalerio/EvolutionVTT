# ADR-014 — Observabilidade

- **Status:** Aprovada

## Decisão
Adotar stack de observabilidade baseada em:
- SLF4J / Logback com logs estruturados.
- Micrometer.
- Prometheus.
- Grafana.
- OpenTelemetry.
- Jaeger.
- Node Exporter.
- Alertmanager.

## Diretrizes
- Correlação entre requisições e operações.
- Monitoramento de REST e WebSocket.
- Métricas de aplicação e infraestrutura.
- Alertas para condições operacionais relevantes.
- Proteção de dados sensíveis: logs, traces e métricas não devem expor credenciais, tokens ou conteúdo privado desnecessário.
