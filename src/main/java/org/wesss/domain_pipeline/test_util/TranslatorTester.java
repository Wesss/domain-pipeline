package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.composable.AccumulatingConsumer;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;

import java.util.ArrayList;
import java.util.List;

public class TranslatorTester<T extends DomainObj, V extends DomainObj> {

    TranslatorAsProducer<T> translatorAsProducer;
    AccumulatingConsumer<DomainObj> accumulatingConsumer;

    TranslatorTester(TranslatorAsProducer<T> translatorAsProducer, AccumulatingConsumer<DomainObj> accumulatingConsumer) {
        this.translatorAsProducer = translatorAsProducer;
        this.accumulatingConsumer = accumulatingConsumer;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        translatorAsProducer.acceptDomain(domainObj);
    }

    /**
     * @return a list of all DomainObjs emitted since the last call to getEmissions in order
     * that they were emitted
     */
    public List<V> getEmissions() {
        List<DomainObj> emissions = accumulatingConsumer.getReceivedDomainObjects();
        List<V> typedEmissions = new ArrayList<>();
        for (DomainObj obj : emissions) {
            typedEmissions.add((V)obj);
        }
        return typedEmissions;
    }
}
