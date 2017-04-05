package org.wesss.domain_pipeline.routing.swapper;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compiler.DomainAcceptorInitializingWalker;
import org.wesss.domain_pipeline.compiler.DomainPasserInitializingWalker;
import org.wesss.domain_pipeline.compiler.SwapperInitializingWalker;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.workers.Translator;

public class TranslatorSwapper<T extends DomainObj, V extends DomainObj> {

    private TranslatorNode<T, V> translatorNode;

    public TranslatorSwapper(TranslatorNode<T, V> translatorNode) {
        this.translatorNode = translatorNode;
    }

    public void swapTo(Translator<T, V> translator) {
        translatorNode.setTranslator(translator);
        DomainAcceptorInitializingWalker.initAcceptorNode(translatorNode);
        DomainPasserInitializingWalker.initPasserNode(translatorNode);
        SwapperInitializingWalker.initTranslatorSwapper(translatorNode);
    }
}
