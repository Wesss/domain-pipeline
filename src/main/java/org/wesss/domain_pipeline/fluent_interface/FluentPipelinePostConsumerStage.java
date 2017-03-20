package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

/**
 * The building stage right after a consumer is given.
 */
public class FluentPipelinePostConsumerStage {

    private DomainPipelineCompiler compiler;
    private OneTimeUseToken useToken;

    public FluentPipelinePostConsumerStage(DomainPipelineCompiler compiler) {
        this.compiler = compiler;
        useToken = new OneTimeUseToken();
    }

    public DomainPipeline build() {
        useToken.use();

        return compiler.compile();
    }
}
