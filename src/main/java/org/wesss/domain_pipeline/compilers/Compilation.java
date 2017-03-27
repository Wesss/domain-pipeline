package org.wesss.domain_pipeline.compilers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A utility class for hooking up DomainPipelineNodes
 */
public class Compilation {

    /********** Static Utils **********/

    /**
     * Creates an emitter that emits domain objs from the given passer node to all of its chilcren and passes
     * said emitter to the passer and its children.
     */
    public static <T extends DomainObj> void hookUpPasserAndAcceptorNodes(DomainPasserNode<T> domainPasserNode) {
        DomainPasser<T> passer = domainPasserNode.getDomainPasser();

        Set<DomainAcceptor<? super T>> childAcceptors = domainPasserNode
                .getChildrenAcceptors().stream()
                .map(DomainAcceptorNode::getDomainAcceptor)
                .collect(Collectors.toSet());

        // init passer
        Emitter<T> emitter =
                EmitterFactory.getEmitter(
                        childAcceptors
                );

        passer.initPasser(emitter);

        // init child acceptors
        for (DomainAcceptor<? super T> childAcceptor : childAcceptors) {
            initChildAcceptor(childAcceptor);
        }
    }

    private static <T extends DomainObj> void initChildAcceptor(DomainAcceptor<T> childAcceptor) {
        Emitter<T> recursiveEmitter =
                EmitterFactory.getEmitter(
                        ArrayUtils.asSet(childAcceptor)
                );
        childAcceptor.initAcceptor(recursiveEmitter);
    }
}
