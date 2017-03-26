package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntIncrementer;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CompositionalPipelineTest {

    private IntProducer intProducer;
    private IntIncrementer intIncrementer;
    private IntIncrementer intIncrementer2;
    private IntConsumer intConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intIncrementer = new IntIncrementer();
        intIncrementer2 = new IntIncrementer();
        intConsumer = new IntConsumer();
    }

    @Test
    public void producerIsComposable() {
        Producer<IntDomainObj> composedProducer = DomainPipeline.createComposedProducer()
                .startingWith(intProducer)
                .thenTranslatedBy(intIncrementer)
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

//    @Test
//    public void translatorIsComposable() {
//        Translator<IntDomainObj, IntDomainObj> composedTranslator = DomainPipeline.createComposedTranslator()
//                .firstTranslatedBy(intIncrementer)
//                .thenTranslatedBy(intIncrementer2)
//                .build();
//
//        DomainPipeline pipeline = DomainPipeline.createPipeline()
//                .startingWith(intProducer)
//                .thenTranslatedBy(composedTranslator)
//                .thenConsumedBy(intConsumer)
//                .build();
//
//        pipeline.start();
//
//        for (int i = 0; i < 3; i++) {
//            intProducer.emitDomainObject(i);
//        }
//
//        assertThat(intConsumer.getReceivedDomainObjects(), contains(2, 3, 4));
//    }

    @Test
    public void consumerIsComposable() {
        Consumer<IntDomainObj> composedConsumer = DomainPipeline.createComposedConsumer()
                .firstTranslatedBy(intIncrementer)
                .thenConsumedBy(intConsumer)
                .build();

        DomainPipeline pipeline = DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenConsumedBy(composedConsumer)
                .build();

        pipeline.start();

        for (int i = 0; i < 3; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(intConsumer.getReceivedDomainObjects(), contains(1, 2, 3));
    }
}
