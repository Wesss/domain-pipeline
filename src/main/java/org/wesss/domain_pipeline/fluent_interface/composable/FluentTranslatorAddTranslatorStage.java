package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentProducerCompiler;
import org.wesss.domain_pipeline.compilers.composable.FluentTranslatorCompiler;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

/**
 * @param <T> the domain type accepted by the building pipeline
 * @param <V> the domain type currently being emitted at the end of the building pipeline
 */
public class FluentTranslatorAddTranslatorStage<T extends DomainObj, V extends DomainObj> {

    private FluentTranslatorCompiler<T, V> compiler;
    private DomainPasserNode<V> passerNode;
    private OneTimeUseToken useToken;

    public FluentTranslatorAddTranslatorStage(FluentTranslatorCompiler<T, V> compiler,
                                              DomainPasserNode<V> passerNode) {
        this.compiler = compiler;
        this.passerNode = passerNode;
        useToken = new OneTimeUseToken();
    }

    public <W extends DomainObj> FluentTranslatorAddTranslatorStage<T, W>
    thenTranslatedBy(Translator<V, W> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<V, W> translatorNode = new TranslatorNode<>(translator);
        passerNode.addChildAcceptor(translatorNode);
        FluentTranslatorCompiler<T, W> newCompiler = compiler.setEndNode(translatorNode);
        return new FluentTranslatorAddTranslatorStage<>(newCompiler, translatorNode);
    }

    public Translator<T, V> build() {
        return compiler.compile();
    }
}
