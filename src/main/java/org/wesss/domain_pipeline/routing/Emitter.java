package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;

import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 *
 * @param <T> the type of emitted domain objs
 */
public class Emitter<T extends DomainObj> {

    private Set<MethodRouter<? super T>> domainAcceptors;

    public Emitter(Set<MethodRouter<? super T>> domainAcceptors) {
        this.domainAcceptors = domainAcceptors;
    }

    public void changeTo(Emitter<T> recursiveEmitter) {
        this.domainAcceptors = recursiveEmitter.domainAcceptors;
    }

    public void emit(T domainObj) {
        for (MethodRouter<? super T> methodRouter : domainAcceptors) {
            methodRouter.acceptDomain(domainObj);
        }
    }

    /********** Static Utils **********/

    /**
     * returns an emitter that does nothing upon receiving DomainObjs to emit
     */
    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new HashSet<>());
    }
}
