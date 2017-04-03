package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.composable.ComposedProducer;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsConsumer;

/**
 * @param <T> the domain type that will be produced by the producer compiled from this compiler
 */
public class FluentProducerCompiler<T extends DomainObj> {

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
        TranslatorAsConsumer<T> fakeConsumer = new TranslatorAsConsumer<>();
        ConsumerNode<DomainObj> fakeConsumerNode = new ConsumerNode<>(fakeConsumer);
        endNode.addChildAcceptor(fakeConsumerNode);

        FluentPipelineCompiler pipelineCompiler = new FluentPipelineCompiler(rootNode);
        pipelineCompiler.compile().start();
        return new ComposedProducer<>(rootNode, fakeConsumer);
    }
}
