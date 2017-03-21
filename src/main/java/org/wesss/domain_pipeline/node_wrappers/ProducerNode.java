package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.collection.ArrayUtils;

public class ProducerNode<T extends DomainObj> implements DomainEmitterNode<T> {

    private Producer<T> producer;
    private DomainAcceptorNode<T> child;

    public ProducerNode(Producer<T> producer) {
        this.producer = producer;
    }

    public void start() {
        producer.start();
    }

    @Override
    public void addChildAcceptor(DomainAcceptorNode<T> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public void build() {
        DomainEmitterNode.buildEmitterNode(producer, ArrayUtils.asSet(child));
    }
}
