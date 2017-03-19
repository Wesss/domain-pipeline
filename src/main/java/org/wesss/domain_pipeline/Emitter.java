package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.general_utils.reflection.RefectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    private final Set<DomainAcceptor<T>> domainAcceptors;
    // A complete map of every possible domain acceptor/domain obj class combo to the accepting method
    private final Map<MethodDeterminer<T, ? extends T>, Method> methodDeterminerToMethod;

    public Emitter(Set<DomainAcceptor<T>> domainAcceptors,
                   Map<MethodDeterminer<T, ? extends T>, Method> methodDeterminerToMethod) {
        this.domainAcceptors = domainAcceptors;
        this.methodDeterminerToMethod = methodDeterminerToMethod;
    }

    public void emit(T domainObj) {
        for (DomainAcceptor<T> domainAcceptor : domainAcceptors) {
            MethodDeterminer<T, ? extends T> methodDeterminer =
                    new MethodDeterminer(domainAcceptor, domainObj.getClass());

            Method acceptMethod = methodDeterminerToMethod.get(methodDeterminer);

            RefectionUtils.invokeRethrowingInRuntimeException(acceptMethod, domainAcceptor, domainObj);
        }
    }

    /********** Emitter domain of work **********/

    static class MethodDeterminer<U extends DomainObj, V extends U> {
        private final DomainAcceptor<U> domainAcceptor;
        private final Class<V> domainObjClazz;

        public MethodDeterminer(DomainAcceptor<U> domainAcceptor, Class<V> domainObjClazz) {
            this.domainAcceptor = domainAcceptor;
            this.domainObjClazz = domainObjClazz;
        }

        public DomainAcceptor<U> getDomainAcceptor() {
            return domainAcceptor;
        }

        public Class<? extends U> getDomainObjClazz() {
            return domainObjClazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodDeterminer that = (MethodDeterminer) o;
            return Objects.equals(domainAcceptor, that.domainAcceptor) &&
                    Objects.equals(domainObjClazz, that.domainObjClazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(domainAcceptor, domainObjClazz);
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
