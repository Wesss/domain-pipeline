package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.util.AccumulatingConsumer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;
import org.wesss.general_utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class TranslatorTester<T extends DomainObj, V extends DomainObj> {

    private TranslatorAsProducer<T> producer;
    private TranslatorNode<T, V> translatorNode;
    private AccumulatingConsumer<DomainObj> accumulatingConsumer;

    TranslatorTester(TranslatorAsProducer<T> producer,
                     TranslatorNode<T, V> translatorNode,
                     AccumulatingConsumer<DomainObj> accumulatingConsumer) {
        this.producer = producer;
        this.translatorNode = translatorNode;
        this.accumulatingConsumer = accumulatingConsumer;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        producer.acceptDomain(domainObj);
    }

    /**
     * @return a list of all DomainObjs emitted since the last call to getEmissions in order
     * that they were emitted
     */
    public List<V> getEmissions() {
        List<DomainObj> emissions = accumulatingConsumer.getReceivedDomainObjects();
        List<V> typedEmissions = new ArrayList<>();
        for (DomainObj obj : emissions) {
            typedEmissions.add((V) obj);
        }
        return typedEmissions;
    }

    /**
     * @return the translator currently under test. This may change if given worker swapped itself with a new
     * worker.
     */
    public Translator<T, V> getTestedTranslator() {
        return translatorNode.getTranslator();
    }
}
