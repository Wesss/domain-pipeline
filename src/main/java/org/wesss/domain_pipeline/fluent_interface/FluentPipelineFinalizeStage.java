package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.compilers.FluentPipelineCompiler;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

/**
 * The building stage right after a consumer is given.
 */
public class FluentPipelineFinalizeStage {

    private FluentPipelineCompiler compiler;
    private OneTimeUseToken useToken;

    FluentPipelineFinalizeStage(FluentPipelineCompiler compiler) {
        this.compiler = compiler;
        useToken = new OneTimeUseToken();
    }

    public DomainPipeline build() {
        useToken.use();

        return compiler.compile();
    }
}
