package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

/**
 * The building stage right after a consumer is given.
 */
public class DomainPipelineBuilderPostConsumerStage {

    private DomainPipelineCompiler compiler;
    private OneTimeUseToken useToken;

    public DomainPipelineBuilderPostConsumerStage(DomainPipelineCompiler compiler) {
        this.compiler = compiler;
        useToken = new OneTimeUseToken();
    }

    public DomainPipeline build() {
        useToken.use();

        return compiler.compile();
    }
}
