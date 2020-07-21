package com.rbkmoney.scrooge.repository;

import com.rbkmoney.scrooge.ScroogeApplication;
import com.rbkmoney.scrooge.entity.OperationLogEntity;
import com.rbkmoney.scrooge.entity.OperationLogPk;
import com.rbkmoney.scrooge.entity.enums.OperationType;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = ScroogeApplication.class,
        webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
@ContextConfiguration(initializers = OperationLogRepositoryTest.Initializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OperationLogRepositoryTest {

    @Autowired
    private OperationLogRepository operationLogRepository;

    @ClassRule
    @SuppressWarnings("rawtypes")
    public static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:9.6")
            .withStartupTimeout(Duration.ofMinutes(5));

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "spring.flyway.url=" + postgres.getJdbcUrl(),
                    "spring.flyway.user=" + postgres.getUsername(),
                    "spring.flyway.password=" + postgres.getPassword())
                    .and(configurableApplicationContext.getEnvironment().getActiveProfiles())
                    .applyTo(configurableApplicationContext);
        }
    }

    @Test
    public void shouldNotInsertDuplicates() {
        // Given
        List<OperationLogEntity> entities = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OperationLogEntity entity = OperationLogEntity.builder()
                    .pk(OperationLogPk.builder()
                            .sequenceId(1L)
                            .planOperationsCount(2L)
                            .planId("3")
                            .batchId(4L)
                            .build())
                    .validationError(null)
                    .traceId("trace_id")
                    .spanId("span_id")
                    .parentId("parent_id")
                    .operationType(OperationType.commit)
                    .description("description")
                    .currencySymbolicCode("RUB")
                    .creationTime(LocalDateTime.now())
                    .batchHash(9999L)
                    .amountWithSign(-100L)
                    .accountId("account_id")
                    .build();

            entities.add(entity);
        }

        // When
        operationLogRepository.saveAll(entities);

        // Then
        assertThat(operationLogRepository.count()).isEqualTo(1L);
    }
}
