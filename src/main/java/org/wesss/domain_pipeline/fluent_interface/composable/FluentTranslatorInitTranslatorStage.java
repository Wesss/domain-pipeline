package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentTranslatorCompiler;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

import java.util.Objects;

public class FluentTranslatorInitTranslatorStage {

    private OneTimeUseToken useToken;

    public FluentTranslatorInitTranslatorStage() {
        useToken = new OneTimeUseToken();
    }

    public <T extends DomainObj, V extends DomainObj> FluentTranslatorAddTranslatorStage<T, V>
    firstTranslatedBy(Translator<T, V> translator) {
        Objects.requireNonNull(translator);
        useToken.use();

        TranslatorNode<T, V> translatorNode = new TranslatorNode<>(translator);
        FluentTranslatorCompiler<T, V> compiler = new FluentTranslatorCompiler<>(translatorNode, translatorNode);
        return new FluentTranslatorAddTranslatorStage<>(compiler, translatorNode);
    }
}
