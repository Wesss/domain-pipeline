package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.workers.Producer;

public class ProducerNode<T extends DomainObj> implements DomainEmitterNode<T> {

    private Producer<T> producer;
    private DomainAcceptorNode<T> acceptorNode;

    public ProducerNode(Producer<T> producer) {
        this.producer = producer;
    }

    public void start() {
        producer.start();
    }


    @Override
    public void addAcceptorNode(DomainAcceptorNode<T> acceptorNode) {
        this.acceptorNode = acceptorNode;
    }

    @Override
    public void build() {
        acceptorNode.build();
        Emitter<T> emitter = EmitterFactory.getEmitter(producer, acceptorNode.getDomainAcceptor());
        producer.init(emitter);
    }
}
