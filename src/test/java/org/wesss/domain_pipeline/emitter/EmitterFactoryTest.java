package org.wesss.domain_pipeline.emitter;

import org.junit.Test;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmitterFactoryTest {

    public EmitterFactoryTest() {

    }

    @Test
    public void createMinimalEmitter() throws NoSuchMethodException {
        IntConsumer domainAcceptor = new IntConsumer();
        IntProducer domainEmitter = new IntProducer();

        Emitter<IntDomainObj> expected = null;
        Emitter<IntDomainObj> actual = null;

        assertThat(actual, is(expected));
    }
}
