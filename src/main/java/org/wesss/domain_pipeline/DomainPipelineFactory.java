package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;

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
    public static <T extends DomainObj, V extends DomainObj>
            DomainPipeline getDomainPipeline(Producer<T> producer,
                                             Consumer<V> consumer) {
        Emitter<T> emitter = EmitterFactory.getEmitter(producer, consumer);
        producer.init(emitter);

        return new DomainPipeline(producer, consumer);
    }
}
