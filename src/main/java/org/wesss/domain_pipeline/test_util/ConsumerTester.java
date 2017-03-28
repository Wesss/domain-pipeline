package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.ManualProducer;

public class ConsumerTester<T extends DomainObj> {

    ManualProducer<T> manualProducer;

    ConsumerTester(ManualProducer<T> manualProducer) {
        this.manualProducer = manualProducer;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        manualProducer.emit(domainObj);
    }
}
