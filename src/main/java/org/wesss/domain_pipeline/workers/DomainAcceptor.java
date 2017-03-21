package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;

/**
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
     * Do work according to the domain obj passed in
     */
    public void acceptDomain(T domainObj);
}
