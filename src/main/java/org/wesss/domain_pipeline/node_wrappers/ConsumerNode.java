package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.PipelineWalker;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

public class ConsumerNode<T extends DomainObj> implements DomainAcceptorNode<T> {

    private MethodRouter<T> methodRouter;
    private Emitter<T> recursiveEmitter;
    private Consumer<T> consumer;

    public ConsumerNode(Consumer<T> consumer) {
        this.methodRouter = MethodRouter.getStubMethodRouter();
        this.consumer = consumer;
        this.recursiveEmitter = Emitter.getStubEmitter();
    }

    @Override
    public MethodRouter<T> getMethodRouter() {
        return methodRouter;
    }

    @Override
    public void setMethodRouter(MethodRouter<T> methodRouter) {
        this.methodRouter.changeTo(methodRouter);
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public Emitter<T> getRecursiveEmitter() {
        return recursiveEmitter;
    }

    @Override
    public void setRecursiveEmitter(Emitter<T> recursiveEmitter) {
        this.recursiveEmitter.changeTo(recursiveEmitter);
    }

    @Override
    public DomainAcceptor<T> getDomainAcceptor() {
        return consumer;
    }

    @Override
    public void buildUsing(PipelineWalker compiler) {
        compiler.visit(this);
    }
}
