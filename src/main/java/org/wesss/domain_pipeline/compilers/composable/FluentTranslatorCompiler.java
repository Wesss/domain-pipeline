package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.compilers.Compilation;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.composable.ComposedTranslator;
import org.wesss.general_utils.collection.ArrayUtils;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * @param <T> the domain type that will be produced by the producer compiled from this compiler
 */
public class FluentTranslatorCompiler<T extends DomainObj, V extends DomainObj> extends PipelineCompiler {

    private DomainAcceptorNode<T> rootNode;
    private DomainPasserNode<V> endNode;

    public FluentTranslatorCompiler(DomainAcceptorNode<T> rootNode, DomainPasserNode<V> endNode) {
        this.rootNode = rootNode;
        this.endNode = endNode;
    }

    public <W extends DomainObj> FluentTranslatorCompiler<T, W> setEndNode(DomainPasserNode<W> endNode) {
        return new FluentTranslatorCompiler<>(
                rootNode,
                endNode
        );
    }

    public Translator<T, V> compile() {
        rootNode.build(this);

        Emitter<T> emitterToRootNode = EmitterFactory.getEmitter(
                ArrayUtils.asSet(rootNode.getDomainAcceptor())
        );
        return new ComposedTranslator<>(rootNode, emitterToRootNode, endNode);
    }

    @Override
    public <W extends DomainObj> void visit(ProducerNode<W> producerNode) {
        throw new IllegalUseException("Producer cannot compose a Translator");
    }

    @Override
    public <W extends DomainObj, X extends DomainObj> void visit(TranslatorNode<W, X> translatorNode) {
        // stop compilation if we reach the end of the pipeline
        if (!translatorNode.equals(endNode)) {
            Compilation.hookUpPasserAndAcceptorNodes(translatorNode);
            super.visit(translatorNode);
        }
    }

    @Override
    public <W extends DomainObj> void visit(ConsumerNode<W> consumerNode) {
        throw new IllegalUseException("Consumer cannot compose a Translator");
    }
}
