package org.wesss.domain_pipeline;

public class EmitterFactory {

    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>();
    }
}
