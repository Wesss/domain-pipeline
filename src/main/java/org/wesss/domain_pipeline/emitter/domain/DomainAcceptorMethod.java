package org.wesss.domain_pipeline.emitter.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a method that can accept a domain obj
 */
public class DomainAcceptorMethod<T extends DomainObj> {

    private final Class<? extends T> acceptedClazz;
    private final Method method;

    public DomainAcceptorMethod(Class<? extends T> acceptedClazz, Method method) {
        this.acceptedClazz = acceptedClazz;
        this.method = method;
    }

    public Class<? extends T> getAcceptedClazz() {
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
}
