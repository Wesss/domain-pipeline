package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;

public class DomainPipelineBuilder {

    private OneTimeUseToken useToken;

    public DomainPipelineBuilder() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj> DomainPipelineBuilderPostProducerStage
            startingWith(Producer<T> producer) {
        useToken.use();

        ProducerNode<T> producerNode= new ProducerNode<>(producer);
        DomainPipelineCompiler compiler = new DomainPipelineCompiler(producerNode);

        return new DomainPipelineBuilderPostProducerStage(compiler, producerNode);
    }
}
