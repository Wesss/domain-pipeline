package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.PipelineWalker;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;

public class ProducerNode<T extends DomainObj> implements DomainPasserNode<T> {

    private Producer<T> producer;
    private Emitter<T> emitter;
    private DomainAcceptorNode<? super T> child;

    public ProducerNode(Producer<T> producer) {
        this.producer = producer;
        this.emitter = Emitter.getStubEmitter();
    }

    public void start() {
        producer.start();
    }

    public Producer<T> getProducer() {
        return producer;
    }

    public void setProducer(Producer<T> producer) {
        this.producer = producer;
    }

    @Override
    public Emitter<T> getEmitter() {
        return emitter;
    }

    @Override
    public void setEmitter(Emitter<T> emitter) {
        this.emitter.changeTo(emitter);
    }

    public DomainAcceptorNode<? super T> getChild() {
        return child;
    }

    public void setChild(DomainAcceptorNode<? super T> child) {
        this.child = child;
    }

    @Override
    public DomainPasser<T> getDomainPasser() {
        return producer;
    }

    @Override
    public void addChildAcceptor(DomainAcceptorNode<? super T> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public Set<DomainAcceptorNode<? super T>> getChildrenAcceptors() {
        return ArrayUtils.asSet(child);
    }

    @Override
    public void buildUsing(PipelineWalker compiler) {
        compiler.visit(this);
    }
}
