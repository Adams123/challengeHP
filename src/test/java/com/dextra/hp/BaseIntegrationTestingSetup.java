package com.dextra.hp;


import com.dextra.hp.config.FeignInterceptor;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.spring.api.DBRider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

import static com.dextra.hp.exception.ExceptionLocalization.DELETED_ENTITY_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration-test")
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringBootTest
public class BaseIntegrationTestingSetup {

    @MockBean
    protected FeignInterceptor feignInterceptor;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected MessageSource messageSource;

    private static PostgreSQLContainer postgres = new PostgreSQLContainer();

    @BeforeClass
    public static void setupContainer() {
        postgres.start();
    }

    @AfterClass
    public static void shutdown() {
        postgres.stop();
    }

    protected void assertThatEntityIsNotAllowedToBeAccessed(String entityId, Exception exception) {
        String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{entityId}, null, Locale.getDefault());
        assertThat(exception.getMessage()).isEqualTo(message);
    }


}
