package com.evolutionvtt.platform.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Valida invariantes críticos dos arquivos de configuração por profile (ADR-004, ADR-014 e Seções 10-11).
 */
class ProfilesConfigurationTest {

    private final YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();

    @Test
    void baseConfigurationEnforcesSafePersistenceActuatorAndLoggingDefaults() throws IOException {
        PropertySource<?> base = loadYaml("application.yml");

        assertThat(base.getProperty("spring.datasource.driver-class-name"))
                .isEqualTo("org.postgresql.Driver");
        assertThat(base.getProperty("spring.datasource.url")).isEqualTo("${DB_URL:}");
        assertThat(base.getProperty("spring.datasource.username")).isEqualTo("${DB_USERNAME:}");
        assertThat(base.getProperty("spring.datasource.password")).isEqualTo("${DB_PASSWORD:}");

        assertThat(base.getProperty("spring.jpa.open-in-view")).isEqualTo(false);
        assertThat(base.getProperty("spring.jpa.hibernate.ddl-auto")).isEqualTo("validate");
        assertThat(base.getProperty("spring.flyway.enabled")).isEqualTo(true);
        assertThat(base.getProperty("spring.flyway.baseline-on-migrate")).isEqualTo(false);
        assertThat(base.getProperty("spring.flyway.validate-on-migrate")).isEqualTo(true);

        assertThat(base.getProperty("management.endpoints.web.exposure.include")).isEqualTo("health,info");
        assertThat(base.getProperty("management.endpoint.health.show-details")).isEqualTo("never");
        assertThat(base.getProperty("logging.structured.format.console")).isEqualTo("${LOG_STRUCTURED_FORMAT:ecs}");

        assertThat(base.getProperty("spring.autoconfigure.exclude[0]")).isNull();
    }

    @Test
    void devAndProdProfilesDoNotDisablePersistenceOrEnableAutoDdl() throws IOException {
        PropertySource<?> dev = loadYaml("application-dev.yml");
        PropertySource<?> prod = loadYaml("application-prod.yml");

        assertThat(dev.getProperty("spring.flyway.enabled")).isNull();
        assertThat(dev.getProperty("spring.autoconfigure.exclude[0]")).isNull();
        assertThat(dev.getProperty("spring.jpa.hibernate.ddl-auto")).isNull();

        assertThat(prod.getProperty("spring.flyway.enabled")).isEqualTo(true);
        assertThat(prod.getProperty("spring.jpa.hibernate.ddl-auto")).isEqualTo("validate");
        assertThat(prod.getProperty("spring.autoconfigure.exclude[0]")).isNull();
        assertThat(prod.getProperty("management.endpoint.health.show-details")).isEqualTo("never");
        assertThat(prod.getProperty("logging.structured.format.console")).isEqualTo("ecs");
    }

    @Test
    void localAndTestProfilesExplicitlyIsolatePersistenceAutoConfiguration() throws IOException {
        for (String file : List.of("application-local.yml", "application-test.yml")) {
            PropertySource<?> profile = loadYaml(file);
            assertThat(profile.getProperty("spring.flyway.enabled")).isEqualTo(false);
            assertThat(profile.getProperty("spring.autoconfigure.exclude[0]"))
                    .isEqualTo("org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration");
            assertThat(profile.getProperty("spring.autoconfigure.exclude[4]"))
                    .isEqualTo("org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration");
        }
    }

    private PropertySource<?> loadYaml(String resourceName) throws IOException {
        List<PropertySource<?>> sources = yamlLoader.load(resourceName, new ClassPathResource(resourceName));
        assertThat(sources).hasSize(1);
        return sources.getFirst();
    }
}
