package com.sapient.dpdhl.gaq.auditing.impl.applicationinsights;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ApplicationInsightsConfiguration.class)
public @interface EnableApplicationInsights {

}
