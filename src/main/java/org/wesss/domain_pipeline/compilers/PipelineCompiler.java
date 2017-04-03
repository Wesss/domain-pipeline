package org.wesss.domain_pipeline.compilers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * A visitor that visits DomainPasserNodes, preparing them for execution
 */
public abstract class PipelineCompiler {

    // TODO split into interface that only requires compile method, split into AST visitor/walker interface

    public final void visit(DomainPipelineNode node) {
        throw new IllegalUseException("This compiler has not been properly set up to visit nodes of type"
                + node.getClass().getSimpleName());
    }

    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        for (DomainAcceptorNode<? super T> child : producerNode.getChildrenAcceptors()) {
            child.build(this);
        }
    }

    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        for (DomainAcceptorNode<? super V> child : translatorNode.getChildrenAcceptors()) {
            child.build(this);
        }
    }

    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {

    }
}
