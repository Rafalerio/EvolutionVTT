/**
 * Módulo arquitetural <strong>Character Management</strong> ({@code character-management}).
 *
 * <p><strong>Responsabilidades (C4-003 / C4-005.04):</strong> fichas originais, modelo híbrido
 * (núcleo tipado + extensões JSONB), definições versionadas, progressão, versionamento lógico,
 * instâncias contextuais ({@code frozen}/{@code local}), histórico imutável, cálculos e ativos.</p>
 *
 * <p><strong>Propriedade de dados (ADR-004):</strong> schema PostgreSQL {@code character_management}.
 * Dependências permitidas via contratos (C4-003): {@code content-management}, {@code rules-engine},
 * {@code file-storage}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 3.</p>
 */
@ArchitecturalModule(
        id = "character-management",
        allowedDependencies = {"content-management", "rules-engine", "file-storage"},
        schema = "character_management"
)
package com.evolutionvtt.modules.charactermanagement;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
