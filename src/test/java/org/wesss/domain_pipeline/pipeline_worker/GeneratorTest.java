package org.wesss.domain_pipeline.pipeline_worker;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.reset;
import static org.wesss.test_utils.MockUtils.genericMock;

public class GeneratorTest {

    private TestGenerator testGenerator;
    private Emitter<DomainObj> mockEmitter;

    public GeneratorTest() {
        mockEmitter = genericMock(Emitter.class);
    }

    @Before
    public void before() {
        reset(mockEmitter);
        testGenerator = new TestGenerator();
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
        testGenerator.init(mockEmitter);
        testGenerator.start();

        // wait to ensure start thread is invoked
        Thread.sleep(10);

        assertThat(testGenerator.getRunCalls(), is(1));
    }

    /********** Test Class Setup **********/

    private class TestGenerator extends Generator<DomainObj> {

        private int runCalls;

        public TestGenerator() {
            runCalls = 0;
        }

        @Override
        protected void run() {
            runCalls++;
        }

        public int getRunCalls() {
            return runCalls;
        }
    }
}
