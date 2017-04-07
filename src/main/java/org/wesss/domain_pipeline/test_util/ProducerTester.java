package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.util.AccumulatingConsumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;

import java.util.ArrayList;
import java.util.List;

public class ProducerTester<T extends DomainObj> {

    private ProducerNode<T> producerNode;
    private AccumulatingConsumer<DomainObj> accumulatingConsumer;

    ProducerTester(ProducerNode<T> producerNode, AccumulatingConsumer<DomainObj> accumulatingConsumer) {
        this.producerNode = producerNode;
        this.accumulatingConsumer = accumulatingConsumer;
    }

    /**
     * @return a list of all DomainObjs emitted since the last call to getEmissions in order
     * that they were emitted
     */
    public List<T> getEmissions() {
        List<DomainObj> emissions = accumulatingConsumer.getReceivedDomainObjects();
        List<T> typedEmissions = new ArrayList<>();
        for (DomainObj obj : emissions) {
            typedEmissions.add((T) obj);
        }
        return typedEmissions;
    }

    /**
     * @return the producer currently under test. This may change if given worker swapped itself with a new
     * worker.
     */
    public Producer<T> getTestedProducer() {
        return producerNode.getProducer();
    }
}
