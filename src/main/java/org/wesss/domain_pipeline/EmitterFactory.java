package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.routing.DomainAcceptorAnalyzer;
import org.wesss.domain_pipeline.routing.PostAnalysisDomainAcceptor;
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
    getEmitter(Set<DomainAcceptor<? super T>> domainAcceptors) {
        Set<PostAnalysisDomainAcceptor<? super T>> analyzedDomainAcceptors = new HashSet<>();

        for (DomainAcceptor<? super T> domainAcceptor : domainAcceptors) {
            PostAnalysisDomainAcceptor<? super T> analyzedDomainAcceptor =
                    DomainAcceptorAnalyzer.analyzeDomainAcceptor(domainAcceptor);

            analyzedDomainAcceptors.add(analyzedDomainAcceptor);
        }

        return new Emitter<>(analyzedDomainAcceptors);
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new HashSet<>());
    }
}
