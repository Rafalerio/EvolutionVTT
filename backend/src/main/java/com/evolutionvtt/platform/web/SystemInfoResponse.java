package com.evolutionvtt.platform.web;

import java.time.Instant;

/**
 * Payload enxuto e não sensível de informações operacionais básicas da aplicação.
 */
public record SystemInfoResponse(
        String application,
        String version,
        String environment,
        String status,
        Instant timestamp
) {
}
