package com.evolutionvtt.platform.web;

import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.evolutionvtt.platform.observability.CorrelationIdFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HealthAndSystemInfoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void actuatorHealthRespondsUpWithoutSensitiveDetails() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(header().exists(CorrelationIdFilter.CORRELATION_ID_HEADER))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components").doesNotExist());
    }

    @Test
    void actuatorLivenessAndReadinessProbesRespondUp() throws Exception {
        mockMvc.perform(get("/actuator/health/liveness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void actuatorInfoExposesOnlyBasicNonSensitiveMetadata() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.app.name").value("evolutionvtt-backend"))
                .andExpect(jsonPath("$.app.version").value(notNullValue()));
    }

    @Test
    void administrativeActuatorEndpointsRemainDisabled() throws Exception {
        mockMvc.perform(get("/actuator/env"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/actuator/beans"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/actuator/configprops"))
                .andExpect(status().isNotFound());
    }

    @Test
    void systemInfoEndpointReturnsExpectedMetadataAndPropagatesCorrelationId() throws Exception {
        mockMvc.perform(get("/api/v1/system/info")
                        .header(CorrelationIdFilter.CORRELATION_ID_HEADER, "req-test-12345"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationIdFilter.CORRELATION_ID_HEADER, "req-test-12345"))
                .andExpect(jsonPath("$.application").value("Evolution VTT Backend"))
                .andExpect(jsonPath("$.version").value(notNullValue()))
                .andExpect(jsonPath("$.environment").value("test"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").value(notNullValue()));
    }

    @Test
    void invalidCorrelationIdHeaderIsReplacedWithSafeUuid() throws Exception {
        mockMvc.perform(get("/api/v1/system/info")
                        .header(CorrelationIdFilter.CORRELATION_ID_HEADER, "invalid header with spaces"))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        CorrelationIdFilter.CORRELATION_ID_HEADER,
                        matchesRegex("^[0-9a-fA-F-]{36}$")
                ));
    }
}
