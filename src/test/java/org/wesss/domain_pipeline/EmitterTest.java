package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.wesss.domain_pipeline.workers.Consumer;
import test_instantiation.basic.IntDomainObj;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class EmitterTest {

    private Consumer<IntDomainObj> mockConsumer;
    private Consumer<IntDomainObj> mockConsumer2;
    private Emitter<IntDomainObj> emitter;

    public EmitterTest() {
        mockConsumer = genericMock(Consumer.class);
        mockConsumer2 = genericMock(Consumer.class);
        emitter = new Emitter<>(Arrays.asList(mockConsumer, mockConsumer2));
    }

    @Before
    public void before() {
        reset(mockConsumer, mockConsumer2);
    }

    @Test
    public void emitNothingPassesNothingOn() {
        verify(mockConsumer, never()).acceptDomain(any());
    }

    @Test
    public void emittedDomainObjIsPassedOn() {
        IntDomainObj domainObj = new IntDomainObj(0);
        emitter.emit(domainObj);

        verify(mockConsumer).acceptDomain(domainObj);
    }

    @Test
    public void manyEmittedDomainObjPassesThemOnInOrder() {
        IntDomainObj domainObj0 = new IntDomainObj(0);
        IntDomainObj domainObj1 = new IntDomainObj(1);
        IntDomainObj domainObj2 = new IntDomainObj(2);
        emitter.emit(domainObj0);
        emitter.emit(domainObj1);
        emitter.emit(domainObj2);

        InOrder inOrder = inOrder(mockConsumer);
        inOrder.verify(mockConsumer).acceptDomain(domainObj0);
        inOrder.verify(mockConsumer).acceptDomain(domainObj1);
        inOrder.verify(mockConsumer).acceptDomain(domainObj2);
    }

    @Test
    public void emittedDomainObjsArePassedOntoAllAcceptors() {
        IntDomainObj domainObj = new IntDomainObj(0);
        emitter.emit(domainObj);

        verify(mockConsumer).acceptDomain(domainObj);
        verify(mockConsumer2).acceptDomain(domainObj);
    }
}
