package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Producer;

/**
 * Responsible for the creation and initialization of DomainPipelines.
 * <p>
 * Methods here should only be accessed through builders.
 */
public class DomainPipelineFactory {

    private DomainPipelineFactory() {

    }

    /**
     * returns a DomainPipeline with all components fully initialized
     */
    static <T extends DomainObj, V extends DomainObj>
            DomainPipeline getDomainPipeline(Producer<T> producer,
                                             Consumer<V> consumer) {
        // TODO extract out type comparisons?
        // check types are the same
        Class<T> generatorClazz = producer.getEmittedDomainClass();
        Class<V> consumerClazz = consumer.getAcceptedDomainClass();

        // TODO check for subclassing, not exact classing
        if (!generatorClazz.equals(consumerClazz)) {
            throw new IllegalArgumentException("producer and consumer DomainObj type mismatch");
        }

        // create emitter and put into producer
        Emitter<V> emitter = EmitterFactory.getEmitter(consumer);
        producer.init((Emitter<T>)emitter);

        return new DomainPipeline(producer, consumer);
    }
}
