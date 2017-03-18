package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;

public class DomainPipelineBuilder {

    // TODO change to actually be a builder
    public <T extends DomainObj> DomainPipeline buildDomainPipelineTESTENDPOINT(Generator<T> generator,
                                                                                Consumer<T> consumer) {
        return DomainPipelineFactory.getDomainPipeline(generator, consumer);
    }
}
