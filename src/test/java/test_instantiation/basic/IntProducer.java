package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Producer;

public class IntProducer extends Producer<IntDomainObj> {

    private int runCalls;

    public IntProducer() {
        runCalls = 0;
    }

    @Override
    protected void run() {
        runCalls++;
    }

    public int getRunCalls() {
        return runCalls;
    }

    public void emitDomainObject(int id) {
        emitter.emit(new IntDomainObj(id));
    }
}
