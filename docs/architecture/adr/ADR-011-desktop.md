# ADR-011 — Cliente Desktop

- **Status:** Aprovada

## Decisão
Utilizar Tauri para empacotar o frontend React + Vite + TypeScript.

## Diretrizes
- Rust fica restrito às integrações necessárias com o sistema operacional.
- A interface e as regras de negócio permanecem no frontend compartilhado e no backend.
- O cliente desktop utiliza a mesma API REST e comunicação STOMP dos demais clientes.
- Offline é limitado inicialmente à abertura do aplicativo e ao acesso a dados previamente armazenados.

## Consequências
A versão desktop não deve criar um backend paralelo nem duplicar regras de negócio.
