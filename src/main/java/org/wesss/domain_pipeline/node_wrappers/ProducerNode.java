package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.workers.Producer;

public class ProducerNode<T extends DomainObj> implements DomainPipelineNode {

    private Producer<T> producer;
    private ConsumerNode<T> consumerNode;

    public ProducerNode(Producer<T> producer) {
        this.producer = producer;
    }

    public void start() {
        producer.start();
    }

    public void setConsumerNode(ConsumerNode<T> consumerNode) {
        this.consumerNode = consumerNode;
    }

    @Override
    public void build() {
        consumerNode.build();
        Emitter<T> emitter = EmitterFactory.getEmitter(producer, consumerNode.getDomainAcceptor());
        producer.init(emitter);
    }
}
