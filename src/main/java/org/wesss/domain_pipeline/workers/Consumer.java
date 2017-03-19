package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;

public abstract class Consumer<T extends DomainObj> implements DomainAcceptor<T> {

    @Override
    public abstract void acceptDomain(T domainObj);
}
