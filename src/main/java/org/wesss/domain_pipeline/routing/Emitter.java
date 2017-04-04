package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;

import java.util.Objects;
import java.util.Set;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 *
 * @param <T> the type of emitted domain objs
 */
public class Emitter<T extends DomainObj> {

    private final Set<MethodRouter<? super T>> domainAcceptors;

    public Emitter(Set<MethodRouter<? super T>> domainAcceptors) {
        this.domainAcceptors = domainAcceptors;
    }

    public void emit(T domainObj) {
        for (MethodRouter<? super T> analyzedDomainAcceptor : domainAcceptors) {
            analyzedDomainAcceptor.acceptDomain(domainObj);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emitter<?> emitter = (Emitter<?>) o;
        return Objects.equals(domainAcceptors, emitter.domainAcceptors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainAcceptors);
    }

    @Override
    public String toString() {
        return "Emitter{" +
                "domainAcceptors=" + domainAcceptors +
                '}';
    }

    /********** Static Utils **********/

    /**
     * returns an emitter that does nothing upon receiving DomainObjs to emit
     */
    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return EmitterFactory.getStubEmitter();
    }
}