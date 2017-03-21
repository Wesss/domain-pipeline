package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainPipeline;

public class DomainPipelineCompiler {

    private ProducerNode<?> rootNode;

    public DomainPipelineCompiler(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    public DomainPipeline compile() {
        rootNode.build();
        return new DomainPipeline(rootNode);
    }
}
