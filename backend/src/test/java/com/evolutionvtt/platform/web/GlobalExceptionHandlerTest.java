package com.evolutionvtt.platform.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.evolutionvtt.platform.observability.CorrelationIdFilter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(GlobalExceptionHandlerTest.TestSupportConfig.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsStructuredProblemDetailOnValidationFailure() throws Exception {
        mockMvc.perform(post("/_test/errors/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(CorrelationIdFilter.CORRELATION_ID_HEADER, "corr-val-001")
                        .content("""
                                {"name": ""}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("urn:evolutionvtt:error:validation-error"))
                .andExpect(jsonPath("$.title").value("Validation Failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.correlationId").value("corr-val-001"))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value("must not be blank"));
    }

    @Test
    void returnsBadRequestOnMalformedJsonBody() throws Exception {
        mockMvc.perform(post("/_test/errors/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid-json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("urn:evolutionvtt:error:malformed-request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("The request payload could not be parsed."));
    }

    @Test
    void returnsNotFoundOnUnknownResource() throws Exception {
        mockMvc.perform(get("/api/v1/does-not-exist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("urn:evolutionvtt:error:resource-not-found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void returnsMethodNotAllowedForUnsupportedHttpVerb() throws Exception {
        mockMvc.perform(post("/api/v1/system/info"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.type").value("urn:evolutionvtt:error:method-not-allowed"))
                .andExpect(jsonPath("$.status").value(405));
    }

    @Test
    void hidesInternalExceptionDetailsOnUnexpectedServerError() throws Exception {
        mockMvc.perform(get("/_test/errors/boom")
                        .header(CorrelationIdFilter.CORRELATION_ID_HEADER, "corr-err-500"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.type").value("urn:evolutionvtt:error:internal-error"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.detail").value("An unexpected internal error occurred."))
                .andExpect(jsonPath("$.correlationId").value("corr-err-500"))
                .andExpect(content().string(not(containsString("internal-sensitive-detail"))));
    }

    @TestConfiguration
    static class TestSupportConfig {

        @RestController
        @RequestMapping("/_test/errors")
        static class TestErrorController {

            @PostMapping("/validate")
            String validate(@Valid @RequestBody SamplePayload payload) {
                return payload.name();
            }

            @GetMapping("/boom")
            String boom() {
                throw new IllegalStateException("internal-sensitive-detail");
            }
        }
    }

    record SamplePayload(@NotBlank String name) {
    }
}
