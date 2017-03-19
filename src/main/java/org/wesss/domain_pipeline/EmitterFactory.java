package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.util.ArrayList;
import java.util.Arrays;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns an emitter that will pass objects given to it to given domainAcceptors
     */
    public static <T extends DomainObj> Emitter<T>
            getEmitter(DomainEmitter<T> domainEmitter, DomainAcceptor<T> domainAcceptor) {
        return new Emitter<>(Arrays.asList(domainAcceptor));
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new ArrayList<>());
    }
}
