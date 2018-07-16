package com.sapient.dpdhl.gaq.auditing;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sapient.dpdhl.gaq.auditing.model.AuditEvent;
import com.sapient.dpdhl.gaq.auditing.model.AuditRequest;

public class AuditServiceImplTest {

    @Test
    public void consumeEvent() throws Exception {

        AuditSink sink1 = Mockito.mock(AuditSink.class);
        AuditSink sink2 = Mockito.mock(AuditSink.class);

        AuditServiceImpl serviceImpl = new AuditServiceImpl();
        serviceImpl.setAuditSinks(List.of(sink1, sink2));

        AuditEvent event = new AuditEvent();
        serviceImpl.consume(event);

        Mockito.verify(sink1).consume(Mockito.same(event));
        Mockito.verify(sink2).consume(Mockito.same(event));

    }

    @Test
    public void consumeEventWithExceptionThrownFromFirstSink() throws Exception {

        AuditSink sink1 = Mockito.mock(AuditSink.class);
        Mockito.doThrow(new Exception("Dummy exception thrown from AuditSink")).when(sink1).consume(Mockito.any(AuditEvent.class));
        AuditSink sink2 = Mockito.mock(AuditSink.class);

        AuditServiceImpl serviceImpl = new AuditServiceImpl();
        serviceImpl.setAuditSinks(List.of(sink1, sink2));

        AuditEvent event = new AuditEvent();
        serviceImpl.consume(event);

        Mockito.verify(sink1).consume(Mockito.same(event));
        Mockito.verify(sink2).consume(Mockito.same(event));

    }

    @Test
    public void consumeRequest() throws Exception {

        AuditSink sink1 = Mockito.mock(AuditSink.class);
        AuditSink sink2 = Mockito.mock(AuditSink.class);

        AuditServiceImpl serviceImpl = new AuditServiceImpl();
        serviceImpl.setAuditSinks(List.of(sink1, sink2));

        AuditRequest event = new AuditRequest();
        serviceImpl.consume(event);

        Mockito.verify(sink1).consume(Mockito.same(event));
        Mockito.verify(sink2).consume(Mockito.same(event));

    }

    @Test
    public void consumeRequestWithExceptionThrownFromFirstSink() throws Exception {

        AuditSink sink1 = Mockito.mock(AuditSink.class);
        Mockito.doThrow(new Exception("Dummy exception thrown from AuditSink")).when(sink1).consume(Mockito.any(AuditRequest.class));
        AuditSink sink2 = Mockito.mock(AuditSink.class);

        AuditServiceImpl serviceImpl = new AuditServiceImpl();
        serviceImpl.setAuditSinks(List.of(sink1, sink2));

        AuditRequest event = new AuditRequest();
        serviceImpl.consume(event);

        Mockito.verify(sink1).consume(Mockito.same(event));
        Mockito.verify(sink2).consume(Mockito.same(event));

    }

}
