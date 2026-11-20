# Terraform — Infraestrutura como Código (OCI)

Este diretório armazenará as definições declarativas de infraestrutura (**Terraform**) do Evolution VTT na **Oracle Cloud Infrastructure (OCI)** e integrações correlatas, conforme a **ADR-013** e a Seção 15.4 do [Blueprint Arquitetural](../../docs/architecture/blueprint/EvolutionVTT_Blueprint_Arquitetural_v1.0.0.md).

## Estado atual

Diretório reservado na **Fase 0 — Fundação do Repositório**. Nenhum recurso de nuvem foi provisionado nesta tarefa.

## Escopo previsto

- Rede, instâncias OCI Compute, registro de imagens (OCI Container Registry) e buckets OCI Object Storage.
- Separação clara entre ambientes (ex.: homologação e produção) quando provisionados.
- Arquivos de exemplo (`*.tfvars.example`) com placeholders para documentação das variáveis exigidas.

## Regras de segurança

- **Nunca versione** arquivos de estado (`*.tfstate`, `*.tfstate.*`), credenciais OCI, chaves privadas (`*.pem`, `*.key`) ou arquivos `*.tfvars` contendo valores reais.
- O estado remoto do Terraform e eventuais segredos de provisionamento devem ser configurados fora do repositório.
- Evite alterações manuais diretas no console da nuvem; qualquer ajuste emergencial deve ser reconciliado no código Terraform.
