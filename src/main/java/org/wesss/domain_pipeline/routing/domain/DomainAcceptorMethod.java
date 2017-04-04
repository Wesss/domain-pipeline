package org.wesss.domain_pipeline.routing.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.lang.reflect.Method;
import java.util.Objects;

import static org.wesss.domain_pipeline.workers.DomainAcceptor.ACCEPT_DOMAIN_METHOD_NAME;

/**
 * Represents a method that can accept a domain obj
 *
 * @param <T> the weakest type that can be acceted by the given method
 */
public class DomainAcceptorMethod<T extends DomainObj> {

    private final Class<T> acceptedClazz;
    private final Method method;

    public DomainAcceptorMethod(Class<T> acceptedClazz, Method method) {
        this.acceptedClazz = acceptedClazz;
        this.method = method;
    }

    public Class<T> getAcceptedClazz() {
        return acceptedClazz;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainAcceptorMethod<?> that = (DomainAcceptorMethod<?>) o;
        return Objects.equals(acceptedClazz, that.acceptedClazz) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceptedClazz, method);
    }

    @Override
    public String toString() {
        return "DomainAcceptorMethod{" +
                "acceptedClazz=" + acceptedClazz +
                ", method=" + method +
                '}';
    }

    /********** Static Utils **********/

    /**
     * Returns true iff given method is the default acceptDomain(T domainObj) method that
     * can still accept domain methods despite being unnanotated
     */
    public static boolean isUnannotatedAcceptDomainMethod(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        return method.getName().equals(ACCEPT_DOMAIN_METHOD_NAME) &&
                parameterTypes.length == 1 &&
                // this method after generic erasure will always have the parameter of type DomainObj.class
                parameterTypes[0].equals(DomainObj.class);
    }
}
