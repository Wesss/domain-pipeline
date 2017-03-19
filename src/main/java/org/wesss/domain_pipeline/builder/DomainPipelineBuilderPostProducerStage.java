package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;

public class DomainPipelineBuilderPostProducerStage {

    private final Producer<?> producer;

    public DomainPipelineBuilderPostProducerStage(Producer<?> producer) {
        this.producer = producer;
    }

    public DomainPipelineBuilderPostConsumerStage thenConsumedBy(Consumer<?> consumer) {
        return new DomainPipelineBuilderPostConsumerStage(producer, consumer);
    }
}
