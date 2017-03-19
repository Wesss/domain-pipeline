package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

public class DomainPipelineCompiler {

    private ProducerNode<?> rootNode;

    DomainPipelineCompiler(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    DomainPipeline compile() {
        rootNode.build();
        return new DomainPipeline(rootNode);
    }
}
