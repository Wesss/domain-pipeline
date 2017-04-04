package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.compilers.PipelineCompiler;

/**
 * Represents a single worker in a domain pipeline as a node in a directed acyclic graph.
 * Nodes represent pipeline workers, whilst an edge represents the parent node sending
 * domain objs to the child node.
 */
public interface DomainPipelineNode {

    /**
     * Build this node using given compiler
     */
    public void build(PipelineCompiler compiler);
}
