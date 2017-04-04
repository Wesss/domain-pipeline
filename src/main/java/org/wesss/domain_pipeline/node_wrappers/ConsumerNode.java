package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

public class ConsumerNode<T extends DomainObj> implements DomainAcceptorNode<T> {

    // TODO code cleanup here (init requirement, immutability, etc)

    private MethodRouter<T> methodRouter;
    private final Consumer<T> consumer;
    private Emitter<T> recursiveEmitter;

    public ConsumerNode(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public DomainAcceptor<T> getDomainAcceptor() {
        return consumer;
    }

    @Override
    public void initMethodRouter(MethodRouter<T> methodRouter) {
        this.methodRouter = methodRouter;
    }

    @Override
    public MethodRouter<T> getMethodRouter() {
        return methodRouter;
    }

    @Override
    public void initRecursiveEmitter(Emitter<T> emitter) {
        this.recursiveEmitter = emitter;
    }

    @Override
    public Emitter<T> getRecursiveEmitter() {
        return recursiveEmitter;
    }

    @Override
    public void build(PipelineCompiler compiler) {
        compiler.visit(this);
    }
}
