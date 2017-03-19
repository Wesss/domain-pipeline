package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

public class ConsumerNode<T extends DomainObj> implements DomainAcceptorNode<T> {

    private final Consumer<T> consumer;

    public ConsumerNode(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public DomainAcceptor<T> getDomainAcceptor() {
        return consumer;
    }

    @Override
    public void build() {

    }
}
