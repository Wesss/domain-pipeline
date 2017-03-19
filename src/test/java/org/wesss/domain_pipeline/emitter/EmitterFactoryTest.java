package org.wesss.domain_pipeline;

import org.junit.Test;
import org.wesss.domain_pipeline.Emitter.MethodDeterminer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;
import test_instantiation.inheritance_based_consumption.DomainObjLeaf1;
import test_instantiation.inheritance_based_consumption.DomainObjRoot;

import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmitterFactoryTest {

    public EmitterFactoryTest() {

    }

    @Test
    public void createMinimalEmitter() throws NoSuchMethodException {
        IntConsumer domainAcceptor = new IntConsumer();
        IntProducer domainEmitter = new IntProducer();

        Set<DomainAcceptor<IntDomainObj>> domainAcceptors =
                new HashSet<>(Arrays.asList(domainAcceptor));

        Map<MethodDeterminer<IntDomainObj, ? extends IntDomainObj>, Method> methodDeterminerToMethod =
                new HashMap<>();
        methodDeterminerToMethod.put(new MethodDeterminer<>(domainAcceptor, IntDomainObj.class),
                domainAcceptor.getClass().getMethod("acceptDomain", IntDomainObj.class));

        Emitter<IntDomainObj> expected = new Emitter(domainAcceptors, methodDeterminerToMethod);
        Emitter<IntDomainObj> actual =
                EmitterFactory.getEmitter(domainEmitter, new HashSet<>(Arrays.asList(domainAcceptor)));

        assertThat(actual, is(expected));
    }
}
