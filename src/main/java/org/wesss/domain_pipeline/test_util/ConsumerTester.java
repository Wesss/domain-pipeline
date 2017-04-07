package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;

public class ConsumerTester<T extends DomainObj> {

    private TranslatorAsProducer<T> producer;
    private ConsumerNode<T> consumerNode;

    ConsumerTester(TranslatorAsProducer<T> producer, ConsumerNode<T> consumerNode) {
        this.producer = producer;
        this.consumerNode = consumerNode;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        producer.acceptDomain(domainObj);
    }

    /**
     * @return the consumer currently under test. This may change if given worker swapped itself with a new
     * worker.
     */
    public Consumer<T> getTestedConsumer() {
        return consumerNode.getConsumer();
    }
}
