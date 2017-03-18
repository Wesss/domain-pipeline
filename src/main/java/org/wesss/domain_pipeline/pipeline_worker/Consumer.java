package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;

public abstract class Consumer<T extends DomainObj> {

    private Class<T> domainObjClazz;

    public Consumer(Class<T> domainObjClazz) {
        this.domainObjClazz = domainObjClazz;
    }

    public Class<T> getDomainObjClass() {
        return domainObjClazz;
    }

    public abstract void acceptDomainObject(T domainObj);
}
