package org.wesss.domain_pipeline.util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * A Consumer that accumulates passed in domain objs
 */
public class AccumulatingConsumer<T extends DomainObj> extends Consumer<T> {

    private List<T> receivedDomainObjects;

    /**
     * @param acceptedClazz the class that is accepted by this consumer
     */
    public AccumulatingConsumer(Class<T> acceptedClazz) {
        super(acceptedClazz);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(T domainObj) {
        receivedDomainObjects.add(domainObj);
    }

    /**
     * @return a list of received domain objects since last call to this method, in order of being
     * received.
     */
    public List<T> getReceivedDomainObjects() {
        List<T> list = receivedDomainObjects;
        receivedDomainObjects = new ArrayList<>();
        return list;
    }
}
