package org.wesss.domain_pipeline.compilers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.EmitterFactory;
import org.wesss.domain_pipeline.compilers.Compilation;
import org.wesss.domain_pipeline.compilers.PipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.composable.ComposedConsumer;
import org.wesss.general_utils.collection.ArrayUtils;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * @param <T> the domain type that will be consumed by the consumer compiled from this compiler
 */
public class FluentConsumerCompiler<T extends DomainObj> extends PipelineCompiler {

    private DomainAcceptorNode<T> rootNode;

    public FluentConsumerCompiler(DomainAcceptorNode<T> rootNode) {
        this.rootNode = rootNode;
    }

    public Consumer<T> compile() {
        rootNode.build(this);

        Emitter<T> emitterToRootNode = EmitterFactory.getEmitter(
                ArrayUtils.asSet(rootNode.getDomainAcceptor())
        );
        return new ComposedConsumer<>(rootNode, emitterToRootNode);
    }

    @Override
    public <V extends DomainObj> void visit(ProducerNode<V> producerNode) {
        throw new IllegalUseException("Producer cannot compose a consumer");
    }

    @Override
    public <V extends DomainObj, W extends DomainObj> void visit(TranslatorNode<V, W> translatorNode) {
        Compilation.hookUpPasserAndAcceptorNodes(translatorNode);
        super.visit(translatorNode);
    }

    @Override
    public <V extends DomainObj> void visit(ConsumerNode<V> consumerNode) {
        super.visit(consumerNode);
    }
}
