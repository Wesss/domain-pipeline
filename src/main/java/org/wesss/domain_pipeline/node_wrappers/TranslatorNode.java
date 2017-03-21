package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.collection.ArrayUtils;

public class TranslatorNode<T extends DomainObj, V extends DomainObj>
        implements DomainAcceptorNode<T>, DomainEmitterNode<V> {

    // TODO perhaps turn this into emitter/acceptor components to not repeat child logic

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
    public void addChildAcceptor(DomainAcceptorNode<V> acceptorNode) {
        this.child = acceptorNode;
    }

    @Override
    public void build() {
        Emitter<V> emitter =
                EmitterFactory.getEmitter(translator, ArrayUtils.asSet(child.getDomainAcceptor()));
        translator.init(emitter);
        child.build();
    }
}
