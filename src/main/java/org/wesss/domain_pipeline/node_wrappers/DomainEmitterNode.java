package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a single worker in a domain pipeline as a node in a directed acyclic graph
 */
public interface DomainEmitterNode<T extends DomainObj> extends DomainPipelineNode {

    /**
     * adds the given acceptor node as a child to this
     */
    public void addChildAcceptor(DomainAcceptorNode<T> acceptorNode);

    /********** Static Utils **********/

    static <T extends DomainObj> void buildEmitterNode(DomainEmitter<T> domainEmitter,
                                                       Set<DomainAcceptorNode<T>> children) {
        Emitter<T> emitter =
                EmitterFactory.getEmitter(
                        domainEmitter,
                        children.stream()
                                .map(DomainAcceptorNode::getDomainAcceptor)
                                .collect(Collectors.toSet())
                );

        domainEmitter.init(emitter);
        for (DomainAcceptorNode<T> child : children) {
            child.build();
        }
    }
}
