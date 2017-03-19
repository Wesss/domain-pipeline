package org.wesss.domain_pipeline.emitter.domain;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostAnalysisDomainAcceptor<T extends DomainObj> {

    private final DomainAcceptor<T> domainAcceptor;
    // Sorted in an order such that each method is to the right of all of other
    // methods that accept a subclass of the method's accepted domain objclass.
    // Must contain a method that accepts T.
    private final List<DomainAcceptorMethod> acceptingMethods;

    public PostAnalysisDomainAcceptor(DomainAcceptor<T> domainAcceptor,
                                      List<DomainAcceptorMethod> acceptingMethods) {
        this.domainAcceptor = domainAcceptor;
        this.acceptingMethods = acceptingMethods;
    }

    public DomainAcceptor<T> getDomainAcceptor() {
        return domainAcceptor;
    }

    public Method getMostSpecificAcceptingMethod(Class<? extends DomainObj> clazzToAccept) {
        for (DomainAcceptorMethod acceptingMethod : acceptingMethods) {
            if (acceptingMethod.getAcceptedClazz().isAssignableFrom(clazzToAccept)) {
                return acceptingMethod.getMethod();
            }
        }
        throw new IllegalStateException("No accepting method present");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostAnalysisDomainAcceptor<?> that = (PostAnalysisDomainAcceptor<?>) o;
        return Objects.equals(domainAcceptor, that.domainAcceptor) &&
                Objects.equals(acceptingMethods, that.acceptingMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainAcceptor, acceptingMethods);
    }
}
