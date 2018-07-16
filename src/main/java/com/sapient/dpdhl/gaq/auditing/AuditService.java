package com.sapient.dpdhl.gaq.auditing;

import com.sapient.dpdhl.gaq.auditing.model.AuditEvent;
import com.sapient.dpdhl.gaq.auditing.model.AuditRequest;

public interface AuditService {

    void consume(AuditEvent event);
    void consume(AuditRequest request);

}
