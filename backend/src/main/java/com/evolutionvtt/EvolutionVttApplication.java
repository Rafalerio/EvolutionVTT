package com.evolutionvtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Ponto de entrada do monólito modular backend do Evolution VTT (ADR-001, ADR-003).
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class EvolutionVttApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvolutionVttApplication.class, args);
    }
}
