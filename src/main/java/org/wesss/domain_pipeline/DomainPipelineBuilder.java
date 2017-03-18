package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;

public class DomainPipelineBuilder {

    // TODO delete and make actual builder
    public DomainPipeline buildBasicDomainPipeline(Generator<?> generator,
                                                Consumer<?> consumer) {
        return DomainPipelineFactory.getDomainPipeline(generator, consumer);
    }
}
