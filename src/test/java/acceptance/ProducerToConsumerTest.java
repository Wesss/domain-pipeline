package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.builder.DomainPipelineBuilder;
import test_instantiation.TestConsumer;
import test_instantiation.TestProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProducerToConsumerTest {

    private TestProducer testGenerator;
    private TestConsumer testConsumer;
    private DomainPipeline minimalPipeline;

    @Before
    public void before() {
        testGenerator = new TestProducer();
        testConsumer = new TestConsumer();
        minimalPipeline = new DomainPipelineBuilder()
                .startingWith(testGenerator)
                .thenConsumedBy(testConsumer)
                .build();

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
}
