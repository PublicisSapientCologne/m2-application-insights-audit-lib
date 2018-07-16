package com.sapient.dpdhl.gaq.auditing.impl.applicationinsights;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;

import com.microsoft.applicationinsights.TelemetryConfiguration;

public class ApplicationInsightsConfigurationTest {

    @Test
    public void applicationInsightsAuditSink() {

        TelemetryConfiguration telemetryConfiguration = new TelemetryConfiguration();
        Assertions.assertNull(telemetryConfiguration.getInstrumentationKey());

        ApplicationInsightsSettings settings = new ApplicationInsightsSettings();
        settings.setTelemetryKey("settingsTelemetryKey");
        settings.setDefaultName("settingsDefaultName");
        settings.setDefaultSource("settingsDefaultSource");

        ApplicationInsightsConfiguration configuration = new ApplicationInsightsConfiguration();
        configuration.setSettings(settings);
        configuration.setTelemetryConfigurationSupplier(() -> telemetryConfiguration);

        ApplicationInsightsAuditSink auditSink = configuration.applicationInsightsAuditSink();
        Assertions.assertEquals("settingsTelemetryKey", auditSink.getTelemetryClient().getContext().getInstrumentationKey());
        Assertions.assertEquals("settingsDefaultName", auditSink.getDefaultName());
        Assertions.assertEquals("settingsDefaultSource", auditSink.getDefaultSource());

    }

    @Test
    public void applicationInsightsTelemetryConfiguration() {

        TelemetryConfiguration telemetryConfiguration = new TelemetryConfiguration();
        Assertions.assertNull(telemetryConfiguration.getInstrumentationKey());

        ApplicationInsightsSettings settings = new ApplicationInsightsSettings();
        settings.setTelemetryKey("settingsTelemetryKey");

        ApplicationInsightsConfiguration configuration = new ApplicationInsightsConfiguration();
        configuration.setSettings(settings);
        configuration.setTelemetryConfigurationSupplier(() -> telemetryConfiguration);

        TelemetryConfiguration telemetryConfigurationFromConfiguration = configuration.applicationInsightsTelemetryConfiguration();
        Assertions.assertEquals(telemetryConfiguration, telemetryConfigurationFromConfiguration);
        Assertions.assertEquals("settingsTelemetryKey", telemetryConfigurationFromConfiguration.getInstrumentationKey());

    }

    @Test
    public void applicationContextWithSettings() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(DummyConfigurationWithAnnotationAndSettings.class)) {
            ApplicationInsightsAuditSink auditSink = applicationContext.getBean(ApplicationInsightsAuditSink.class);
            Assertions.assertNotNull(auditSink);
        }
    }

    @Test
    public void applicationContextWithoutSettings() {
        Assertions.assertThrows(UnsatisfiedDependencyException.class, () -> new AnnotationConfigApplicationContext(DummyConfigurationWithAnnotation.class));
    }

    @EnableApplicationInsights
    static class DummyConfigurationWithAnnotation {
    }

    @EnableApplicationInsights
    static class DummyConfigurationWithAnnotationAndSettings {

        @Bean
        ApplicationInsightsSettings applicationInsightsSettings() {
            ApplicationInsightsSettings settings = new ApplicationInsightsSettings();
            settings.setAuditLogging(false);
            settings.setTelemetryKey("telemetryKeyExample");
            return settings;
        }

    }

}
