package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;

public abstract class Consumer<T extends DomainObj> implements DomainAcceptor<T> {

    private Class<T> domainObjClazz;

    public Consumer(Class<T> domainObjClazz) {
        this.domainObjClazz = domainObjClazz;
    }

    @Override
    public Class<T> getAcceptedDomainClass() {
        return domainObjClazz;
    }

    @Override
    public abstract void acceptDomain(T domainObj);
}
