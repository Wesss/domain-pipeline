package org.wesss.domain_pipeline.compiler;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;

public class DomainPasserInitializingWalker extends PipelineWalker {

    @Override
    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        super.visit(producerNode);

        initPasserNode(producerNode);
    }

    @Override
    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        super.visit(translatorNode);

        initPasserNode(translatorNode);
    }

    @Override
    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {
        super.visit(consumerNode);
    }

    public static <T extends DomainObj> void initPasserNode(DomainPasserNode<T> passerNode) {
        passerNode.getDomainPasser().initPasser(passerNode.getEmitter());
    }
}
