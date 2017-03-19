package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.builder.DomainPipelineBuilder;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProducerToConsumerTest {

    private IntProducer intProducer;
    private IntConsumer intConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intConsumer = new IntConsumer();
        DomainPipeline minimalPipeline = new DomainPipelineBuilder()
                .startingWith(intProducer)
                .thenConsumedBy(intConsumer)
                .build();

        minimalPipeline.start();
    }

    /********** Test Cases **********/

    @Test
    public void generatingNothingDoesNothing() {
        assertThat(intConsumer.getReceivedDomainObjects(), empty());
    }

    @Test
    public void sentDomainObjectIsConsumed() {
        intProducer.emitDomainObject(0);

        assertThat(intConsumer.getReceivedDomainObjects(), contains(0));
    }

    @Test
    public void manySentDomainObjectAreConsumedInOrder() {
        for (int i = 0; i < 7; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(intConsumer.getReceivedDomainObjects(), contains(0, 1, 2, 3, 4, 5, 6));
    }
}
