/**
 * Módulo arquitetural <strong>Identity &amp; Access</strong> ({@code identity-access}).
 *
 * <p><strong>Responsabilidades (C4-003 / C4-005.01):</strong> contas ({@code AccountId}),
 * credenciais (Argon2id), autenticação OAuth 2.1/OIDC, sessões, dispositivos, papéis globais
 * e segurança/revogação.</p>
 *
 * <p><strong>Propriedade de dados (ADR-004):</strong> schema PostgreSQL {@code identity_access}.
 * Não possui dependências de domínio de outros módulos.</p>
 *
 * <p><strong>Camadas internas previstas (ADR-003 / C4-004):</strong> {@code application},
 * {@code domain}, {@code contracts} e {@code infrastructure}, criadas sob demanda na Fase 1.</p>
 */
@ArchitecturalModule(
        id = "identity-access",
        allowedDependencies = {},
        schema = "identity_access"
)
package com.evolutionvtt.modules.identityaccess;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
