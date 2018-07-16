package com.sapient.dpdhl.gaq.auditing;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sapient.dpdhl.gaq.auditing.model.AuditEvent;
import com.sapient.dpdhl.gaq.auditing.model.AuditRequest;

class AuditServiceImpl implements AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
    private List<AuditSink> auditSinks = null;

    @Override
    public void consume(AuditEvent event) {

        List<AuditSink> sinks = Optional.ofNullable(this.getAuditSinks()).orElseGet(Collections::emptyList);
        log.trace("Forwarding AuditEvent to {} sinks: {}", sinks.size(), event);

        sinks.forEach(sink -> {
            try {
                sink.consume(event);
            } catch (Exception e) {
                log.warn("Cannot forward AuditEvent '{}' to sink '{}'", event, sink, e);
            }
        });

    }

    @Override
    public void consume(AuditRequest request) {

        List<AuditSink> sinks = Optional.ofNullable(this.getAuditSinks()).orElseGet(Collections::emptyList);
        log.trace("Forwarding AuditRequest to {} sinks: {}", sinks.size(), request);

        sinks.forEach(sink -> {
            try {
                sink.consume(request);
            } catch (Exception e) {
                log.warn("Cannot forward AuditRequest '{}' to sink '{}'", request, sink, e);
            }
        });

    }

    List<AuditSink> getAuditSinks() {
        return this.auditSinks;
    }
    @Autowired(required = false)
    void setAuditSinks(List<AuditSink> auditSinks) {
        this.auditSinks = auditSinks;
    }

}
