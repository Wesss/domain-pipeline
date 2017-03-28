package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.util.ManualConsumer;
import org.wesss.domain_pipeline.util.ManualProducer;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;

import java.util.ArrayList;
import java.util.List;

public class TranslatorTester<T extends DomainObj, V extends DomainObj> {

    ManualProducer<T> manualProducer;
    ManualConsumer<DomainObj> manualConsumer;

    TranslatorTester(ManualProducer<T> manualProducer, ManualConsumer<DomainObj> manualConsumer) {
        this.manualProducer = manualProducer;
        this.manualConsumer = manualConsumer;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        manualProducer.emit(domainObj);
    }

    /**
     * @return a list of all DomainObjs emitted since the last call to getEmissions in order
     * that they were emitted
     */
    public List<V> getEmissions() {
        List<DomainObj> emissions = manualConsumer.getReceivedDomainObjects();
        List<V> typedEmissions = new ArrayList<>();
        for (DomainObj obj : emissions) {
            typedEmissions.add((V)obj);
        }
        return typedEmissions;
    }
}
