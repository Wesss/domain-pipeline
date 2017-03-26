package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;

public class ComposedTranslator<T extends DomainObj, V extends DomainObj> extends Translator<T, V> {

    // TODO possibly make composed consumer/producer extend composed translator and limit functionality

    private DomainAcceptorNode<T> rootNode;
    private Emitter<T> emitterToRootNode;
    private DomainPasserNode<V> endNode;

    public ComposedTranslator(DomainAcceptorNode<T> rootNode,
                              Emitter<T> emitterToRootNode,
                              DomainPasserNode<V> endNode) {
        super(rootNode.getDomainAcceptor().getAcceptedClass());
        this.rootNode = rootNode;
        this.emitterToRootNode = emitterToRootNode;
        this.endNode = endNode;
    }

    @Override
    public void initAcceptor(Emitter<T> recursiveEmitter) {
        super.initAcceptor(recursiveEmitter);
        rootNode.getDomainAcceptor().initAcceptor(emitterToRootNode);
    }

    @Override
    public void acceptDomain(T domainObj) {
        emitterToRootNode.emit(domainObj);
    }

    @Override
    public void initPasser(Emitter<V> emitter) {
        super.initPasser(emitter);
        endNode.getDomainPasser().initPasser(emitter);
    }
}
