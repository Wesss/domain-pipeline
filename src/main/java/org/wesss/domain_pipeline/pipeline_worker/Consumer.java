package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;

public abstract class Consumer<T extends DomainObj> {

    public abstract void acceptDomainObject(T domainObj);
}
