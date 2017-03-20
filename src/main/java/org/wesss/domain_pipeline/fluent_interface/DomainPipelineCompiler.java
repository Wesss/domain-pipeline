package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

public class DomainPipelineCompiler {

    // TODO move compiler out of this package into node wrappers?

    private ProducerNode<?> rootNode;

    DomainPipelineCompiler(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    DomainPipeline compile() {
        rootNode.build();
        return new DomainPipeline(rootNode);
    }
}
