package org.wesss.domain_pipeline.workers;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.routing.Emitter;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.reset;
import static org.wesss.test_utils.MockUtils.genericMock;

public class ProducerTest {

    private IntProducer testGenerator;
    private Emitter<IntDomainObj> mockEmitter;

    public ProducerTest() {
        mockEmitter = genericMock(Emitter.class);
    }

    @Before
    public void before() {
        reset(mockEmitter);
        testGenerator = new IntProducer();
    }

    @Test
    public void doingNothingDoesntCallRun() {
        assertThat(testGenerator.getRunCalls(), is(0));
    }

    @Test
    public void startBeforeInitThrowsError() throws InterruptedException {
        try {
            testGenerator.start();
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void startCallsRun() throws InterruptedException {
        testGenerator.initPasser(mockEmitter);
        testGenerator.start();

        // wait to ensure start thread is invoked
        Thread.sleep(50);

        assertThat(testGenerator.getRunCalls(), is(1));
    }
}
