package com.sapient.azure.auditing.applicationinsights;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.sapient.azure.auditing.AuditEventSink;
import com.sapient.azure.auditing.auditype.Event;
import com.sapient.azure.auditing.auditype.ExternalRequest;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Supplier;

class ApplicationInsightsAuditEventSink implements AuditEventSink {

    private Supplier<TelemetryConfiguration> configurationSupplier = TelemetryConfiguration::getActive;
    private TelemetryClient telemetryClient = null;

    @PostConstruct
    void initialize() {
        TelemetryConfiguration telemetryConfiguration = this.getConfigurationSupplier().get();
        telemetryClient = new TelemetryClient(telemetryConfiguration);
    }

    @Override
    public void process(Event event) {
        EventTelemetry eventTelemetry = new EventTelemetry();

        eventTelemetry.setName(event.getName());
        eventTelemetry.setTimestamp(new java.util.Date(event.getTimestamp().toEpochMilli()));
        eventTelemetry.getProperties().put("operationId", event.getOperationId());
        eventTelemetry.getProperties().putAll(event.getProperties());
        eventTelemetry.getMetrics().putAll(event.getMetrics());

        telemetryClient.trackEvent(eventTelemetry);
    }

    @Override
    public void process(ExternalRequest request) {
        RemoteDependencyTelemetry requestTelemetry = new RemoteDependencyTelemetry();

        Duration requestTelemetryDuration = request.getDuration() == null ? null : new Duration(request.getDuration().toMillis());
        requestTelemetry.setDuration(requestTelemetryDuration);
        requestTelemetry.setName(request.getName());
        requestTelemetry.setResultCode(request.getResultCode());
        requestTelemetry.setTimestamp(new Date(request.getTimestamp().toEpochMilli()));
        requestTelemetry.setTarget(request.getTarget());
        requestTelemetry.setSuccess(request.isSuccess());
        requestTelemetry.setType(request.getType());

        telemetryClient.trackDependency(requestTelemetry);
    }

    Supplier<TelemetryConfiguration> getConfigurationSupplier() {
        return this.configurationSupplier;
    }
    void setConfigurationSupplier(Supplier<TelemetryConfiguration> configurationSupplier) {
        this.configurationSupplier = configurationSupplier;
    }

    private TelemetryClient getTelemetryClient() {
        return this.telemetryClient;
    }
    private void setTelemetryClient(TelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

}
