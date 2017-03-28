package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.ManualConsumer;

import java.util.ArrayList;
import java.util.List;

public class ProducerTester<T extends DomainObj> {

    ManualConsumer<DomainObj> manualConsumer;

    ProducerTester(ManualConsumer<DomainObj> manualConsumer) {
        this.manualConsumer = manualConsumer;
    }

    /**
     * @return a list of all DomainObjs emitted since the last call to getEmissions in order
     * that they were emitted
     */
    public List<T> getEmissions() {
        List<DomainObj> emissions = manualConsumer.getReceivedDomainObjects();
        List<T> typedEmissions = new ArrayList<>();
        for (DomainObj obj : emissions) {
            typedEmissions.add((T)obj);
        }
        return typedEmissions;
    }
}
