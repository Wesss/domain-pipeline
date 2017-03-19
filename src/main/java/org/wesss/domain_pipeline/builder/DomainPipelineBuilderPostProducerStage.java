package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

/**
 * The building stage right after a producer is given
 * @param <T> the domain type currently being omitted at the end of the building pipeline
 */
public class DomainPipelineBuilderPostProducerStage<T extends DomainObj> {

    private DomainPipelineCompiler compiler;
    private ProducerNode<T> producerNode;
    private OneTimeUseToken useToken;

    public DomainPipelineBuilderPostProducerStage(DomainPipelineCompiler compiler,
                                                  ProducerNode<T> producerNode) {
        this.compiler = compiler;
        this.producerNode = producerNode;
        useToken = new OneTimeUseToken();
    }

    public DomainPipelineBuilderPostConsumerStage thenConsumedBy(Consumer<T> consumer) {
        useToken.use();

        ConsumerNode<T> consumerNode = new ConsumerNode<>(consumer);
        producerNode.addAcceptorNode(consumerNode);

        return new DomainPipelineBuilderPostConsumerStage(compiler);
    }
}
