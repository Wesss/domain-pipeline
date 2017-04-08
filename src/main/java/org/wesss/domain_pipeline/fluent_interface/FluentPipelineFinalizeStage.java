package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.compiler.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;
import org.wesss.general_utils.language.Reference;

/**
 * The building stage right after a consumer is given.
 * @param <T> the type being consumed by the consumer node last given to this fluent interface
 */
public class FluentPipelineFinalizeStage<T extends DomainObj> {

    private FluentPipelineCompiler compiler;
    private ConsumerNode<T> consumerNode;
    private OneTimeUseToken useToken;

    FluentPipelineFinalizeStage(ConsumerNode<T> consumerNode, FluentPipelineCompiler compiler) {
        this.compiler = compiler;
        this.consumerNode = consumerNode;
        useToken = new OneTimeUseToken();
    }

    public DomainPipeline build() {
        useToken.use();

        return compiler.compile();
    }

    public FluentPipelineFinalizeStage savingNodeIn(Reference<ConsumerNode<T>> consumerReference) {
        consumerReference.setReference(consumerNode);
        return this;
    }
}
