/**
 * Módulo arquitetural <strong>File Storage</strong> ({@code file-storage}).
 *
 * <p><strong>Responsabilidades (C4-003 / C4-005.02 / ADR-010):</strong> registro e metadados de arquivos,
 * validação real de conteúdo, quarentena, seleção de provedor, adaptadores (Cloudflare R2 para imagens,
 * OCI Object Storage para demais arquivos, Local em dev/testes), URLs assinadas, referências e limpeza
 * de órfãos.</p>
 *
 * <p><strong>Propriedade de dados (ADR-004):</strong> schema PostgreSQL {@code file_storage}.
 * Não possui dependências de domínio de outros módulos.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 2.</p>
 */
@ArchitecturalModule(
        id = "file-storage",
        allowedDependencies = {},
        schema = "file_storage"
)
package com.evolutionvtt.modules.filestorage;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
