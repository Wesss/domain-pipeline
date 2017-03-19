package org.wesss.domain_pipeline.builder;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;

public class DomainPipelineBuilder {

    public DomainPipelineBuilder() {

    }

    public <T extends DomainObj> DomainPipelineBuilderPostProducerStage
            startingWith(Producer<T> producer) {
        ProducerNode<T> producerNode= new ProducerNode<>(producer);
        DomainPipelineCompiler compiler = new DomainPipelineCompiler(producerNode);

        return new DomainPipelineBuilderPostProducerStage(compiler, producerNode);
    }
}
