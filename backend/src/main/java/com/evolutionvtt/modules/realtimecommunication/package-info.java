/**
 * Módulo arquitetural <strong>Real-time Communication</strong> ({@code real-time-communication}).
 *
 * <p><strong>Responsabilidades (C4-003 / ADR-007):</strong> conexões WebSocket/STOMP, autenticação
 * de conexão, canais, autorização de inscrições, distribuição de eventos, sincronização,
 * comunicação transitória e recuperação por snapshot.</p>
 *
 * <p><strong>Propriedade de dados:</strong> camada de transporte sem propriedade de dados de domínio.
 * Módulos de domínio não podem depender deste módulo. Dependências permitidas via contratos (C4-003):
 * {@code identity-access}, {@code campaigns-tables}.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 5.</p>
 */
@ArchitecturalModule(
        id = "real-time-communication",
        allowedDependencies = {"identity-access", "campaigns-tables"}
)
package com.evolutionvtt.modules.realtimecommunication;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
