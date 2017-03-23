package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainPasser;

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

    static <T extends DomainObj> void buildEmitterNode(DomainPasser<T> domainPasser,
                                                       Set<DomainAcceptorNode<T>> children) {
        Set<DomainAcceptor<T>> childAcceptors = children.stream()
                .map(DomainAcceptorNode::getDomainAcceptor)
                .collect(Collectors.toSet());

        Emitter<T> emitter =
                EmitterFactory.getEmitter(
                        domainPasser,
                        childAcceptors
                );

        domainPasser.initPasser(emitter);
        for (DomainAcceptor<T> childAcceptor : childAcceptors) {
            childAcceptor.initAcceptor(emitter);
        }

        for (DomainAcceptorNode<T> child : children) {
            child.build();
        }
    }
}
