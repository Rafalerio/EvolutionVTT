# ADR-010 — Armazenamento de Arquivos

- **Status:** Aprovada

## Decisão de provedores
- Cloudflare R2 para todas as imagens em produção.
- OCI Object Storage para os demais arquivos.
- Armazenamento local em desenvolvimento e testes.
- Módulo File Storage abstrai provedores por interfaces e adaptadores.
- PostgreSQL armazena metadados e referências, não os binários.
- Upload/download por URLs assinadas quando apropriado.

## Segurança e ciclo de vida
- Validar conteúdo real, tamanho e tipo; não confiar apenas na extensão ou MIME declarado.
- Upload passa por quarentena/validação antes de ficar disponível.
- Exclusão lógica e exclusão física assíncrona.
- Deduplicação física é permitida, sem eliminar independência lógica dos recursos.
- Deve existir limpeza de arquivos órfãos e verificação de integridade.

## Observações
Compatibilidade operacional, custos e egress entre R2 e OCI devem ser validados durante a implementação.
