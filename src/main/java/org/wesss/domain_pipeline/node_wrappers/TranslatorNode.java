package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.collection.ArrayUtils;

import java.util.Set;

public class TranslatorNode<T extends DomainObj, V extends DomainObj>
        implements DomainTranslatorNode<T, V> {

    private Translator<T, V> translator;
    private DomainAcceptorNode<V> child;

    public TranslatorNode(Translator<T, V> translator) {
        this.translator = translator;
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
    public void addChildAcceptor(DomainAcceptorNode<V> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public Set<DomainAcceptorNode<V>> getChildrenAcceptors() {
        return ArrayUtils.asSet(child);
    }

    @Override
    public void build(PipelineCompiler compiler) {
        compiler.visit(this);
    }
}
