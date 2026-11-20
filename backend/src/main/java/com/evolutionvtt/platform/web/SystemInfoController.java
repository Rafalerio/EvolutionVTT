package com.evolutionvtt.platform.web;

import com.evolutionvtt.platform.config.ApplicationInfoProperties;
import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint técnico de fundação expondo informações básicas não sensíveis da aplicação.
 * Não contém lógica de negócio.
 */
@RestController
@RequestMapping("/api/v1/system")
public class SystemInfoController {

    private final ApplicationInfoProperties applicationInfo;

    public SystemInfoController(ApplicationInfoProperties applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    @GetMapping("/info")
    public SystemInfoResponse getSystemInfo() {
        return new SystemInfoResponse(
                applicationInfo.name(),
                applicationInfo.version(),
                applicationInfo.environment(),
                "UP",
                Instant.now()
        );
    }
}
