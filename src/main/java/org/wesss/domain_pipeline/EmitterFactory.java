package org.wesss.domain_pipeline;

public class EmitterFactory {

    private EmitterFactory() {

    }

    static <T extends DomainObj> Emitter<T> getEmitter() {
        // TODO
        return null;
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        // TODO
        return null;
    }
}
