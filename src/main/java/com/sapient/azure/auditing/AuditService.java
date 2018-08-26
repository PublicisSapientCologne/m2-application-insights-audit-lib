package com.sapient.azure.auditing;

import com.sapient.azure.auditing.auditype.Event;
import com.sapient.azure.auditing.auditype.ExternalRequest;

import java.time.Instant;

/**
 * Service to send audit messages to enabled audit services.
 */
public interface AuditService {

    /**
     * Log an Event.
     * @param event
     */
    void publish(Event event);

    /**
     * Log a request to an external service.
     * @param event
     */
    void publish(ExternalRequest event);

    /**
     * Get the current time as an Instant.
     * @return now
     */
    Instant getNow();

}
