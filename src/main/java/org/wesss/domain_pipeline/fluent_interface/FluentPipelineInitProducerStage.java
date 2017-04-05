package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

public class FluentPipelineInitProducerStage {

    private OneTimeUseToken useToken;

    public FluentPipelineInitProducerStage() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj> FluentPipelineAddWorkerStage<T>
    startingWith(Producer<T> producer) {
        Objects.requireNonNull(producer);
        useToken.use();

        ProducerNode<T> producerNode = new ProducerNode<>(producer);
        FluentPipelineCompiler compiler = new FluentPipelineCompiler(producerNode);

        return new FluentPipelineAddWorkerStage<>(compiler, producerNode);
    }
}
