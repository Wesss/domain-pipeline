package org.wesss.domain_pipeline.workers.swappable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.swapper.TranslatorSwapper;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Translator;

/**
 * A Translator that can swap out with other translators arbitrarily
 */
public abstract class SwappableTranslator<T extends DomainObj, V extends DomainObj> extends Translator<T, V> {

    protected TranslatorSwapper<T, V> swapper;

    /**
     * @param acceptedClazz the class that is accepted by this consumer
     */
    public SwappableTranslator(Class<T> acceptedClazz) {
        super(acceptedClazz);
    }

    public void initSwapper(TranslatorSwapper<T, V> swapper) {
        this.swapper = swapper;
    }
}
