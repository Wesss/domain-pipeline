package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;

/**
 * @param <T> the type of domain obj consumed
 */
public abstract class Consumer<T extends DomainObj> implements DomainAcceptor<T> {

    /**
     * Emits domain objects back into this consumer
     */
    protected Emitter<T> recursiveEmitter;
    private Class<T> acceptedClazz;

    /**
     * @param acceptedClazz the class that is accepted by this consumer
     */
    public Consumer(Class<T> acceptedClazz) {
        this.recursiveEmitter = Emitter.getStubEmitter();
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public void initAcceptor(Emitter<T> recursiveEmitter) {
        this.recursiveEmitter = recursiveEmitter;
    }

    @Override
    public abstract void acceptDomain(T domainObj);
}
