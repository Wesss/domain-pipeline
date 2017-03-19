package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;

/**
 * This class represents the entirety of a domain pipeline that generates domain objects
 * and passes them along until consumption.
 */
public class DomainPipeline {
    private final Producer<?> producer;
    private final Consumer<?> consumer;

    DomainPipeline(Producer<?> producer, Consumer<?> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public void start() {
        producer.start();
    }
}
