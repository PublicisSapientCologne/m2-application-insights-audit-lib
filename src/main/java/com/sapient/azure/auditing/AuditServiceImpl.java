package com.sapient.azure.auditing;

import com.sapient.azure.auditing.auditype.Event;
import com.sapient.azure.auditing.auditype.ExternalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class AuditServiceImpl implements AuditService {

    private final Clock clock = Clock.systemUTC();
    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);

    private List<AuditEventSink> auditEventSinks = new ArrayList<>();

    @Override
    public void publish(Event event) {
        for (AuditEventSink eventSink : auditEventSinks) {
            try {
                eventSink.process(event);
            } catch (Exception e) {
                log.error("Cannot distribute event '{}' to AuditEventSink '{}'", event, eventSink, e);
            }
        }
    }

    @Override
    public void publish(ExternalRequest event) {
        for (AuditEventSink eventSink : auditEventSinks) {
            try {
                eventSink.process(event);
            } catch (Exception e) {
                log.error("Cannot distribute event '{}' to AuditEventSink '{}'", event, eventSink, e);
            }
        }
    }

    @Override
    public Instant getNow() {
        return clock.instant();
    }

    @Autowired(required = false)
    public void setAuditEventSinks(List<AuditEventSink> auditEventSinks) {
        if (auditEventSinks == null || auditEventSinks.size() == 0) {
            log.warn("No 'AuditEventSink's enabled. No events will be logged.");
        }
        this.auditEventSinks = auditEventSinks;
    }
}
