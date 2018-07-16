package com.sapient.dpdhl.gaq.auditing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AuditConfigurationTest {

    @Test
    public void applicationContextBuildWithoutEnableAuditingAnnotationPresent() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationImplDisabled.class)) {
            Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(AuditService.class));
        }
    }

    @Test
    public void applicationContextBuildWithEnableAuditingAnnotationPresent() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationImplEnabled.class)) {
            Assertions.assertNotNull(applicationContext.getBean(AuditService.class));
        }
    }

    @EnableAuditing
    static class ConfigurationImplEnabled {
    }

    static class ConfigurationImplDisabled {
    }

}
