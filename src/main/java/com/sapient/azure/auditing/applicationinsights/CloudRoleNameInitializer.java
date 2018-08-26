package com.sapient.azure.auditing.applicationinsights;

import com.microsoft.applicationinsights.extensibility.TelemetryInitializer;
import com.microsoft.applicationinsights.extensibility.context.ContextTagKeys;
import com.microsoft.applicationinsights.telemetry.Telemetry;

public class CloudRoleNameInitializer implements TelemetryInitializer {

    /**
     * The name of the application.
     *
     * Unfortunately CloudRoleNameInitializer and how ApplicationInsights SDK by Microsoft in general is working
     * is not handled by spring so we cannot just use a spring property here.
     *
     * ApplicationName will be set by {@link ApplicationInsightsConfiguration#setApplicationName()}
     */
    private static String applicationName;

    @Override
    public void initialize(Telemetry telemetry) {
        String deviceRoleName = ContextTagKeys.getKeys().getDeviceRoleName();
        telemetry.getContext().getTags().put(deviceRoleName, applicationName);
    }

    public static void setApplicationName(String applicationName) {
        CloudRoleNameInitializer.applicationName = applicationName;
    }
}