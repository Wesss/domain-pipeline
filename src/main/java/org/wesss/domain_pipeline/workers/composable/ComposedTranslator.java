package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.workers.Translator;

public class ComposedTranslator<T extends DomainObj, V extends DomainObj> extends Translator<T, V> {

    private TranslatorAsProducer<T> fakeProducer;
    private TranslatorAsConsumer<V> fakeConsumer;

    public ComposedTranslator(TranslatorAsProducer<T> fakeProducer,
                              TranslatorAsConsumer<V> fakeConsumer,
                              Class<T> acceptedClazz) {
        super(acceptedClazz);
        this.fakeProducer = fakeProducer;
        this.fakeConsumer = fakeConsumer;
    }

    @Override
    public void initPasser(Emitter<V> emitter) {
        super.initPasser(emitter);
        fakeConsumer.initPasser(emitter);
    }

    @Override
    public void acceptDomain(T domainObj) {
        fakeProducer.acceptDomain(domainObj);
    }
}
