package org.wesss.domain_pipeline.emitter;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.util.Set;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns an emitter that will pass objects given to it to given domainAcceptors
     */
    public static <T extends DomainObj> Emitter<T>
            getEmitter(DomainEmitter<T> domainEmitter, Set<DomainAcceptor<T>> domainAcceptor) {
        return null; // TODO
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        // TODO
        return null;
        //return new Emitter<>(new ArrayList<>());
    }
}
