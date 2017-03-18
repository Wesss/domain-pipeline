package org.wesss.domain_pipeline;

import java.util.ArrayList;

public class EmitterFactory {

    private EmitterFactory() {

    }

    static <T extends DomainObj> Emitter<T> getEmitter() {
        // TODO
        return null;
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new ArrayList<>());
    }
}
