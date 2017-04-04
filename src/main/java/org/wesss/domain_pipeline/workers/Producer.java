package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.Emitter;

/**
 * @param <T> the type of domain obj produced
 */
public abstract class Producer<T extends DomainObj> implements DomainPasser<T> {

    /**
     * Emits domain objects to the next pipeline worker(s)
     */
    protected Emitter<T> emitter;
    private boolean isInitialized;

    public Producer() {
        this.emitter = Emitter.getStubEmitter();
        isInitialized = false;
    }

    @Override
    public void initPasser(Emitter<T> emitter) {
        this.emitter = emitter;
        isInitialized = true;
    }

    public void start() {
        if (!isInitialized) {
            throw new IllegalStateException("Producer must be initialized before being started");
        }

        Thread thread = new Thread(this::run);
        thread.start();
    }

    /**
     * Begin the generation of domain objects.
     * <p>
     * This method is called via starting a new thread pointing to this method, allowing
     * this method to block independently of other producers.
     * <p>
     * TODO document how to properly handle interrupts so that producers can shut down properly
     */
    protected abstract void run();
}
