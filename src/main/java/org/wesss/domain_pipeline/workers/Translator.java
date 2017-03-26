package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;

/**
 * @param <T> the type of domain obj consumed
 */
public abstract class Translator<T extends DomainObj, V extends DomainObj>
        implements DomainAcceptor<T>, DomainPasser<V> {

    // TODO possibly make consumer/producer extend translator and limit functionality

    /**
     * Emits domain objects back into this consumer
     */
    protected Emitter<T> recursiveEmitter;
    /**
     * Emits domain objects to the next pipeline worker(s)
     */
    protected Emitter<V> emitter;
    private Class<T> acceptedClazz;

    /**
     * @param acceptedClazz the class type that is accepted by this translator
     */
    public Translator(Class<T> acceptedClazz) {
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public void initPasser(Emitter<V> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void initAcceptor(Emitter<T> recursiveEmitter) {
        this.recursiveEmitter = recursiveEmitter;
    }

    @Override
    public abstract void acceptDomain(T domainObj);
}
