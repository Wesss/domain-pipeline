package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;

/**
 * A domain pipeline worker that is capable of passing out (aka emitting) domain objs
 *
 * @param <T> The type of domain obj emitted
 */
public interface DomainPasser<T extends DomainObj> {

    /**
     * Accept the emitter to be used to pass on domain objs to the next pipeline worker.
     * This method must be called before work is passed to this object
     */
    public void initPasser(Emitter<T> emitter);
}
