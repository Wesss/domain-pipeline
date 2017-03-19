package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

/**
 * This class represents the entirety of a domain pipeline that generates domain objects
 * and passes them along until consumption.
 */
public class DomainPipeline {
    private final ProducerNode<?> rootNode;

    public DomainPipeline(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    public void start() {
        rootNode.start();
    }
}
