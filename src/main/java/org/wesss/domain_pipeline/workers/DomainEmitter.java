package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;

public interface DomainEmitter<T extends DomainObj> {

    /**
     * Returns the class of the emitted DomainObjs
     */
    public Class<T> getEmittedDomainClass();

    /**
     * Accept the emitter to be used for emitted DomainObjs.
     * This method must be called before work is passed to this object
     */
    public void init(Emitter<T> emitter);
}
