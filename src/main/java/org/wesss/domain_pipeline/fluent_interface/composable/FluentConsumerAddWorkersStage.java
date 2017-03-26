package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentConsumerCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

/**
 * @param <T> the domain type accepted by the building pipeline
 * @param <V> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentConsumerAddWorkersStage<T extends DomainObj, V extends DomainObj> {

    private FluentConsumerCompiler<T> compiler;
    private DomainPasserNode<V> passerNode;
    private OneTimeUseToken useToken;

    public FluentConsumerAddWorkersStage(FluentConsumerCompiler<T> compiler, DomainPasserNode<V> passerNode) {
        this.compiler = compiler;
        this.passerNode = passerNode;
        useToken = new OneTimeUseToken();
    }

    public <W extends DomainObj> FluentConsumerAddWorkersStage<T, W>
    thenTranslatedBy(Translator<V, W> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<V, W> translatorNode = new TranslatorNode<>(translator);
        passerNode.addChildAcceptor(translatorNode);
        return new FluentConsumerAddWorkersStage<>(compiler, translatorNode);
    }

    public FluentConsumerFinalizeStage<T>
    thenConsumedBy(Consumer<V> consumer) {
        Objects.requireNonNull(consumer);
        useToken.use();

        ConsumerNode<V> consumerNode = new ConsumerNode<>(consumer);
        passerNode.addChildAcceptor(consumerNode);
        return new FluentConsumerFinalizeStage<>(compiler);
    }
}
