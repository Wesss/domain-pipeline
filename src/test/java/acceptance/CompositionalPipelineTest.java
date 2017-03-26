package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntIncrementer;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CompositionalPipelineTest {

    private IntProducer intProducer;
    private IntIncrementer intIncrementer0;
    private IntIncrementer intIncrementer1;
    private IntConsumer intConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intIncrementer0 = new IntIncrementer();
        intIncrementer1 = new IntIncrementer();
        intConsumer = new IntConsumer();
    }

    @Test
    public void producerIsComposable() {
        Producer<IntDomainObj> composedProducer = DomainPipeline.createComposedProducer()
                .startingWith(intProducer)
                .thenTranslatedBy(intIncrementer0)
                .build();

        DomainPipeline pipeline = DomainPipeline.createPipeline()
                .startingWith(composedProducer)
                .thenConsumedBy(intConsumer)
                .build();

        pipeline.start();

        for (int i = 0; i < 3; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(intConsumer.getReceivedDomainObjects(), contains(1, 2, 3));
    }

    @Test
    public void translatorIsComposable() {
        Translator<IntDomainObj, IntDomainObj> composedTranslator = DomainPipeline.createComposedTranslator()
                .firstTranslatedBy(intIncrementer0)
                .thenTranslatedBy(intIncrementer1)
                .build();

        DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenTranslatedBy(composedTranslator)
                .thenConsumedBy(intConsumer)
                .build()
                .start();

        for (int i = 0; i < 3; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(intConsumer.getReceivedDomainObjects(), contains(2, 3, 4));
    }

    @Test
    public void consumerIsComposable() {
        Consumer<IntDomainObj> composedConsumer = DomainPipeline.createComposedConsumer()
                .firstTranslatedBy(intIncrementer0)
                .thenConsumedBy(intConsumer)
                .build();

        DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenConsumedBy(composedConsumer)
                .build()
                .start();

        for (int i = 0; i < 3; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(intConsumer.getReceivedDomainObjects(), contains(1, 2, 3));
    }
}
