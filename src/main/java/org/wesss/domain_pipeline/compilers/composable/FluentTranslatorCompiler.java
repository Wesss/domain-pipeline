package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsConsumer;
import org.wesss.domain_pipeline.workers.composable.TranslatorAsProducer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.composable.ComposedTranslator;

/**
 * @param <T> the domain type that will be produced by the producer compiled from this compiler
 */
public class FluentTranslatorCompiler<T extends DomainObj, V extends DomainObj> {

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
        Class<T> acceptedClass = rootNode.getDomainAcceptor().getAcceptedClass();
        TranslatorAsProducer<T> fakeProducer = new TranslatorAsProducer<>(acceptedClass);
        ProducerNode<T> fakeProducerNode = new ProducerNode<>(fakeProducer);
        fakeProducerNode.addChildAcceptor(rootNode);

        TranslatorAsConsumer<V> fakeConsumer = new TranslatorAsConsumer<>();
        ConsumerNode<DomainObj> fakeConsumerNode = new ConsumerNode<>(fakeConsumer);
        endNode.addChildAcceptor(fakeConsumerNode);

        FluentPipelineCompiler pipelineCompiler = new FluentPipelineCompiler(fakeProducerNode);
        pipelineCompiler.compile().start();

        return new ComposedTranslator<>(fakeProducer, fakeConsumer, acceptedClass);
    }
}
