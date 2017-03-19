package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.workers.Producer;

public class DomainPipelineBuilder {

    public DomainPipelineBuilder() {

    }

    public DomainPipelineBuilderPostProducerStage startingWith(Producer<?> producer) {
        return new DomainPipelineBuilderPostProducerStage(producer);
    }
}
