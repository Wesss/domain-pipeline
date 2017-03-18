package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;

/**
 * Responsible for the creation and initialization of DomainPipelines.
 * <p>
 * Methods here should only be accessed through builders
 */
public class DomainPipelineFactory {

    static DomainPipeline getDomainPipeline(Generator generator, Consumer consumer) {
        return new DomainPipeline(generator, consumer);
    }
}
