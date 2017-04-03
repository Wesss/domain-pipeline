package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Translator;

/**
 * Re emits given domain objs only iff they are accepted by a predetermined predicate
 */
public abstract class FilterTranslator<T extends DomainObj> extends Translator<T, T> {

    /**
     * @param acceptedClazz the class type that is accepted by this translator
     */
    public FilterTranslator(Class<T> acceptedClazz) {
        super(acceptedClazz);
    }

    @Override
    public void acceptDomain(T domainObj) {
        if (filter(domainObj)) {
            emitter.emit(domainObj);
        }
    }

    /**
     * @return true iff given domain obj is allowed to be re emitted
     */
    public abstract boolean filter(T domainObj);
}
