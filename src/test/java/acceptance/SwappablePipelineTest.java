package acceptance;

import org.junit.Before;
import org.junit.Test;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntIncrementer;
import test_instantiation.basic.IntProducer;
import test_instantiation.swappable_workers.SwappableIntConsumer;
import test_instantiation.swappable_workers.SwappableIntProducer;
import test_instantiation.swappable_workers.SwappableIntTranslator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.wesss.domain_pipeline.DomainPipeline.createPipeline;

public class SwappablePipelineTest {

    private IntProducer intProducer;
    private IntIncrementer intIncrementer;
    private IntConsumer intConsumer;
    private SwappableIntProducer swappableIntProducer;
    private SwappableIntTranslator swappableIntTranslator;
    private SwappableIntConsumer swappableIntConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intIncrementer = new IntIncrementer();
        intConsumer = new IntConsumer();
        swappableIntProducer = new SwappableIntProducer();
        swappableIntTranslator = new SwappableIntTranslator();
        swappableIntConsumer = new SwappableIntConsumer();
    }

    @Test
    public void swappableProducerSwapsOut() {
        createPipeline()
                .startingWith(swappableIntProducer)
                .thenConsumedBy(intConsumer)
                .build()
                .start();

        swappableIntProducer.emitDomainObject(1);
        swappableIntProducer.swapTo(intProducer);
        intProducer.emitDomainObject(2);

        assertThat(intConsumer.getReceivedDomainObjects(), contains(1, 2));
    }

    @Test
    public void swappableTranslatorSwapsOut() {
        createPipeline()
                .startingWith(intProducer)
                .thenTranslatedBy(swappableIntTranslator)
                .thenConsumedBy(intConsumer)
                .build()
                .start();

        intProducer.emitDomainObject(1);
        swappableIntTranslator.swapTo(intIncrementer);
        intProducer.emitDomainObject(1);

        assertThat(intConsumer.getReceivedDomainObjects(), contains(1, 2));
    }

    @Test
    public void swappableConsumerSwapsOut() {
        createPipeline()
                .startingWith(intProducer)
                .thenConsumedBy(swappableIntConsumer)
                .build()
                .start();

        intProducer.emitDomainObject(1);
        swappableIntConsumer.swapTo(intConsumer);
        intProducer.emitDomainObject(2);

        assertThat(swappableIntConsumer.getReceivedDomainObjects(), contains(1));
        assertThat(intConsumer.getReceivedDomainObjects(), contains(2));
    }

    @Test
    public void fullySwappablePipelineCompletelySwapsOut() {
        createPipeline()
                .startingWith(swappableIntProducer)
                .thenTranslatedBy(swappableIntTranslator)
                .thenConsumedBy(swappableIntConsumer)
                .build()
                .start();

        swappableIntProducer.emitDomainObject(1);
        swappableIntProducer.swapTo(intProducer);
        swappableIntTranslator.swapTo(intIncrementer);
        swappableIntConsumer.swapTo(intConsumer);
        intProducer.emitDomainObject(1);

        assertThat(swappableIntConsumer.getReceivedDomainObjects(), contains(1));
        assertThat(intConsumer.getReceivedDomainObjects(), contains(2));
    }
}
