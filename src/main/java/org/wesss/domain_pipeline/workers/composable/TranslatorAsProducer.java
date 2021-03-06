package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.Producer;

/**
 * A Translator that simply re-emit the domain objects it accepts, disguised as a producer
 */
public class TranslatorAsProducer<T extends DomainObj> extends Producer<T> implements DomainAcceptor<T> {

    private Class<T> acceptedClazz;

    public TranslatorAsProducer(Class<T> acceptedClazz) {
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    protected void run() {

    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public void initAcceptor(Emitter<T> ignored) {

    }

    @Override
    public void acceptDomain(T domainObj) {
        emitter.emit(domainObj);
    }
}
