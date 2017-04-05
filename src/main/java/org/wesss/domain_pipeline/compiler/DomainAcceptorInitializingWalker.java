package org.wesss.domain_pipeline.compiler;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouterFactory;
import org.wesss.general_utils.collection.ArrayUtils;

public class DomainAcceptorInitializingWalker extends PipelineWalker {

    @Override
    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        super.visit(producerNode);
    }

    @Override
    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        super.visit(translatorNode);

        initAcceptorNode(translatorNode);
    }

    @Override
    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {
        super.visit(consumerNode);

        initAcceptorNode(consumerNode);
    }

    public static <T extends DomainObj> void initAcceptorNode(DomainAcceptorNode<T> acceptorNode) {
        acceptorNode.setMethodRouter(
                MethodRouterFactory.getMethodRouter(acceptorNode.getDomainAcceptor())
        );

        acceptorNode.setRecursiveEmitter(
                new Emitter<>(ArrayUtils.asSet(acceptorNode.getMethodRouter()))
        );

        acceptorNode.getDomainAcceptor().initAcceptor(acceptorNode.getRecursiveEmitter());
    }
}
