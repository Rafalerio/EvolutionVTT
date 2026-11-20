/**
 * Módulo arquitetural <strong>Community &amp; Messaging</strong> ({@code community-messaging}).
 *
 * <p><strong>Responsabilidades (C4-003):</strong> mensagens diretas, mensagens de mesa, fóruns,
 * avisos, conteúdo comunitário, moderação, anexos e histórico de mensagens.</p>
 *
 * <p><strong>Dependências permitidas via contratos (C4-003):</strong> {@code identity-access},
 * {@code campaigns-tables}, {@code file-storage}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 6.</p>
 */
@ArchitecturalModule(
        id = "community-messaging",
        allowedDependencies = {"identity-access", "campaigns-tables", "file-storage"}
)
package com.evolutionvtt.modules.communitymessaging;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
