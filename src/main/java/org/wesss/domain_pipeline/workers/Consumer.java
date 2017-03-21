package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;

/**
 *
 * @param <T> the type of domain obj consumed
 */
public abstract class Consumer<T extends DomainObj> implements DomainAcceptor<T> {

    private Class<T> acceptedClazz;

    public Consumer(Class<T> acceptedClazz) {
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public abstract void acceptDomain(T domainObj);
}
