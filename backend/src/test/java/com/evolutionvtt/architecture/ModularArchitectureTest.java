package com.evolutionvtt.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static org.assertj.core.api.Assertions.assertThat;

import com.evolutionvtt.platform.modularity.ArchitecturalModule;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Testes arquiteturais do monólito modular (ADR-003, ADR-004, ADR-007, ADR-015 e C4-003).
 *
 * <p>Valida imediatamente os metadados de fronteira dos módulos declarados e ativa guardrails
 * contínuos sobre isolamento interno, independência de domínio e ausência de ciclos.</p>
 */
class ModularArchitectureTest {

    private static final String ROOT_PACKAGE = "com.evolutionvtt";
    private static final String MODULES_PACKAGE = "com.evolutionvtt.modules";

    private static final Map<String, ModuleExpectation> APPROVED_MODULES = buildApprovedModules();

    private static JavaClasses productionClasses;

    @BeforeAll
    static void importProductionClasses() {
        productionClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(ROOT_PACKAGE);
    }

    @Test
    void allApprovedActiveModulesDeclareAccurateMetadataAndSchemas() {
        Map<String, ArchitecturalModule> discovered = discoverDeclaredModules();

        assertThat(discovered.keySet())
                .as("Os 10 módulos ativos aprovados no C4-003 devem estar declarados (commerce permanece futuro)")
                .containsExactlyInAnyOrderElementsOf(APPROVED_MODULES.keySet());

        APPROVED_MODULES.forEach((moduleId, expectation) -> {
            ArchitecturalModule metadata = discovered.get(moduleId);
            assertThat(metadata.allowedDependencies())
                    .as("Dependências permitidas do módulo '%s' devem refletir o C4-003", moduleId)
                    .containsExactlyInAnyOrderElementsOf(expectation.allowedDependencies());
            assertThat(metadata.schema())
                    .as("Schema PostgreSQL do módulo '%s' deve refletir a ADR-004/Blueprint", moduleId)
                    .isEqualTo(expectation.approvedSchema());
        });
    }

    @Test
    void declaredModuleDependencyGraphIsAcyclicAndReferencesOnlyKnownModules() {
        Map<String, ArchitecturalModule> discovered = discoverDeclaredModules();

        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> adjacency = new HashMap<>();
        discovered.keySet().forEach(id -> inDegree.put(id, 0));

        discovered.forEach((moduleId, metadata) -> {
            for (String dependency : metadata.allowedDependencies()) {
                assertThat(discovered)
                        .as("Módulo '%s' declara dependência desconhecida '%s'", moduleId, dependency)
                        .containsKey(dependency);
                assertThat(dependency)
                        .as("Módulo '%s' não pode depender de si mesmo", moduleId)
                        .isNotEqualTo(moduleId);
                adjacency.computeIfAbsent(dependency, ignored -> new java.util.ArrayList<>()).add(moduleId);
                inDegree.merge(moduleId, 1, Integer::sum);
            }
        });

        Deque<String> queue = new ArrayDeque<>();
        inDegree.forEach((id, degree) -> {
            if (degree == 0) {
                queue.add(id);
            }
        });

        int visited = 0;
        while (!queue.isEmpty()) {
            String current = queue.poll();
            visited++;
            for (String dependent : adjacency.getOrDefault(current, List.of())) {
                int remaining = inDegree.merge(dependent, -1, Integer::sum);
                if (remaining == 0) {
                    queue.add(dependent);
                }
            }
        }

        assertThat(visited)
                .as("O grafo de dependências dos módulos deve ser acíclico (DAG)")
                .isEqualTo(discovered.size());
    }

    @Test
    void modulesMustBeFreeOfCyclicDependencies() {
        slices()
                .matching(MODULES_PACKAGE + ".(*)..")
                .should()
                .beFreeOfCycles()
                .allowEmptyShould(true)
                .check(productionClasses);
    }

    @Test
    void platformPackageMustNotDependOnBusinessModules() {
        noClasses()
                .that().resideInAPackage("com.evolutionvtt.platform..")
                .should().dependOnClassesThat().resideInAPackage(MODULES_PACKAGE + "..")
                .check(productionClasses);
    }

    @Test
    void forbiddenGenericCatchAllPackagesMustNotExist() {
        noClasses()
                .should().resideInAnyPackage(
                        ROOT_PACKAGE + "..common..",
                        ROOT_PACKAGE + "..utils..",
                        ROOT_PACKAGE + "..shared.."
                )
                .check(productionClasses);
    }

    @Test
    void domainLayerMustRemainIndependentOfFrameworksAndPersistence() {
        noClasses()
                .that().resideInAPackage(MODULES_PACKAGE + "..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "org.hibernate..",
                        "jakarta.servlet..",
                        "jakarta.websocket.."
                )
                .allowEmptyShould(true)
                .check(productionClasses);
    }

    @Test
    void businessModulesMustNotDependOnRealTimeCommunicationModule() {
        noClasses()
                .that().resideInAPackage(MODULES_PACKAGE + "..")
                .and().resideOutsideOfPackage(MODULES_PACKAGE + ".realtimecommunication..")
                .should().dependOnClassesThat().resideInAPackage(MODULES_PACKAGE + ".realtimecommunication..")
                .allowEmptyShould(true)
                .check(productionClasses);
    }

    @Test
    void modulesMustOnlyDependOnPublicContractsOfAllowedDependencies() {
        APPROVED_MODULES.forEach((moduleId, expectation) -> {
            String modulePackage = MODULES_PACKAGE + "." + expectation.packageSegment() + "..";

            // Proíbe acesso aos pacotes internos (domain, application, infrastructure) de qualquer outro módulo
            APPROVED_MODULES.forEach((targetId, target) -> {
                if (moduleId.equals(targetId)) {
                    return;
                }
                String targetBase = MODULES_PACKAGE + "." + target.packageSegment();

                if (!expectation.allowedDependencies().contains(targetId)) {
                    noClasses()
                            .that().resideInAPackage(modulePackage)
                            .should().dependOnClassesThat().resideInAPackage(targetBase + "..")
                            .allowEmptyShould(true)
                            .check(productionClasses);
                } else {
                    noClasses()
                            .that().resideInAPackage(modulePackage)
                            .should().dependOnClassesThat().resideInAnyPackage(
                                    targetBase + ".domain..",
                                    targetBase + ".application..",
                                    targetBase + ".infrastructure.."
                            )
                            .allowEmptyShould(true)
                            .check(productionClasses);
                }
            });
        });
    }

    private Map<String, ArchitecturalModule> discoverDeclaredModules() {
        Map<String, ArchitecturalModule> discovered = new LinkedHashMap<>();
        productionClasses.stream()
                .filter(javaClass -> javaClass.getPackageName().startsWith(MODULES_PACKAGE + "."))
                .filter(javaClass -> javaClass.getSimpleName().equals("package-info"))
                .filter(javaClass -> javaClass.isAnnotatedWith(ArchitecturalModule.class))
                .forEach(javaClass -> {
                    ArchitecturalModule annotation = javaClass.getAnnotationOfType(ArchitecturalModule.class);
                    discovered.put(annotation.id(), annotation);
                });
        return discovered;
    }

    private static Map<String, ModuleExpectation> buildApprovedModules() {
        Map<String, ModuleExpectation> map = new LinkedHashMap<>();
        map.put("identity-access", new ModuleExpectation("identityaccess", Set.of(), "identity_access"));
        map.put("user-profiles", new ModuleExpectation("userprofiles", Set.of("identity-access", "file-storage"), ""));
        map.put("content-management", new ModuleExpectation("contentmanagement", Set.of("identity-access", "file-storage", "rules-engine"), ""));
        map.put("rules-engine", new ModuleExpectation("rulesengine", Set.of(), "rules_engine"));
        map.put("character-management", new ModuleExpectation("charactermanagement", Set.of("content-management", "rules-engine", "file-storage"), "character_management"));
        map.put("campaigns-tables", new ModuleExpectation("campaignstables", Set.of("identity-access", "character-management", "content-management"), "campaigns_tables"));
        map.put("vtt-scene-management", new ModuleExpectation("vttscenemanagement", Set.of("campaigns-tables", "character-management", "file-storage"), ""));
        map.put("real-time-communication", new ModuleExpectation("realtimecommunication", Set.of("identity-access", "campaigns-tables"), ""));
        map.put("community-messaging", new ModuleExpectation("communitymessaging", Set.of("identity-access", "campaigns-tables", "file-storage"), ""));
        map.put("file-storage", new ModuleExpectation("filestorage", Set.of(), "file_storage"));
        return Map.copyOf(map);
    }

    private record ModuleExpectation(
            String packageSegment,
            Set<String> allowedDependencies,
            String approvedSchema
    ) {
    }
}
