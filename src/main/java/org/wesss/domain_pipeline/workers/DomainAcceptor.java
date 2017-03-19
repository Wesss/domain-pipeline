package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;

public interface DomainAcceptor<T extends DomainObj> {

    /**
     * Do work according to the domain obj passed in
     */
    public void acceptDomain(T domainObj);
}
