package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainPipeline;

/**
 * The building stage right after a consumer is given.
 */
public class DomainPipelineBuilderPostConsumerStage {

    private DomainPipelineCompiler compiler;

    public DomainPipelineBuilderPostConsumerStage(DomainPipelineCompiler compiler) {
        this.compiler = compiler;
    }

    public DomainPipeline build() {
        return compiler.compile();
    }
}
