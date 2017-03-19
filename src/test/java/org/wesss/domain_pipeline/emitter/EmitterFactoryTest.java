package org.wesss.domain_pipeline.emitter;

import org.junit.Test;
import org.wesss.domain_pipeline.emitter.domain.DomainAcceptorMethod;
import org.wesss.domain_pipeline.emitter.domain.PostAnalysisDomainAcceptor;
import org.wesss.general_utils.collection.ArrayUtils;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;
import test_instantiation.inheritance_based_consumption.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static test_instantiation.inheritance_based_consumption.InheritDomainSubclassConsumer.ACCEPT_LEAF1_METHOD_NAME;
import static test_instantiation.inheritance_based_consumption.InheritDomainSubclassConsumer.ACCEPT_ROOT_METHOD_NAME;

public class EmitterFactoryTest {

    public EmitterFactoryTest() {

    }

    @Test
    public void createMinimalEmitter() throws NoSuchMethodException {
        IntProducer domainEmitter = new IntProducer();
        IntConsumer domainAcceptor = new IntConsumer();

        List<DomainAcceptorMethod> domainAcceptorMethods = ArrayUtils.asList(
                new DomainAcceptorMethod<>(
                        IntDomainObj.class,
                        domainAcceptor.getClass().getMethod("acceptDomain", IntDomainObj.class)
                )
        );

        Emitter<IntDomainObj> expected = new Emitter<>(ArrayUtils.asSet(
                new PostAnalysisDomainAcceptor(
                        domainAcceptor,
                        domainAcceptorMethods
                )
        ));

        Emitter<IntDomainObj> actual =
                EmitterFactory.getEmitter(domainEmitter, ArrayUtils.asSet(domainAcceptor));

        assertThat(actual, is(expected));
    }

    @Test
    public void createDualAcceptorEmitter() throws NoSuchMethodException {
        IntProducer domainEmitter = new IntProducer();
        IntConsumer domainAcceptor1 = new IntConsumer();
        IntConsumer domainAcceptor2 = new IntConsumer();

        List<DomainAcceptorMethod> domainAcceptorMethods1 = ArrayUtils.asList(
                new DomainAcceptorMethod<>(
                        IntDomainObj.class,
                        domainAcceptor1.getClass().getMethod("acceptDomain", IntDomainObj.class)
                )
        );
        List<DomainAcceptorMethod> domainAcceptorMethods2 = ArrayUtils.asList(
                new DomainAcceptorMethod<>(
                        IntDomainObj.class,
                        domainAcceptor2.getClass().getMethod("acceptDomain", IntDomainObj.class)
                )
        );

        Emitter<IntDomainObj> expected = new Emitter<>(ArrayUtils.asSet(
                new PostAnalysisDomainAcceptor(
                        domainAcceptor1,
                        domainAcceptorMethods1
                ),
                new PostAnalysisDomainAcceptor(
                        domainAcceptor2,
                        domainAcceptorMethods2
                )
        ));

        Emitter<IntDomainObj> actual =
                EmitterFactory.getEmitter(domainEmitter, ArrayUtils.asSet(domainAcceptor1, domainAcceptor2));

        assertThat(actual, is(expected));
    }

    @Test
    public void createDomainSubclassEmitter() throws NoSuchMethodException {
        InheritProducer domainEmitter = new InheritProducer();
        InheritDomainSubclassConsumer domainAcceptor = new InheritDomainSubclassConsumer();

        List<DomainAcceptorMethod> domainAcceptorMethods = ArrayUtils.asList(
                new DomainAcceptorMethod<>(
                        DomainObjLeaf1.class,
                        domainAcceptor.getClass().getMethod(ACCEPT_LEAF1_METHOD_NAME, DomainObjLeaf1.class)
                ),
                new DomainAcceptorMethod<>(
                        DomainObjRoot.class,
                        domainAcceptor.getClass().getMethod(ACCEPT_ROOT_METHOD_NAME, DomainObjRoot.class)
                )
        );

        Emitter<IntDomainObj> expected = new Emitter<>(ArrayUtils.asSet(
                new PostAnalysisDomainAcceptor(
                        domainAcceptor,
                        domainAcceptorMethods
                )
        ));

        Emitter<DomainObjRoot> actual =
                EmitterFactory.getEmitter(domainEmitter, ArrayUtils.asSet(domainAcceptor));

        assertThat(actual, is(expected));
    }
}
