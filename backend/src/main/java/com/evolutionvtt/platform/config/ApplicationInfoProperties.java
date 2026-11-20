package com.evolutionvtt.platform.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Propriedades externas não sensíveis de identificação da aplicação.
 */
@Validated
@ConfigurationProperties(prefix = "evolutionvtt.application")
public record ApplicationInfoProperties(
        @NotBlank String name,
        @NotBlank String version,
        @NotBlank String environment
) {
}
