package org.wesss.domain_pipeline.fluent_interface;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.FluentPipelineCompiler;
import org.wesss.domain_pipeline.node_wrappers.*;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;
import org.wesss.general_utils.language.Reference;

import java.util.Objects;

/**
 * The building stage right after a producer is given
 *
 * @param <T> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentPipelineAddWorkerStage<P extends DomainPasserNode<T>, T extends DomainObj> {

    private FluentPipelineCompiler compiler;
    private P passerNode;
    private OneTimeUseToken useToken;

    FluentPipelineAddWorkerStage(FluentPipelineCompiler compiler,
                                 P passerNode) {
        this.compiler = compiler;
        this.passerNode = passerNode;
        useToken = new OneTimeUseToken();
    }

    /**
     * @param <V> the domain type being emitted by given translator
     */
    public <V extends DomainObj> FluentPipelineAddWorkerStage<TranslatorNode<? super T, V>, V>
    thenTranslatedBy(Translator<? super T, V> translator) {
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

        return new FluentPipelineFinalizeStage(consumerNode, compiler);
    }

    /**
     * Saves the last added pipelineworker in the given reference. This should only be called internally.
     */
    public FluentPipelineAddWorkerStage<P, T> savingNodeIn(Reference<P> producerReference) {
        producerReference.setReference(passerNode);
        return this;
    }
}
