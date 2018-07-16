package com.sapient.dpdhl.gaq.auditing;

import com.sapient.dpdhl.gaq.auditing.model.AuditEvent;
import com.sapient.dpdhl.gaq.auditing.model.AuditRequest;

public interface AuditSink {

    void consume(AuditEvent event) throws Exception;
    void consume(AuditRequest request) throws Exception;

}
