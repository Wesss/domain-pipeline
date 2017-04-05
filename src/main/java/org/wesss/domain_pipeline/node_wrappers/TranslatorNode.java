package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.PipelineWalker;
import org.wesss.domain_pipeline.routing.Emitter;
import org.wesss.domain_pipeline.routing.MethodRouter;
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
        this.methodRouter = MethodRouter.getStubMethodRouter();
        this.recursiveEmitter = Emitter.getStubEmitter();
        this.translator = translator;
        this.emitter = Emitter.getStubEmitter();
    }

    @Override
    public MethodRouter<T> getMethodRouter() {
        return methodRouter;
    }

    @Override
    public void setMethodRouter(MethodRouter<T> methodRouter) {
        this.methodRouter.changeTo(methodRouter);
    }

    @Override
    public Emitter<T> getRecursiveEmitter() {
        return recursiveEmitter;
    }

    @Override
    public void setRecursiveEmitter(Emitter<T> recursiveEmitter) {
        this.recursiveEmitter.changeTo(recursiveEmitter);
    }

    public Translator<T, V> getTranslator() {
        return translator;
    }

    public void setTranslator(Translator<T, V> translator) {
        this.translator = translator;
    }

    @Override
    public Emitter<V> getEmitter() {
        return emitter;
    }

    @Override
    public void setEmitter(Emitter<V> emitter) {
        this.emitter.changeTo(emitter);
    }

    public DomainAcceptorNode<? super V> getChild() {
        return child;
    }

    public void setChild(DomainAcceptorNode<? super V> child) {
        this.child = child;
    }

    @Override
    public DomainAcceptor<T> getDomainAcceptor() {
        return translator;
    }


    @Override
    public DomainPasser<V> getDomainPasser() {
        return translator;
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
    public void buildUsing(PipelineWalker compiler) {
        compiler.visit(this);
    }
}
