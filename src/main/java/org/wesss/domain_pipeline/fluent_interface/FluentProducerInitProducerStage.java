package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.FluentProducerCompiler;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

public class FluentProducerInitProducerStage {

    private OneTimeUseToken useToken;

    public FluentProducerInitProducerStage() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj> FluentProducerAddTranslatorsStage<T>
    startingWith(Producer<T> producer) {
        Objects.requireNonNull(producer);
        useToken.use();

        ProducerNode<T> producerNode = new ProducerNode<>(producer);
        FluentProducerCompiler<T> compiler = new FluentProducerCompiler<>(producerNode, producerNode);
        return new FluentProducerAddTranslatorsStage<>(compiler, producerNode);
    }
}
