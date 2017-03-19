package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Consumer;

/**
 * The building stage right after a producer is given
 * @param <T> the domain type currently being omitted at the end of the building pipeline
 */
public class DomainPipelineBuilderPostProducerStage<T extends DomainObj> {

    private final DomainPipelineCompiler compiler;
    private final ProducerNode<T> producerNode;

    public DomainPipelineBuilderPostProducerStage(DomainPipelineCompiler compiler,
                                                  ProducerNode<T> producerNode) {
        this.compiler = compiler;
        this.producerNode = producerNode;
    }

    public DomainPipelineBuilderPostConsumerStage thenConsumedBy(Consumer<T> consumer) {
        ConsumerNode<T> consumerNode = new ConsumerNode<>(consumer);
        producerNode.setConsumerNode(consumerNode);

        return new DomainPipelineBuilderPostConsumerStage(compiler);
    }
}
