package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
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

    /**
     * stores given method routher as the method routher for this domain acceptor
     */
    public void initMethodRouter(MethodRouter<T> methodRouter);

    /**
     * @return stored method routher iff it has been initialized
     */
    public MethodRouter<T> getMethodRouter();

    /**
     * stores given emitter as the recursive emitter for this domain acceptor
     */
    public void initRecursiveEmitter(Emitter<T> emitter);

    /**
     * @return stored recursive emitter iff it has been initialized
     */
    public Emitter<T> getRecursiveEmitter();
}
