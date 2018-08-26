package com.sapient.azure.auditing.applicationinsights;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables Azure's ApplicationInsights as an audit backend.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ApplicationInsightsConfiguration.class)
@Configuration
@Documented
public @interface EnableApplicationInsights {

}
