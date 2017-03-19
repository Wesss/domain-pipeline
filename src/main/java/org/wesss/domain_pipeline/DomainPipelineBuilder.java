package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Producer;

public class DomainPipelineBuilder {

    // TODO delete and make actual builder
    public DomainPipeline buildBasicDomainPipeline(Producer<?> producer,
                                                Consumer<?> consumer) {
        return DomainPipelineFactory.getDomainPipeline(producer, consumer);
    }
}
