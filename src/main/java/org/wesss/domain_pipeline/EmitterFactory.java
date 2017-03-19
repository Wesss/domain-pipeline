package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.util.ArrayList;
import java.util.Arrays;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns
     */
    static <T extends DomainObj, V extends DomainObj> Emitter<T>
            getEmitter(DomainEmitter<T> domainEmitter, DomainAcceptor<V> domainAcceptor) {
        // check types are the same
        Class<T> generatorClazz = domainEmitter.getEmittedDomainClass();
        Class<V> consumerClazz = domainAcceptor.getAcceptedDomainClass();

        if (!generatorClazz.equals(consumerClazz)) {
            throw new IllegalArgumentException("producer and consumer DomainObj type mismatch");
        }

        return (Emitter<T>)new Emitter<>(Arrays.asList(domainAcceptor));
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new ArrayList<>());
    }
}
