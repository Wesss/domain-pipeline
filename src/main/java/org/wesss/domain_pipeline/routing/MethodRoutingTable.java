package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @param <T> the weakest type that this routing table can accept
 */
public class MethodRoutingTable<T extends DomainObj> {

    // Sorted in an order such that each method is to the right of all of other
    // methods that accept a subclass of the method's accepted domain objclass.
    // Must contain a method that accepts T.
    private final List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods;

    public MethodRoutingTable(List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods) {
        this.domainAcceptorMethods = domainAcceptorMethods;
    }

    public Method getMethodToAccept(Class<? extends T> clazzToAccept) {
        for (DomainAcceptorMethod acceptingMethod : domainAcceptorMethods) {
            if (acceptingMethod.getAcceptedClazz().isAssignableFrom(clazzToAccept)) {
                return acceptingMethod.getMethod();
            }
        }
        throw new IllegalStateException("No accepting method present");
    }
}