package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_workers.Consumer;
import org.wesss.domain_pipeline.pipeline_workers.Generator;

/**
 * This represents the entirety of a domain pipeline that generates domain objects and passes them along
 * until consumption.
 */
public class DomainPipeline {
    private final Generator<?> generator;
    private final Consumer<?> consumer;

    // Only to be called by builder
    DomainPipeline(Generator generator, Consumer consumer) {
        this.generator = generator;
        this.consumer = consumer;
    }

    public void start() {

    }
}
