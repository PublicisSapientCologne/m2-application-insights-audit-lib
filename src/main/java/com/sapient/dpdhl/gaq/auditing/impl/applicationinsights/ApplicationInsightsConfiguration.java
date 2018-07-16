package com.sapient.dpdhl.gaq.auditing.impl.applicationinsights;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.logback.ApplicationInsightsAppender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@Configuration
class ApplicationInsightsConfiguration {

    private Supplier<TelemetryConfiguration> telemetryConfigurationSupplier = TelemetryConfiguration::getActive;
    private ApplicationInsightsSettings settings = null;

    @Bean
    TelemetryConfiguration applicationInsightsTelemetryConfiguration() {

        TelemetryConfiguration telemetryConfiguration = this.getTelemetryConfigurationSupplier().get();
        telemetryConfiguration.setInstrumentationKey(this.getSettings().getTelemetryKey());
        return telemetryConfiguration;

    }

    @Bean
    ApplicationInsightsAuditSink applicationInsightsAuditSink() {

        TelemetryConfiguration telemetryConfiguration = this.applicationInsightsTelemetryConfiguration();
        TelemetryClient telemetryClient = new TelemetryClient(telemetryConfiguration);

        ApplicationInsightsAuditSink auditSink = new ApplicationInsightsAuditSink(telemetryClient);
        auditSink.setDefaultName(this.getSettings().getDefaultName());
        auditSink.setDefaultSource(this.getSettings().getDefaultSource());
        return auditSink;

    }

    @PostConstruct
    void initializeLogger() {
        if (this.getSettings().isAuditLogging()) {

            LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();

            ApplicationInsightsAppender applicationInsightsAppender = new ApplicationInsightsAppender();
            applicationInsightsAppender.setContext(loggerContext);
            applicationInsightsAppender.setInstrumentationKey(this.getSettings().getTelemetryKey());
            applicationInsightsAppender.setName("applicationInsightsAppender");
            applicationInsightsAppender.start();

            Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(applicationInsightsAppender);

        }
    }

    Supplier<TelemetryConfiguration> getTelemetryConfigurationSupplier() {
        return this.telemetryConfigurationSupplier;
    }
    void setTelemetryConfigurationSupplier(Supplier<TelemetryConfiguration> telemetryConfigurationSupplier) {
        this.telemetryConfigurationSupplier = telemetryConfigurationSupplier;
    }

    ApplicationInsightsSettings getSettings() {
        return this.settings;
    }
    @Autowired
    void setSettings(ApplicationInsightsSettings settings) {
        this.settings = settings;
    }

}
