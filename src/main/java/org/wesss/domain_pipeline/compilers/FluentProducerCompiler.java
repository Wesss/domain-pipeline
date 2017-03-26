package org.wesss.domain_pipeline.compilers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * @param <T> the domain type being produced by the producer to visit
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

        return new Producer<T>() {
            @Override
            public void initPasser(Emitter<T> emitter) {
                super.initPasser(emitter);
                endNode.getDomainPasser().initPasser(emitter);
            }

            @Override
            protected void run() {
                rootNode.start();
            }
        };
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
