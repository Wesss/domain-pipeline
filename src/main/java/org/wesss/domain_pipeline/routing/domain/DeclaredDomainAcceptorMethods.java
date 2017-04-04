package org.wesss.domain_pipeline.routing.domain;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.general_utils.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @param <T> the weakest type that can be accepted by these methods
 */
public class DeclaredDomainAcceptorMethods<T extends DomainObj> {

    // null means method not present or has been overridden
    @Nullable
    private final DomainAcceptorMethod<T> unannotatedMethod;
    private final List<DomainAcceptorMethod<? extends T>> annotatedMethods;

    public DeclaredDomainAcceptorMethods(DomainAcceptorMethod<T> unannotatedMethod,
                                         List<DomainAcceptorMethod<? extends T>> annotatedMethods) {
        this.unannotatedMethod = unannotatedMethod;
        this.annotatedMethods = annotatedMethods;
    }

    public boolean isUnannotatedAcceptorMethodPresent() {
        return unannotatedMethod != null;
    }

    public DomainAcceptorMethod<T> getUnannotatedMethod() {
        return unannotatedMethod;
    }

    public List<DomainAcceptorMethod<? extends T>> getAnnotatedMethods() {
        return new ArrayList<>(annotatedMethods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclaredDomainAcceptorMethods<?> that = (DeclaredDomainAcceptorMethods<?>) o;
        return Objects.equals(unannotatedMethod, that.unannotatedMethod) &&
                Objects.equals(annotatedMethods, that.annotatedMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unannotatedMethod, annotatedMethods);
    }
}
