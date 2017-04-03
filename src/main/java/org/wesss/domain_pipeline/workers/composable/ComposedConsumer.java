package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.TranslatorAsProducer;
import org.wesss.domain_pipeline.workers.Consumer;

public class ComposedConsumer<T extends DomainObj> extends Consumer<T> {

    private TranslatorAsProducer<T> fakeProducer;

    public ComposedConsumer(TranslatorAsProducer<T> fakeProducer, Class<T> acceptedClazz) {
        super(acceptedClazz);
        this.fakeProducer = fakeProducer;
    }

    @Override
    public void acceptDomain(T domainObj) {
        fakeProducer.acceptDomain(domainObj);
    }
}
