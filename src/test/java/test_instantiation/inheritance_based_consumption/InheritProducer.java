package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.workers.Producer;

public class InheritProducer extends Producer<DomainObjRoot> {

    public InheritProducer() {

    }

    @Override
    protected void run() {

    }

    public void emitRoot() {
        emitter.emit(new DomainObjRoot());
    }

    public void emitLeaf1() {
        emitter.emit(new DomainObjLeaf1());
    }

    public void emitLeaf1_1() {
        emitter.emit(new DomainObjLeaf1_1());
    }

    public void emitLeaf2() {
        emitter.emit(new DomainObjLeaf2());
    }
}
