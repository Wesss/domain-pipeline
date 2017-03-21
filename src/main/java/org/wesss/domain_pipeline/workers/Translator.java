package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;

/**
 * @param <T> the type of domain obj consumed
 */
public abstract class Translator<T extends DomainObj, V extends DomainObj>
        implements DomainAcceptor<T>, DomainEmitter<V> {

    protected Emitter<V> emitter;
    private Class<T> acceptedClazz;

    public Translator(Class<T> acceptedClazz) {
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public void init(Emitter<V> emitter) {
        this.emitter = emitter;
    }

    @Override
    public abstract void acceptDomain(T domainObj);
}
