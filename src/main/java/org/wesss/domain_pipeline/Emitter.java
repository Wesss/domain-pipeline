package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.DomainAcceptor;

import java.util.List;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    private List<DomainAcceptor<T>> domainAcceptors;

    Emitter(List<DomainAcceptor<T>> domainAcceptors) {
        this.domainAcceptors = domainAcceptors;
    }

    public void emit(T domainObj) {
        for (DomainAcceptor<T> domainAcceptor : domainAcceptors) {
            domainAcceptor.acceptDomain(domainObj);
        }
    }

    /********** Static Utils **********/

    /**
     * returns an emitter that does nothing upon receiving DomainObjs to emit
     */
    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return EmitterFactory.getStubEmitter();
    }
}
