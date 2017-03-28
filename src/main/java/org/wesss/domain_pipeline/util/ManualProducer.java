package org.wesss.domain_pipeline.util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Producer;

/**
 * A Producer that must manually be passed domain objs to emit.
 * If not started, emit calls are ignored
 */
public class ManualProducer<T extends DomainObj> extends Producer<T> {
    private boolean isRunning;

    public ManualProducer() {
        isRunning = false;
    }

    @Override
    public void start() {
        super.start();
        isRunning = true;
    }

    @Override
    protected void run() {

    }

    public void emit(T domainObj) {
        if (isRunning) {
            emitter.emit(domainObj);
        }
    }
}
