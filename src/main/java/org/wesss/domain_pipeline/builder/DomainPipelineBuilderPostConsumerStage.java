package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.DomainPipelineFactory;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;

public class DomainPipelineBuilderPostConsumerStage {

    private final Producer<?> producer;
    private final Consumer<?> consumer;

    public DomainPipelineBuilderPostConsumerStage(Producer<?> producer,
                                                  Consumer<?> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public DomainPipeline build() {
        return DomainPipelineFactory.getDomainPipeline(producer, consumer);
    }
}
