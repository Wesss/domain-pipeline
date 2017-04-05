package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.routing.domain.MethodRoutingTable;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

import java.util.Set;

/**
 * Represents a node in the domain pipeline directed acyclic graph that is capable
 * of having a parent.
 */
public interface DomainAcceptorNode<T extends DomainObj> extends DomainPipelineNode {

    /**
     * Return the domain acceptor of this node
     */
    DomainAcceptor<T> getDomainAcceptor();

    /**
     * @return stored method router iff it has been initialized
     */
    public MethodRouter<T> getMethodRouter();

    public void setMethodRouter(MethodRouter<T> methodRouter);

    /**
     * @return stored recursive emitter iff it has been initialized
     */
    public Emitter<T> getRecursiveEmitter();

    public void setRecursiveEmitter(Emitter<T> recursiveEmitter);
}
