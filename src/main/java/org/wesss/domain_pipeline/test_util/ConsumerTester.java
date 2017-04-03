package org.wesss.domain_pipeline.test_util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.TranslatorAsProducer;

public class ConsumerTester<T extends DomainObj> {

    TranslatorAsProducer<T> translatorAsProducer;

    ConsumerTester(TranslatorAsProducer<T> translatorAsProducer) {
        this.translatorAsProducer = translatorAsProducer;
    }

    /**
     * Passes given domain obj to the pipeline worker under test
     */
    public void passIn(T domainObj) {
        translatorAsProducer.acceptDomain(domainObj);
    }
}
