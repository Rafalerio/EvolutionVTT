/**
 * Módulo arquitetural <strong>VTT &amp; Scene Management</strong> ({@code vtt-scene-management}).
 *
 * <p><strong>Responsabilidades (C4-003 / ADR-008):</strong> cenas, configuração e calibração de grade,
 * integração de mapas, tokens e controle de tokens, objetos de cena, desenhos/medidas, visibilidade
 * filtrada no servidor, estado autoritativo em world space e snapshots.</p>
 *
 * <p><strong>Dependências permitidas via contratos (C4-003):</strong> {@code campaigns-tables},
 * {@code character-management}, {@code file-storage}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 5.</p>
 */
@ArchitecturalModule(
        id = "vtt-scene-management",
        allowedDependencies = {"campaigns-tables", "character-management", "file-storage"}
)
package com.evolutionvtt.modules.vttscenemanagement;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
