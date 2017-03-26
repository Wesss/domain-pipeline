package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import test_instantiation.basic.IntProducer;
import test_instantiation.basic.IntRecursiveConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class RecursiveConsumptionPipelineTest {

    private IntProducer intProducer;
    private IntRecursiveConsumer intRecursiveConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intRecursiveConsumer = new IntRecursiveConsumer();
        DomainPipeline minimalPipeline = DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenConsumedBy(intRecursiveConsumer)
                .build();

        minimalPipeline.start();
    }

    /********** Test Cases **********/

    @Test
    public void domainObjsAreConsumedRecursively() {
        intProducer.emitDomainObject(5);
        intProducer.emitDomainObject(-5);
        intProducer.emitDomainObject(-15);

        assertThat(intRecursiveConsumer.getReceivedDomainObjects(), contains(5, -5, 5, -15, -5, 5));
    }
}
