package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

/**
 * The building stage right after a producer is given
 *
 * @param <T> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentPipelineAddWorkerStage<T extends DomainObj> {

    private FluentPipelineCompiler compiler;
    private DomainPasserNode<T> passerNode;
    private OneTimeUseToken useToken;

    FluentPipelineAddWorkerStage(FluentPipelineCompiler compiler,
                                 DomainPasserNode<T> passerNode) {
        this.compiler = compiler;
        this.passerNode = passerNode;
        useToken = new OneTimeUseToken();
    }

    public <V extends DomainObj> FluentPipelineAddWorkerStage<V> thenTranslatedBy(Translator<? super T, V> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<? super T, V> translatorNode = new TranslatorNode<>(translator);
        passerNode.addChildAcceptor(translatorNode);

        return new FluentPipelineAddWorkerStage<>(compiler, translatorNode);
    }

    public FluentPipelineFinalizeStage thenConsumedBy(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        useToken.use();

        ConsumerNode<? super T> consumerNode = new ConsumerNode<>(consumer);
        passerNode.addChildAcceptor(consumerNode);

        return new FluentPipelineFinalizeStage(compiler);
    }
}
