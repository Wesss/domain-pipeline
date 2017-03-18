package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;

public abstract class Generator<T extends DomainObj> {

    protected Emitter<T> emitter;
    private boolean isInitialized;

    public Generator() {
        this.emitter = Emitter.getStubEmitter();
        isInitialized = false;
    }

    public void init(Emitter<T> emitter) {
        this.emitter = emitter;
        isInitialized = true;
    }

    public void start() {
        if (!isInitialized) {
            throw new IllegalStateException("Generator must be initialized before being started");
        }

        Thread thread = new Thread(this::run);
        thread.start();
    }

    /**
     * Begin the generation of domain objects.
     * <p>
     * This method is called via starting a new thread pointing to this method, allowing
     * this method to block or loop until a Thread Interrupt.
     */
    protected abstract void run();
}
