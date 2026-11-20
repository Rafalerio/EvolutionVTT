/**
 * Módulo arquitetural <strong>Content Management</strong> ({@code content-management}).
 *
 * <p><strong>Responsabilidades (C4-003 / ADR-006):</strong> catálogo, hierarquia de livros/capítulos,
 * metadados e etiquetas, relacionamentos, autoria, homebrew, revisão/moderação, publicação,
 * controle de acesso e versionamento imutável.</p>
 *
 * <p><strong>Dependências permitidas via contratos (C4-003):</strong> {@code identity-access},
 * {@code file-storage}, {@code rules-engine}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 2.</p>
 */
@ArchitecturalModule(
        id = "content-management",
        allowedDependencies = {"identity-access", "file-storage", "rules-engine"}
)
package com.evolutionvtt.modules.contentmanagement;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
