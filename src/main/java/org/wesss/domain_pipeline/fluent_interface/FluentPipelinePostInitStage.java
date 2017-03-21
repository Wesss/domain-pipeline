package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.DomainPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

public class FluentPipelinePostInitStage {

    private OneTimeUseToken useToken;

    public FluentPipelinePostInitStage() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj> FluentPipelinePostProducerStage
            startingWith(Producer<T> producer) {
        Objects.requireNonNull(producer);
        useToken.use();

        ProducerNode<T> producerNode= new ProducerNode<>(producer);
        DomainPipelineCompiler compiler = new DomainPipelineCompiler(producerNode);

        return new FluentPipelinePostProducerStage(compiler, producerNode);
    }
}
