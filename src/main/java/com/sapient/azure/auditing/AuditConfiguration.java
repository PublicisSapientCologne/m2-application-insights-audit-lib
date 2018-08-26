package com.sapient.azure.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(prefix = "applicationinsights", name = "telemetryKey")
class AuditConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuditConfiguration.class);

    @Bean
    AuditService auditService() {
        log.debug("Creating AuditService implementation");
        return new AuditServiceImpl();
    }

}
