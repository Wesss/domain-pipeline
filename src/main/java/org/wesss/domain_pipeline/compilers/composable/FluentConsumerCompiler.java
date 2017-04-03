package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.DomainAcceptorNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.composable.ComposedConsumer;

/**
 * @param <T> the domain type that will be consumed by the consumer compiled from this compiler
 */
public class FluentConsumerCompiler<T extends DomainObj> {

    private DomainAcceptorNode<T> rootNode;

    public FluentConsumerCompiler(DomainAcceptorNode<T> rootNode) {
        this.rootNode = rootNode;
    }

    public Consumer<T> compile() {
        Class<T> acceptedClass = rootNode.getDomainAcceptor().getAcceptedClass();
        TranslatorAsProducer<T> fakeProducer = new TranslatorAsProducer<>(acceptedClass);
        ProducerNode<T> fakeProducerNode = new ProducerNode<>(fakeProducer);
        fakeProducerNode.addChildAcceptor(rootNode);

        FluentPipelineCompiler pipelineCompiler = new FluentPipelineCompiler(fakeProducerNode);
        pipelineCompiler.compile().start();

        return new ComposedConsumer<>(fakeProducer, acceptedClass);
    }
}
