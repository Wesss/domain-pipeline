package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;

/**
 * A domain pipeline worker that is capable of accepting domain objs passed out by other domain pipeline workers.
 *
 * @param <T> the type of domain obj accepted
 */
public interface DomainAcceptor<T extends DomainObj> {

    public static final String ACCEPT_DOMAIN_METHOD_NAME = "acceptDomain";

    /**
     * Return the class accepted by this acceptor
     */
    public Class<T> getAcceptedClass();

    /**
     * Accept the emitter to be used to pass on domain objs back into this.
     * This method must be called before work is passed to this object.
     */
    public void initAcceptor(Emitter<T> recursiveEmitter);

    /**
     * Do work according to the domain obj passed in
     */
    public void acceptDomain(T domainObj);
}
