package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.util.AccumulatingConsumer;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;

/**
 * Interface for generating test instances of domain pipeline workers
 */
public class DomainPipelineTest {

    // TODO test testers

    /**
     * @return a test interface for testing the emitted domain objs of given producer
     */
    public static <T extends DomainObj> ProducerTester<T> testProducer(Producer<T> producer) {
        AccumulatingConsumer<DomainObj> accumulatingConsumer = new AccumulatingConsumer<>(DomainObj.class);
        DomainPipeline.createPipeline()
                .startingWith(producer)
                .thenConsumedBy(accumulatingConsumer)
                .build()
                .start();

        return new ProducerTester<>(accumulatingConsumer);
    }

    /**
     * @return a test interface for passing in and testing emitted domain objs of given translator
     */
    public static <T extends DomainObj, V extends DomainObj>
    TranslatorTester<T, V> testTranslator(Translator<T, V> translator) {
        TranslatorAsProducer<T> translatorAsProducer = new TranslatorAsProducer<>(translator.getAcceptedClass());
        AccumulatingConsumer<DomainObj> accumulatingConsumer = new AccumulatingConsumer<>(DomainObj.class);
        DomainPipeline.createPipeline()
                .startingWith(translatorAsProducer)
                .thenTranslatedBy(translator)
                .thenConsumedBy(accumulatingConsumer)
                .build()
                .start();

        return new TranslatorTester<T, V>(translatorAsProducer, accumulatingConsumer);
    }

    /**
     * @return a test interface for passing domain objs to the consumer
     */
    public static <T extends DomainObj> ConsumerTester<T> testConsumer(Consumer<T> consumer) {
        TranslatorAsProducer<T> translatorAsProducer = new TranslatorAsProducer<>(consumer.getAcceptedClass());
        DomainPipeline.createPipeline()
                .startingWith(translatorAsProducer)
                .thenConsumedBy(consumer)
                .build()
                .start();

        return new ConsumerTester<>(translatorAsProducer);
    }
}
