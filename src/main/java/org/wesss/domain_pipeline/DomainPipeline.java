package org.wesss.domain_pipeline;

import org.wesss.domain_pipeline.fluent_interface.FluentPipelineInitProducerStage;
import org.wesss.domain_pipeline.fluent_interface.FluentProducerInitProducerStage;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

/**
 * This class represents the entirety of a domain pipeline that generates domain objects
 * and passes them along until consumption.
 */
public class DomainPipeline {

    // TODO Emit domain objects to consumers that accept weaker domain object types
    // TODO Emit domain objects to multiple consumers

    private final ProducerNode<?> rootNode;

    public DomainPipeline(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * start the execution of the pipeline.
     */
    public void start() {
        rootNode.start();
    }

    /********** Static Interface **********/

    /**
     * @return A fluent-style interface for creating domain pipelines
     */
    public static FluentPipelineInitProducerStage createPipeline() {
        return new FluentPipelineInitProducerStage();
    }

    /**
     * @return A fluent-style interface for creating producers composed of many domain pipeline workers
     */
    public static FluentProducerInitProducerStage createComposedProducer() {
        return new FluentProducerInitProducerStage();
    }
}
