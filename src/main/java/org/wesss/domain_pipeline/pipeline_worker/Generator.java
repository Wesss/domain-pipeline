package org.wesss.domain_pipeline.pipeline_worker;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;

public abstract class Generator<T extends DomainObj> {

    protected Emitter<T> emitter;

    public Generator() {
        this.emitter = null; // TODO make empty emitter
    }

    public void init(Emitter<T> emitter) {
        this.emitter = emitter;
    }

    public void start() {
        // TODO
    }

    /**
     * Begin the generation of domain objects.
     * <p>
     * This method is called via starting a new thread pointing to this method, allowing
     * this method to block or loop until a Thread Interrupt.
     */
    protected abstract void run();
}
