/**
 * Módulo arquitetural <strong>Campaigns &amp; Tables</strong> ({@code campaigns-tables}).
 *
 * <p><strong>Responsabilidades (C4-003 / C4-005.05):</strong> mesas como salas de interação,
 * participantes, convites, permissões contextuais, diretório hierárquico de recursos
 * (personagens, NPCs e mapas), vínculos/cópias, associações de cenas e cena ativa compartilhada.</p>
 *
 * <p><strong>Propriedade de dados (ADR-004):</strong> schema PostgreSQL {@code campaigns_tables}.
 * Dependências permitidas via contratos (C4-003): {@code identity-access},
 * {@code character-management}, {@code content-management}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 4.</p>
 */
@ArchitecturalModule(
        id = "campaigns-tables",
        allowedDependencies = {"identity-access", "character-management", "content-management"},
        schema = "campaigns_tables"
)
package com.evolutionvtt.modules.campaignstables;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
