package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;

public class ComposedProducer<T extends DomainObj> extends Producer<T> {

    private ProducerNode<?> rootNode;
    private DomainPasserNode<T> endNode;

    public ComposedProducer(ProducerNode<?> rootNode, DomainPasserNode<T> endNode) {
        this.rootNode = rootNode;
        this.endNode = endNode;
    }

    @Override
    public void initPasser(Emitter<T> emitter) {
        super.initPasser(emitter);
        endNode.getDomainPasser().initPasser(emitter);
    }

    @Override
    protected void run() {
        rootNode.start();
    }
}
