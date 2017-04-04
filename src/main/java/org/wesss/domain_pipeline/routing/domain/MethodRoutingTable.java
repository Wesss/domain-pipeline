package org.wesss.domain_pipeline.routing.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Represents a routing table that can be used to determine the method that should be called
 * upon receiving any specific domain obj
 *
 * @param <T> the weakest type that this routing table can accept
 */
public class MethodRoutingTable<T extends DomainObj> {

    // Sorted in an order such that each method is to the right of all of other
    // methods that accept a subclass of the method's accepted domain obj class.
    // Must contain a method that accepts T.
    private final List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods;

    public MethodRoutingTable(List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods) {
        this.domainAcceptorMethods = domainAcceptorMethods;
    }

    public Method getMethodToAccept(Class<? extends T> clazzToAccept) {
        for (DomainAcceptorMethod<? extends T> acceptingMethod : domainAcceptorMethods) {
            if (acceptingMethod.getAcceptedClazz().isAssignableFrom(clazzToAccept)) {
                return acceptingMethod.getMethod();
            }
        }
        throw new IllegalStateException("No accepting method present");
    }
}
