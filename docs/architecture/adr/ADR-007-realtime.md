# ADR-007 — Comunicação em Tempo Real

- **Status:** Aprovada

## Decisão
Utilizar WebSocket + STOMP para comunicação em tempo real, com o backend como autoridade do estado.

## Diretrizes
- Sem broker externo inicialmente.
- Autorização por mesa/canal, validada no servidor.
- Snapshots permitem recuperação e ressincronização.
- Eventos importantes e duráveis são persistidos pelo módulo proprietário do dado.
- Eventos transitórios — por exemplo cursor, seleção, preview e arraste — podem não ser persistidos.
- O módulo RTC é responsável por conexão, canais, distribuição, sincronização e recuperação; não é proprietário do domínio de mensagens ou do estado da VTT.

## Consequências
O cliente não é fonte de verdade. Comandos recebidos devem ser autorizados e validados no servidor antes de alterar o estado oficial.
