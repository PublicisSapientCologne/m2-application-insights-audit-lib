package com.sapient.dpdhl.gaq.auditing.impl.applicationinsights;

public class ApplicationInsightsSettings {

    private String defaultName = null;
    private String defaultSource = null;
    private String telemetryKey = null;
    private boolean auditLogging = true;

    public String getDefaultName() {
        return this.defaultName;
    }
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultSource() {
        return this.defaultSource;
    }
    public void setDefaultSource(String defaultSource) {
        this.defaultSource = defaultSource;
    }

    public String getTelemetryKey() {
        return this.telemetryKey;
    }
    public void setTelemetryKey(String telemetryKey) {
        this.telemetryKey = telemetryKey;
    }

    public boolean isAuditLogging() {
        return this.auditLogging;
    }
    public void setAuditLogging(boolean auditLogging) {
        this.auditLogging = auditLogging;
    }

}
