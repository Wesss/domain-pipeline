package org.wesss.domain_pipeline.routing.swapper;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.DomainPasserInitializingWalker;
import org.wesss.domain_pipeline.compiler.SwapperInitializingWalker;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;

public class ProducerSwapper<T extends DomainObj> {

    private ProducerNode<T> producerNode;

    public ProducerSwapper(ProducerNode<T> producerNode) {
        this.producerNode = producerNode;
    }

    public void swapTo(Producer<T> producer) {
        producerNode.setProducer(producer);
        DomainPasserInitializingWalker.initPasserNode(producerNode);
        SwapperInitializingWalker.initProducerSwapper(producerNode);
    }
}
