package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.Producer;

public class ComposedConsumer<T extends DomainObj> extends Consumer<T> {

    private DomainAcceptorNode<T> rootNode;
    private Emitter<T> emitterToRootNode;

    public ComposedConsumer(DomainAcceptorNode<T> rootNode, Emitter<T> emitterToRootNode) {
        super(rootNode.getDomainAcceptor().getAcceptedClass());
        this.rootNode = rootNode;
        this.emitterToRootNode = emitterToRootNode;
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
}
