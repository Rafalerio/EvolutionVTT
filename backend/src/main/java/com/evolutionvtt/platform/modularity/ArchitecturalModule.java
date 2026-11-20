package com.evolutionvtt.platform.modularity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declara metadados explícitos de fronteira para um módulo arquitetural do monólito modular,
 * alinhado à ADR-003, ADR-004 e C4-003.
 *
 * <p>Os metadados declarados aqui são verificados automaticamente pela suíte de testes
 * arquiteturais (ArchUnit) para garantir conformidade com o grafo de dependências aprovado.</p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface ArchitecturalModule {

    /**
     * Identificador arquitetural canônico do módulo (ex.: {@code "identity-access"}).
     */
    String id();

    /**
     * Lista de identificadores canônicos de módulos dos quais este módulo pode depender
     * exclusivamente via contratos públicos ({@code contracts}), conforme o C4-003.
     */
    String[] allowedDependencies() default {};

    /**
     * Schema PostgreSQL proprietário aprovado na ADR-004/Blueprint.
     * Valor vazio indica módulo sem dados de domínio (ex.: RTC) ou schema pendente de fixação.
     */
    String schema() default "";
}
