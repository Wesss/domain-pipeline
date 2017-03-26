package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainPasser;

import java.util.Set;

/**
 * Represents a single worker in a domain pipeline as a node in a directed acyclic graph
 * that is capable of having a child
 */
public interface DomainPasserNode<T extends DomainObj> extends DomainPipelineNode {

    /**
     * Return the domain passer of this node
     */
    DomainPasser<T> getDomainPasser();

    /**
     * adds the given acceptor node as a child to this
     */
    public void addChildAcceptor(DomainAcceptorNode<T> acceptorNode);

    /**
     * @return all of the acceptor nodes that accept domain objects passed out by this node
     */
    public Set<DomainAcceptorNode<T>> getChildrenAcceptors();
}
