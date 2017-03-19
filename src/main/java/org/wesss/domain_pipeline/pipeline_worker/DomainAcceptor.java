package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;

public interface DomainAcceptor<T extends DomainObj> {

    /**
     * Returns the class of the accepted DomainObjs
     */
    public Class<T> getAcceptedDomainClass();

    /**
     * Do work according to the domain obj passed in
     */
    public void acceptDomain(T domainObj);
}
