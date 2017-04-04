package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.domain.DomainAcceptorAnalyzer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

import java.util.HashSet;
import java.util.Set;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns an emitter that will pass objects given to it to given domainAcceptors
     */
    public static <T extends DomainObj> Emitter<T>
    getEmitter(Set<MethodRouter<? super T>> methodRouters) {
        return new Emitter<>(methodRouters);
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new HashSet<>());
    }
}
