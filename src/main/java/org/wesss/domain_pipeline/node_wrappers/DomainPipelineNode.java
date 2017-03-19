package org.wesss.domain_pipeline.node_wrappers;

public interface DomainPipelineNode {

    /**
     * Make this Pipeline subtree valid for execution (ie. by hooking up emitters)
     */
    public void build();
}
