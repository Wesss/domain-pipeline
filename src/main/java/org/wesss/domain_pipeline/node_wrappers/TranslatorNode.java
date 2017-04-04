package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;

public class TranslatorNode<T extends DomainObj, V extends DomainObj>
        implements DomainAcceptorNode<T>, DomainPasserNode<V> {

    private MethodRouter<T> methodRouter;
    private Emitter<T> recursiveEmitter;
    private Translator<T, V> translator;
    private Emitter<V> emitter;
    private DomainAcceptorNode<? super V> child;

    public TranslatorNode(Translator<T, V> translator) {
        this.translator = translator;
    }

    @Override
    public DomainAcceptor<T> getDomainAcceptor() {
        return translator;
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
    public DomainPasser<V> getDomainPasser() {
        return translator;
    }

    @Override
    public void initEmitter(Emitter<V> emitter) {
        this.emitter = emitter;
    }

    @Override
    public Emitter<V> getEmitter() {
        return emitter;
    }

    @Override
    public void addChildAcceptor(DomainAcceptorNode<? super V> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public Set<DomainAcceptorNode<? super V>> getChildrenAcceptors() {
        return ArrayUtils.asSet(child);
    }

    @Override
    public void build(PipelineCompiler compiler) {
        compiler.visit(this);
    }
}
