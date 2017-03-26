package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;

public class ProducerNode<T extends DomainObj> implements DomainPasserNode<T> {

    private Producer<T> producer;
    private DomainAcceptorNode<T> child;

    public ProducerNode(Producer<T> producer) {
        this.producer = producer;
    }

    public void start() {
        producer.start();
    }

    @Override
    public DomainPasser<T> getDomainPasser() {
        return producer;
    }

    @Override
    public void addChildAcceptor(DomainAcceptorNode<T> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public Set<DomainAcceptorNode<T>> getChildrenAcceptors() {
        return ArrayUtils.asSet(child);
    }

    @Override
    public void build(PipelineCompiler compiler) {
        compiler.visit(this);
    }
}
