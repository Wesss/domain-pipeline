package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;

import java.util.List;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    private List<Consumer<T>> domainObjectAcceptors;

    Emitter(List<Consumer<T>> domainObjectAcceptors) {
        this.domainObjectAcceptors = domainObjectAcceptors;
    }

    public void emit(T domainObj) {
        for (Consumer<T> domainObjectAcceptor : domainObjectAcceptors) {
            domainObjectAcceptor.acceptDomainObject(domainObj);
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
