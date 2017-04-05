package org.wesss.domain_pipeline.compiler;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

public class FluentPipelineCompiler {

    private ProducerNode<?> rootNode;

    public FluentPipelineCompiler(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    public DomainPipeline compile() {
        rootNode.buildUsing(new DomainAcceptorInitializingWalker());
        rootNode.buildUsing(new InterNodeRoutingWalker());
        rootNode.buildUsing(new DomainPasserInitializingWalker());
        rootNode.buildUsing(new SwapperInitializingWalker());

        return new DomainPipeline(rootNode);
    }
}
