package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;

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
            DomainPipeline getDomainPipeline(Generator<T> generator,
                                             Consumer<V> consumer) {
        // TODO extract out type comparisons?
        // check types are the same
        Class<T> generatorClazz = generator.getDomainObjClass();
        Class<V> consumerClazz = consumer.getDomainObjClass();

        // TODO check for subclassing, not exact classing
        if (!generatorClazz.equals(consumerClazz)) {
            throw new IllegalArgumentException("generator and consumer DomainObj type mismatch");
        }

        // create emitter and put into generator
        Emitter<V> emitter = EmitterFactory.getEmitter(consumer);
        generator.init((Emitter<T>)emitter);

        return new DomainPipeline(generator, consumer);
    }
}
