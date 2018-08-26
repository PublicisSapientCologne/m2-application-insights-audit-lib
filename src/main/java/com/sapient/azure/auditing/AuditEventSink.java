package com.sapient.azure.auditing;

import com.sapient.azure.auditing.auditype.Event;
import com.sapient.azure.auditing.auditype.ExternalRequest;

/**
 * Defines an endpoint where an event is being distributed to
 *
 * @author Christian Robert
 */
public interface AuditEventSink {

    void process(Event event);
    void process(ExternalRequest request);

}
