package org.wesss.domain_pipeline.emitter;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.emitter.domain.PostAnalysisDomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.general_utils.reflection.RefectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    private final Set<PostAnalysisDomainAcceptor<T>> domainAcceptors;

    public Emitter(Set<PostAnalysisDomainAcceptor<T>> domainAcceptors) {
        this.domainAcceptors = domainAcceptors;
    }

    public void emit(T domainObj) {
        for (PostAnalysisDomainAcceptor analyzedDomainAcceptor: domainAcceptors) {
            Method acceptingMethod =
                    analyzedDomainAcceptor.getMostSpecificAcceptingMethod(domainObj.getClass());

            RefectionUtils.invokeRethrowingInRuntimeException(
                    acceptingMethod,
                    analyzedDomainAcceptor.getDomainAcceptor(),
                    domainObj);
        }
    }

    /********** Static Utils **********/

    /**
     * returns an emitter that does nothing upon receiving DomainObjs to emit
     */
    public static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return EmitterFactory.getStubEmitter();
    }
}
