package org.wesss.domain_pipeline.compiler;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.routing.MethodRouterFactory;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class InterNodeRoutingWalker extends PipelineWalker {

    @Override
    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        super.visit(producerNode);

        setRouting(producerNode);
    }

    @Override
    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        super.visit(translatorNode);

        setRouting(translatorNode);
    }

    @Override
    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {
        super.visit(consumerNode);
    }

    public static <T extends DomainObj> void setRouting(DomainPasserNode<T> passerNode) {
        Set<MethodRouter<? super T>> childMethodRouters = passerNode
                .getChildrenAcceptors().stream()
                .map(DomainAcceptorNode::getMethodRouter)
                .collect(Collectors.toSet());

        Emitter<T> emitter = new Emitter<>(childMethodRouters);

        passerNode.setEmitter(emitter);
    }
}
