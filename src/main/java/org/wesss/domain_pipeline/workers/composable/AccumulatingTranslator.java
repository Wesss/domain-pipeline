package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.interdomain.ListDomain;
import org.wesss.domain_pipeline.workers.Translator;

import java.util.ArrayList;
import java.util.List;

/**
 * A Translator that accumulates passed in domain objs until notified to pass on all given DomainObjs at once.
 */
public class AccumulatingTranslator<T extends DomainObj> extends Translator<T, ListDomain<T>> {

    private List<T> receivedDomainObjs;

    public AccumulatingTranslator(Class<T> acceptedClazz) {
        super(acceptedClazz);
        this.receivedDomainObjs = new ArrayList<>();
    }

    @Override
    public void acceptDomain(T domainObj) {
        receivedDomainObjs.add(domainObj);
    }

    /**
     * emits all received domain objs as a list, ordered by time received.
     */
    public void emitAllReceivedDomainObjs() {
        emitter.emit(new ListDomain<>(receivedDomainObjs));
        receivedDomainObjs.clear();
    }
}
