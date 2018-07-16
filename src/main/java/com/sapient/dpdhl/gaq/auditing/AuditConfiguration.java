package com.sapient.dpdhl.gaq.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

class AuditConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuditConfiguration.class);

    @Bean
    AuditService auditService() {
        log.debug("Creating AuditService implementation");
        return new AuditServiceImpl();
    }

}
