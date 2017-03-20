package org.wesss.domain_pipeline.emitter;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.MethodRoutingTable;
import org.wesss.domain_pipeline.routing.PostAnalysisDomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.util.*;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns an emitter that will pass objects given to it to given domainAcceptors
     */
    public static <T extends DomainObj> Emitter<T>
            getEmitter(DomainEmitter<T> domainEmitter, Set<DomainAcceptor<T>> domainAcceptors) {
        Set<PostAnalysisDomainAcceptor<T>> analyzedDomainAcceptors = new HashSet<>();

        for (DomainAcceptor<T> domainAcceptor : domainAcceptors) {
            MethodRoutingTable<T> methodRoutingTable =
                    domainAcceptor.getMethodRoutingTable();

            analyzedDomainAcceptors.add(new PostAnalysisDomainAcceptor<>(
                    domainAcceptor,
                    methodRoutingTable
            ));
        }

        return new Emitter<>(analyzedDomainAcceptors);
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new HashSet<>());
    }
}
