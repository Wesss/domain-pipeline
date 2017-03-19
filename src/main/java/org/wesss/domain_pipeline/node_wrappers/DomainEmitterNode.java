package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;

/**
 * Represents a single worker in a domain pipeline as a node in a directed acyclic graph
 */
public interface DomainEmitterNode<T extends DomainObj> extends DomainPipelineNode {

    /**
     * adds the given acceptor node as a child to this
     */
    public void addAcceptorNode(DomainAcceptorNode<T> acceptorNode);
}
