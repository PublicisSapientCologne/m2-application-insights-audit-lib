package com.sapient.azure.auditing;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables audit logging.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AuditConfiguration.class)
@Configuration
@Documented
public @interface EnableAuditing {
}
