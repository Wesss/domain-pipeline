package org.wesss.domain_pipeline.fluent_interface.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.compilers.composable.FluentConsumerCompiler;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.general_utils.fluentstyle.OneTimeUseToken;

/**
 * @param <T> the domain type accepted by the building consumer
 */
public class FluentConsumerFinalizeStage<T extends DomainObj> {

    private FluentConsumerCompiler compiler;
    private OneTimeUseToken useToken;

    FluentConsumerFinalizeStage(FluentConsumerCompiler<T> compiler) {
        this.compiler = compiler;
        useToken = new OneTimeUseToken();
    }

    public Consumer<T> build() {
        useToken.use();

        return compiler.compile();
    }
}
