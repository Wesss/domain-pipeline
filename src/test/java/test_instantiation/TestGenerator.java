package test_instantiation;

import org.wesss.domain_pipeline.pipeline_worker.Generator;

public class TestGenerator extends Generator<TestIntDomainObj> {

    private int runCalls;

    public TestGenerator() {
        super(TestIntDomainObj.class);
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
