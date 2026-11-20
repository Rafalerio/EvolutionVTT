/**
 * Módulo arquitetural <strong>Rules Engine</strong> ({@code rules-engine}).
 *
 * <p><strong>Responsabilidades (C4-003 / C4-005.03 / ADR-005):</strong> modelo de definição de regras,
 * DSL declarativa, AST canônica, validação estrutural/semântica, compilação, execução determinística
 * com limites de segurança, aleatoriedade registrada e extensões confiáveis.</p>
 *
 * <p><strong>Propriedade de dados (ADR-004):</strong> schema PostgreSQL {@code rules_engine}.
 * Não possui dependências de domínio de outros módulos.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 3.</p>
 */
@ArchitecturalModule(
        id = "rules-engine",
        allowedDependencies = {},
        schema = "rules_engine"
)
package com.evolutionvtt.modules.rulesengine;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
