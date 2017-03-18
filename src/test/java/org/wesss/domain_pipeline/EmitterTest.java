package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import test_instantiation.TestIntDomainObj;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class EmitterTest {

    private Consumer<TestIntDomainObj> mockConsumer;
    private Emitter<TestIntDomainObj> emitter;

    public EmitterTest() {
        mockConsumer = genericMock(Consumer.class);
        emitter = new Emitter<>(Arrays.asList(mockConsumer));
    }

    @Before
    public void before() {
        reset(mockConsumer);
    }

    @Test
    public void emitNothingPassesNothingOn() {
        verify(mockConsumer, never()).acceptDomainObject(any());
    }

    @Test
    public void emitDomainObjPassesItOn() {
        TestIntDomainObj domainObj = new TestIntDomainObj(0);
        emitter.emit(domainObj);

        verify(mockConsumer).acceptDomainObject(domainObj);
    }

    @Test
    public void emitManyDomainObjPassesThemOnInOrder() {
        TestIntDomainObj domainObj0 = new TestIntDomainObj(0);
        TestIntDomainObj domainObj1 = new TestIntDomainObj(1);
        TestIntDomainObj domainObj2 = new TestIntDomainObj(2);
        emitter.emit(domainObj0);
        emitter.emit(domainObj1);
        emitter.emit(domainObj2);

        InOrder inOrder = inOrder(mockConsumer);
        inOrder.verify(mockConsumer).acceptDomainObject(domainObj0);
        inOrder.verify(mockConsumer).acceptDomainObject(domainObj1);
        inOrder.verify(mockConsumer).acceptDomainObject(domainObj2);
    }
}
