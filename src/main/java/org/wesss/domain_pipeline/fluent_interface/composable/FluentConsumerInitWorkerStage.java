package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentConsumerCompiler;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

public class FluentConsumerInitWorkerStage {

    private OneTimeUseToken useToken;

    public FluentConsumerInitWorkerStage() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj, V extends DomainObj> FluentConsumerAddWorkersStage<T, V>
    firstTranslatedBy(Translator<T, V> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<T, V> translatorNode = new TranslatorNode<>(translator);
        FluentConsumerCompiler<T> compiler = new FluentConsumerCompiler<>(translatorNode);
        return new FluentConsumerAddWorkersStage<>(compiler, translatorNode);
    }

    public <T extends DomainObj> FluentConsumerFinalizeStage<T>
    firstConsumedBy(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        useToken.use();

        ConsumerNode<T> consumerNode = new ConsumerNode<>(consumer);
        FluentConsumerCompiler<T> compiler = new FluentConsumerCompiler<>(consumerNode);
        return new FluentConsumerFinalizeStage<>(compiler);
    }
}
