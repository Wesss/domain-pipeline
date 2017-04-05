package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.routing.domain.DomainAcceptorMethod;
import org.wesss.domain_pipeline.routing.domain.MethodRoutingTable;
import org.wesss.general_utils.collection.ArrayUtils;
import test_instantiation.annotated_consumption.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EmitterTest {

    private InheritDomainSubclassConsumer mockConsumer1;
    private InheritDomainSubclassConsumer mockConsumer2;
    private Emitter<DomainObjRoot> emitter;

    public EmitterTest() {
        mockConsumer1 = mock(InheritDomainSubclassConsumer.class);
        mockConsumer2 = mock(InheritDomainSubclassConsumer.class);
    }

    @Before
    public void before() throws NoSuchMethodException {
        reset(mockConsumer1, mockConsumer2);

        List<DomainAcceptorMethod<? extends DomainObjRoot>> domainAcceptorMethods1 = new ArrayList<>();
        domainAcceptorMethods1.add(new DomainAcceptorMethod<>(
                DomainObjLeaf1.class,
                mockConsumer1.getClass().getMethod("acceptLeaf1", DomainObjLeaf1.class)
        ));
        domainAcceptorMethods1.add(new DomainAcceptorMethod<>(
                DomainObjRoot.class,
                mockConsumer1.getClass().getMethod("acceptDomain", DomainObjRoot.class)
        ));

        List<DomainAcceptorMethod<? extends DomainObjRoot>> domainAcceptorMethods2 = new ArrayList<>();
        domainAcceptorMethods2.add(new DomainAcceptorMethod<>(
                DomainObjLeaf1.class,
                mockConsumer2.getClass().getMethod("acceptLeaf1", DomainObjLeaf1.class)
        ));
        domainAcceptorMethods2.add(new DomainAcceptorMethod<>(
                DomainObjRoot.class,
                mockConsumer2.getClass().getMethod("acceptDomain", DomainObjRoot.class)
        ));

        MethodRoutingTable<DomainObjRoot> methodRoutingTable = new MethodRoutingTable<>(domainAcceptorMethods1);
        MethodRoutingTable<DomainObjRoot> methodRoutingTable2 = new MethodRoutingTable<>(domainAcceptorMethods2);

        MethodRouter<DomainObjRoot> postAnalysisConsumer1 =
                new MethodRouter<>(mockConsumer1, methodRoutingTable);
        MethodRouter<DomainObjRoot> postAnalysisConsumer2 =
                new MethodRouter<>(mockConsumer2, methodRoutingTable2);

        emitter = new Emitter(ArrayUtils.asSet(postAnalysisConsumer1, postAnalysisConsumer2));
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

    @Test
    public void emittedUknownDomainObjsArePassedOnToClosestKnwonSuperclass() {
        DomainObjLeaf1_1 domainObj1_1 = new DomainObjLeaf1_1();
        DomainObjLeaf2 domainObj2 = new DomainObjLeaf2();
        emitter.emit(domainObj1_1);
        emitter.emit(domainObj2);

        verify(mockConsumer1).acceptLeaf1(domainObj1_1);
        verify(mockConsumer1).acceptDomain(domainObj2);
    }
}
