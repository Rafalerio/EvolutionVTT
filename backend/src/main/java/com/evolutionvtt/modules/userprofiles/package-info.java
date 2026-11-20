/**
 * Módulo arquitetural <strong>User &amp; Profiles</strong> ({@code user-profiles}).
 *
 * <p><strong>Responsabilidades (C4-003):</strong> perfil público, preferências, identidade pública,
 * privacidade do perfil, avatar e integração com identidade (sem replicar credenciais).</p>
 *
 * <p><strong>Dependências permitidas via contratos (C4-003):</strong> {@code identity-access},
 * {@code file-storage}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 1.</p>
 */
@ArchitecturalModule(
        id = "user-profiles",
        allowedDependencies = {"identity-access", "file-storage"}
)
package com.evolutionvtt.modules.userprofiles;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
