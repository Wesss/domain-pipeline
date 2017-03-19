package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

/**
 * Represents a node in the domain pipeline directed acyclic graph that is capable
 * of having a parent.
 */
public interface DomainAcceptorNode<T extends DomainObj> extends DomainPipelineNode {

    /**
     * Return the domain acceptor of this node
     */
    DomainAcceptor<T> getDomainAcceptor();
}
