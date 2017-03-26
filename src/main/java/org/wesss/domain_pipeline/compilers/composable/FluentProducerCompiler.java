package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.Compilation;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.composable.ComposedProducer;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * @param <T> the domain type that will be produced by the producer compiled from this compiler
 */
public class FluentProducerCompiler<T extends DomainObj> extends PipelineCompiler {

    private ProducerNode<?> rootNode;
    private DomainPasserNode<T> endNode;

    public FluentProducerCompiler(ProducerNode<?> rootNode, DomainPasserNode<T> endNode) {
        this.rootNode = rootNode;
        this.endNode = endNode;
    }

    public <V extends DomainObj> FluentProducerCompiler<V> setEndNode(DomainPasserNode<V> endNode) {
        return new FluentProducerCompiler<>(
                rootNode,
                endNode
        );
    }

    public Producer<T> compile() {
        rootNode.build(this);

        return new ComposedProducer<>(rootNode, endNode);
    }

    @Override
    public <V extends DomainObj> void visit(ProducerNode<V> producerNode) {
        // stop compilation if we reach the end of the pipeline
        if (!producerNode.equals(endNode)) {
            Compilation.hookUpPasserAndAcceptorNodes(producerNode);
            super.visit(producerNode);
        }
    }

    @Override
    public <V extends DomainObj, W extends DomainObj> void visit(TranslatorNode<V, W> translatorNode) {
        // stop compilation if we reach the end of the pipeline
        if (!translatorNode.equals(endNode)) {
            Compilation.hookUpPasserAndAcceptorNodes(translatorNode);
            super.visit(translatorNode);
        }
    }

    @Override
    public <V extends DomainObj> void visit(ConsumerNode<V> consumerNode) {
        throw new IllegalUseException("Consumer cannot compose a producer");
    }
}
