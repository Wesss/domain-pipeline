package test_instantiation;

import org.wesss.domain_pipeline.workers.Producer;

public class TestProducer extends Producer<TestIntDomainObj> {

    private int runCalls;

    public TestProducer() {
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
        emitter.emit(new TestIntDomainObj(id));
    }
}
