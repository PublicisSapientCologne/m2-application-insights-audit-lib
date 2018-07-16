package com.sapient.dpdhl.gaq.auditing.impl.applicationinsights;

import org.apache.commons.lang3.StringUtils;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.sapient.dpdhl.gaq.auditing.AuditSink;
import com.sapient.dpdhl.gaq.auditing.model.AuditEvent;
import com.sapient.dpdhl.gaq.auditing.model.AuditRequest;

class ApplicationInsightsAuditSink implements AuditSink {

    private TelemetryClient telemetryClient = null;
    private String defaultName = null;
    private String defaultSource = null;

    ApplicationInsightsAuditSink(TelemetryClient telemetryClient) {
        this.setTelemetryClient(telemetryClient);
    }

    @Override
    public void consume(AuditEvent event) throws Exception {

        EventTelemetry eventTelemetry = new EventTelemetry();
        eventTelemetry.setName(event.getName());
        eventTelemetry.setTimestamp(new java.util.Date(event.getTimestamp().toEpochMilli()));
        eventTelemetry.getProperties().putAll(event.getProperties());
        eventTelemetry.getMetrics().putAll(event.getMetrics());

        this.getTelemetryClient().trackEvent(eventTelemetry);

    }

    @Override
    public void consume(AuditRequest request) throws Exception {

        RequestTelemetry requestTelemetry = new RequestTelemetry();
        requestTelemetry.setDuration(request.getDuration() == null ? null : new Duration(request.getDuration().toMillis()));
        requestTelemetry.setName(StringUtils.defaultIfEmpty(request.getName(), this.getDefaultName()));
        requestTelemetry.setResponseCode(request.getResponseCode());
        requestTelemetry.setSource(StringUtils.defaultIfEmpty(request.getSource(), this.getDefaultSource()));
        requestTelemetry.setTimestamp(new java.util.Date(request.getTimestamp() == null ? System.currentTimeMillis() : request.getTimestamp().toEpochMilli()));
        requestTelemetry.setUrl(request.getUrl());

        this.getTelemetryClient().trackRequest(requestTelemetry);

    }

    TelemetryClient getTelemetryClient() {
        return this.telemetryClient;
    }
    private void setTelemetryClient(TelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

    String getDefaultName() {
        return this.defaultName;
    }
    void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    String getDefaultSource() {
        return this.defaultSource;
    }
    void setDefaultSource(String defaultSource) {
        this.defaultSource = defaultSource;
    }

}
