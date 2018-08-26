package com.sapient.azure.auditing.applicationinsights;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "applicationinsights")
public class ApplicationInsightsProperties {

    private String telemetryKey;
    private String applicationName;
    private List<String> ignoredPaths = new ArrayList<>();

    public String getTelemetryKey() {
        return telemetryKey;
    }
    public void setTelemetryKey(String telemetryKey) {
        this.telemetryKey = telemetryKey;
    }

    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<String> getIgnoredPaths() {
        return ignoredPaths;
    }
    public void setIgnoredPaths(List<String> ignoredPaths) {
        this.ignoredPaths = ignoredPaths;
    }
}
