package org.wesss.domain_pipeline.routing.swapper;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.DomainAcceptorInitializingWalker;
import org.wesss.domain_pipeline.compiler.SwapperInitializingWalker;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.workers.Consumer;

public class ConsumerSwapper<T extends DomainObj> {

    private ConsumerNode<T> consumerNode;

    public ConsumerSwapper(ConsumerNode<T> consumerNode) {
        this.consumerNode = consumerNode;
    }

    public void swapTo(Consumer<T> consumer) {
        consumerNode.setConsumer(consumer);
        DomainAcceptorInitializingWalker.initAcceptorNode(consumerNode);
        SwapperInitializingWalker.initConsumerSwapper(consumerNode);
    }
}
