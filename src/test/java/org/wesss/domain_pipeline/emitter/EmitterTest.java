package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.wesss.domain_pipeline.Emitter.MethodDeterminer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import test_instantiation.inheritance_based_consumption.DomainObjLeaf1;
import test_instantiation.inheritance_based_consumption.DomainObjRoot;
import test_instantiation.inheritance_based_consumption.InheritSubclassConsumer;

import java.lang.reflect.Method;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EmitterTest {

    private InheritSubclassConsumer mockConsumer1;
    private InheritSubclassConsumer mockConsumer2;
    private Emitter<DomainObjRoot> emitter;

    public EmitterTest() {
        mockConsumer1 = mock(InheritSubclassConsumer.class);
        mockConsumer2 = mock(InheritSubclassConsumer.class);
    }

    @Before
    public void before() throws NoSuchMethodException {
        reset(mockConsumer1, mockConsumer2);

        Set<DomainAcceptor<DomainObjRoot>> domainAcceptors =
                new HashSet<>(Arrays.asList(mockConsumer1, mockConsumer2));

        List<Class<? extends DomainObjRoot>> acceptedDomainObjs = new ArrayList<>();
        acceptedDomainObjs.add()

        Map<MethodDeterminer<DomainObjRoot, ? extends DomainObjRoot>, Method> methodDeterminerToMethod =
                new HashMap<>();
        methodDeterminerToMethod.put(new MethodDeterminer<>(mockConsumer1, DomainObjRoot.class),
                mockConsumer1.getClass().getMethod("acceptDomain", DomainObjRoot.class));
        methodDeterminerToMethod.put(new MethodDeterminer<>(mockConsumer1, DomainObjLeaf1.class),
                mockConsumer1.getClass().getMethod("acceptLeaf1", DomainObjLeaf1.class));
        methodDeterminerToMethod.put(new MethodDeterminer<>(mockConsumer2, DomainObjRoot.class),
                mockConsumer2.getClass().getMethod("acceptDomain", DomainObjRoot.class));
        methodDeterminerToMethod.put(new MethodDeterminer<>(mockConsumer2, DomainObjLeaf1.class),
                mockConsumer2.getClass().getMethod("acceptLeaf1", DomainObjLeaf1.class));

        emitter = new Emitter(domainAcceptors, methodDeterminerToMethod);
    }

    @Test
    public void emitNothingPassesNothingOn() {
        verify(mockConsumer1, never()).acceptDomain(any());
        verify(mockConsumer1, never()).acceptLeaf1(any());
        verify(mockConsumer2, never()).acceptDomain(any());
        verify(mockConsumer2, never()).acceptLeaf1(any());
    }

    @Test
    public void emittedDomainObjIsPassedOn() {
        DomainObjRoot domainObjRoot = new DomainObjRoot();
        emitter.emit(domainObjRoot);

        verify(mockConsumer1).acceptDomain(domainObjRoot);
    }

    @Test
    public void manyEmittedDomainObjPassesThemOnInOrder() {
        DomainObjRoot domainObj0 = new DomainObjRoot();
        DomainObjRoot domainObj1 = new DomainObjRoot();
        DomainObjRoot domainObj2 = new DomainObjRoot();
        emitter.emit(domainObj0);
        emitter.emit(domainObj1);
        emitter.emit(domainObj2);

        InOrder inOrder = inOrder(mockConsumer1);
        inOrder.verify(mockConsumer1).acceptDomain(domainObj0);
        inOrder.verify(mockConsumer1).acceptDomain(domainObj1);
        inOrder.verify(mockConsumer1).acceptDomain(domainObj2);
    }

    @Test
    public void emittedDomainObjsArePassedOntoAllAcceptors() {
        DomainObjRoot domainObj = new DomainObjRoot();
        emitter.emit(domainObj);

        verify(mockConsumer1).acceptDomain(domainObj);
        verify(mockConsumer2).acceptDomain(domainObj);
    }

    @Test
    public void emittedSubclassedDomainObjsArePassedOnToReroutedAcceptMethods() {
        DomainObjLeaf1 domainObj = new DomainObjLeaf1();
        emitter.emit(domainObj);

        verify(mockConsumer1).acceptLeaf1(domainObj);
        verify(mockConsumer1, never()).acceptDomain(domainObj);
    }
}
