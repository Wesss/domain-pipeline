package org.wesss.domain_pipeline.compilers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.EmitterFactory;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.routing.MethodRouterFactory;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A utility class for hooking up DomainPipelineNodes
 */
public class Compilation {

    public static <T extends DomainObj> void initAcceptorNode(DomainAcceptorNode<T> acceptorNode) {
        acceptorNode.initMethodRouter(
                MethodRouterFactory.getMethodRouter(acceptorNode.getDomainAcceptor())
        );

        acceptorNode.initRecursiveEmitter(
                EmitterFactory.getEmitter(ArrayUtils.asSet(acceptorNode.getMethodRouter()))
        );
    }

    public static <T extends DomainObj> void initPasserNode(DomainPasserNode<T> passerNode) {
        DomainPasser<T> passer = passerNode.getDomainPasser();

        Set<MethodRouter<? super T>> childMethodRouters = passerNode
                .getChildrenAcceptors().stream()
                .map(DomainAcceptorNode::getMethodRouter)
                .collect(Collectors.toSet());

        Emitter<T> emitter = EmitterFactory.getEmitter(childMethodRouters);

        passerNode.initEmitter(emitter);
    }
}
