package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;

import java.util.ArrayList;
import java.util.Arrays;

public class EmitterFactory {

    private EmitterFactory() {

    }

    static <T extends DomainObj> Emitter<T> getEmitter(Consumer<T> consumer) {
        return new Emitter<>(Arrays.asList(consumer));
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new ArrayList<>());
    }
}
