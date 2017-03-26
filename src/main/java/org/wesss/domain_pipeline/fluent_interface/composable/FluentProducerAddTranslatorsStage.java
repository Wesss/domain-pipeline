package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentProducerCompiler;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

/**
 * @param <T> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentProducerAddTranslatorsStage<T extends DomainObj> {

    private FluentProducerCompiler<T> compiler;
    private DomainPasserNode<T> passerNode;
    private OneTimeUseToken useToken;

    public FluentProducerAddTranslatorsStage(FluentProducerCompiler<T> compiler, DomainPasserNode<T> passerNode) {
        this.compiler = compiler;
        this.passerNode = passerNode;
        useToken = new OneTimeUseToken();
    }

    public <V extends DomainObj> FluentProducerAddTranslatorsStage<V>
    thenTranslatedBy(Translator<T, V> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<T, V> translatorNode = new TranslatorNode<>(translator);
        passerNode.addChildAcceptor(translatorNode);
        FluentProducerCompiler<V> newCompiler = compiler.setEndNode(translatorNode);
        return new FluentProducerAddTranslatorsStage<>(newCompiler, translatorNode);
    }

    public Producer<T> build() {
        return compiler.compile();
    }
}
