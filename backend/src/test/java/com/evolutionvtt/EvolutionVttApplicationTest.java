package com.evolutionvtt;

import static org.assertj.core.api.Assertions.assertThat;

import com.evolutionvtt.platform.config.ApplicationInfoProperties;
import com.evolutionvtt.platform.observability.CorrelationIdFilter;
import com.evolutionvtt.platform.web.GlobalExceptionHandler;
import com.evolutionvtt.platform.web.SystemInfoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EvolutionVttApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationInfoProperties applicationInfoProperties;

    @Test
    void contextLoadsWithFoundationBeans() {
        assertThat(applicationContext.getBean(SystemInfoController.class)).isNotNull();
        assertThat(applicationContext.getBean(GlobalExceptionHandler.class)).isNotNull();
        assertThat(applicationContext.getBean(CorrelationIdFilter.class)).isNotNull();
    }

    @Test
    void bindsValidatedApplicationInfoPropertiesForTestProfile() {
        assertThat(applicationInfoProperties.name()).isEqualTo("Evolution VTT Backend");
        assertThat(applicationInfoProperties.version()).isNotBlank();
        assertThat(applicationInfoProperties.environment()).isEqualTo("test");
    }
}
