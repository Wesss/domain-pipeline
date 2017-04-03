package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.DomainPasser;
import org.wesss.general_utils.annotations.Nullable;
import org.wesss.general_utils.exceptions.IllegalUseException;

/**
 * A Translator that simply re-emit the domain objects it accepts, disguised as a consumer
 */
public class TranslatorAsConsumer<T extends DomainObj> extends Consumer<DomainObj> implements DomainPasser<T> {

    @Nullable
    private Emitter<T> emitter;

    public TranslatorAsConsumer() {
        super(DomainObj.class);
        emitter = null;
    }

    @Override
    public void acceptDomain(DomainObj domainObj) {
        if (emitter == null) {
            throw new IllegalUseException("Emitter has not been initialized");
        }
        emitter.emit((T) domainObj);
    }

    @Override
    public void initPasser(Emitter emitter) {
        this.emitter = emitter;
    }
}
