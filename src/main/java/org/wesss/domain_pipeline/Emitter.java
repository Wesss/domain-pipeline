package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    private Consumer<T> domainObjectAcceptor;

    Emitter(Consumer domainObjectAcceptor) {
        this.domainObjectAcceptor = domainObjectAcceptor;
    }

    public void emit(T domainObj) {
        domainObjectAcceptor.acceptDomainObject(domainObj);
    }

    /********** Static Utils **********/

    /**
     * returns an emitter that does nothing upon receiving DomainObjs to emit
     */
    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return EmitterFactory.getStubEmitter();
    }
}
