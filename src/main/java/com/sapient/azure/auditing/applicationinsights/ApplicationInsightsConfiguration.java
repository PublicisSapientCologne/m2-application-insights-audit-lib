package com.sapient.azure.auditing.applicationinsights;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.logback.ApplicationInsightsAppender;
import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.function.Supplier;

@ConditionalOnProperty(prefix = "applicationinsights", name = "telemetryKey")
@Configuration
class ApplicationInsightsConfiguration {

    private Supplier<TelemetryConfiguration> configurationSupplier = TelemetryConfiguration::getActive;

    @PostConstruct
    void initializeInstrumentationKey() {
        this.getConfigurationSupplier().get().setInstrumentationKey(applicationInsightsProperties().getTelemetryKey());
    }

    @PostConstruct
    void setApplicationName() {
        CloudRoleNameInitializer.setApplicationName(applicationInsightsProperties().getApplicationName());
    }

    @PostConstruct
    void initializeLogger() {
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        ApplicationInsightsAppender applicationInsightsAppender = new ApplicationInsightsAppender();
        applicationInsightsAppender.setContext(loggerContext);
        applicationInsightsAppender.setInstrumentationKey(applicationInsightsProperties().getTelemetryKey());
        applicationInsightsAppender.setName("applicationInsightsAppender");
        applicationInsightsAppender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(applicationInsightsAppender);
    }

    @Bean
    public ApplicationInsightsProperties applicationInsightsProperties() {
        return new ApplicationInsightsProperties();
    }

    @Bean
    public ApplicationInsightsAuditEventSink applicationInsightsAuditEventSink() {
        return new ApplicationInsightsAuditEventSink();
    }

    @Bean
    public FilterRegistrationBean<Filter> webRequestTrackingFilterRegistration() {
       FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
       registration.setFilter(new ApplicationInsightsFilter(this.webRequestTrackingFilter(), applicationInsightsProperties()));
       registration.setName("webRequestTrackingFilter");
       registration.addUrlPatterns("/*");
       registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
       return registration;
    }

    @Bean
    public WebRequestTrackingFilter webRequestTrackingFilter() {
        return new WebRequestTrackingFilter(applicationInsightsProperties().getApplicationName());
    }

    Supplier<TelemetryConfiguration> getConfigurationSupplier() {
        return this.configurationSupplier;
    }
    void setConfigurationSupplier(Supplier<TelemetryConfiguration> configurationSupplier) {
        this.configurationSupplier = configurationSupplier;
    }

}
