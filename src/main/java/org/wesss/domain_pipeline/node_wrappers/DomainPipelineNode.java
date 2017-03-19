package org.wesss.domain_pipeline.node_wrappers;

/**
 * Represents a single worker in a domain pipeline as a node in a directed acyclic graph.
 * Nodes represent pipline workers, whilst an edge represents the parent node sending
 * domain objs to the child node.
 */
public interface DomainPipelineNode {

    /**
     * Make this Pipeline node and all subsequent nodes valid for execution
     * (ie. by hooking up emitters).
     */
    public void build();
}
