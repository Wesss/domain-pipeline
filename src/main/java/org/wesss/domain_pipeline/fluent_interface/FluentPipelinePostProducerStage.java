package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

/**
 * The building stage right after a producer is given
 *
 * @param <T> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentPipelinePostProducerStage<T extends DomainObj> {

    private DomainPipelineCompiler compiler;
    private ProducerNode<T> producerNode;
    private OneTimeUseToken useToken;

    public FluentPipelinePostProducerStage(DomainPipelineCompiler compiler,
                                           ProducerNode<T> producerNode) {
        this.compiler = compiler;
        this.producerNode = producerNode;
        useToken = new OneTimeUseToken();
    }

    public FluentPipelinePostConsumerStage thenConsumedBy(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        useToken.use();

        ConsumerNode<T> consumerNode = new ConsumerNode<>(consumer);
        producerNode.addAcceptorNode(consumerNode);

        return new FluentPipelinePostConsumerStage(compiler);
    }
}
