package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.DomainPipelineBuilder;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;
import test_instantiation.TestDomainObj;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneratorToConsumerTest {

    private TestGenerator testGenerator;
    private TestConsumer testConsumer;
    private DomainPipeline minimalPipeline;

    @Before
    public void before() {
        testGenerator = new TestGenerator();
        testConsumer = new TestConsumer();
        minimalPipeline = null; // TODO wire in builder

        minimalPipeline.start();
    }

    /********** Test Cases **********/

    @Test
    public void generatingNothingDoesNothing() {
        assertThat(testConsumer.getReceivedDomainObjects(), empty());
    }

    @Test
    public void sentDomainObjectIsConsumed() {
        testGenerator.emitDomainObject(0);

        assertThat(testConsumer.getReceivedDomainObjects(), contains(0));
    }

    @Test
    public void manySentDomainObjectAreConsumedInOrder() {
        for (int i = 0; i < 7; i++) {
            testGenerator.emitDomainObject(i);
        }

        assertThat(testConsumer.getReceivedDomainObjects(), contains(0, 1, 2, 3, 4, 5, 6));
    }

    /********** Test Class Setup **********/

    private class TestGenerator extends Generator<TestDomainObj> {

        @Override
        protected void run() {

        }

        public void emitDomainObject(int id) {
            emitter.emit(new TestDomainObj(id));
        }
    }

    private class TestConsumer extends Consumer<TestDomainObj> {

        private List<Integer> receivedDomainObjects;

        public TestConsumer() {
            receivedDomainObjects = new ArrayList<>();
        }

        @Override
        public void acceptDomainObject(TestDomainObj domainObj) {
            receivedDomainObjects.add(domainObj.getId());
        }

        public List<Integer> getReceivedDomainObjects() {
            return new ArrayList<>(receivedDomainObjects);
        }
    }
}
