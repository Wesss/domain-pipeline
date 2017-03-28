package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.util.ManualConsumer;
import org.wesss.domain_pipeline.util.ManualProducer;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;

/**
 * Interface for generating test instances of domain pipeline workers
 */
public class DomainPipelineTest {

    // TODO test testers

    /**
     * @return a test interface for testing the emitted domain objs of given producer
     */
    public static <T extends DomainObj> ProducerTester<T> testProducer(Producer<T> producer) {
        ManualConsumer<DomainObj> manualConsumer = new ManualConsumer<>(DomainObj.class);
        DomainPipeline.createPipeline()
                .startingWith(producer)
                .thenConsumedBy(manualConsumer)
                .build()
                .start();

        return new ProducerTester<>(manualConsumer);
    }

    /**
     * @return a test interface for passing in and testing emitted domain objs of given translator
     */
    public static <T extends DomainObj, V extends DomainObj>
    TranslatorTester<T, V> testTranslator(Translator<T, V> translator) {
        ManualProducer<T> manualProducer = new ManualProducer<>();
        ManualConsumer<DomainObj> manualConsumer = new ManualConsumer<>(DomainObj.class);
        DomainPipeline.createPipeline()
                .startingWith(manualProducer)
                .thenTranslatedBy(translator)
                .thenConsumedBy(manualConsumer)
                .build()
                .start();

        return new TranslatorTester<T, V>(manualProducer, manualConsumer);
    }

    /**
     * @return a test interface for passing domain objs to the consumer
     */
    public static <T extends DomainObj> ConsumerTester<T> testConsumer(Consumer<T> consumer) {
        ManualProducer<T> manualProducer = new ManualProducer<>();
        DomainPipeline.createPipeline()
                .startingWith(manualProducer)
                .thenConsumedBy(consumer)
                .build()
                .start();

        return new ConsumerTester<>(manualProducer);
    }
}
