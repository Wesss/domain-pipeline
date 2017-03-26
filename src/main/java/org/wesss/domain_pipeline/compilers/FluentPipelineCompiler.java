package org.wesss.domain_pipeline.compilers;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;

public class FluentPipelineCompiler extends PipelineCompiler{

    private ProducerNode<?> rootNode;

    public FluentPipelineCompiler(ProducerNode<?> rootNode) {
        this.rootNode = rootNode;
    }

    public DomainPipeline compile() {
        rootNode.build(this);
        return new DomainPipeline(rootNode);
    }

    @Override
    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        Compilation.hookUpPasserAndAcceptorNodes(producerNode);
        super.visit(producerNode);
    }

    @Override
    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        Compilation.hookUpPasserAndAcceptorNodes(translatorNode);
        super.visit(translatorNode);
    }

    @Override
    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {
        super.visit(consumerNode);
    }
}
