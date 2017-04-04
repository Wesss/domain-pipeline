package org.wesss.domain_pipeline.routing.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> the weakest type that this routing table can accept
 */
public class MethodRoutingTableBuilder<T extends DomainObj> {

    private final List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods;

    public MethodRoutingTableBuilder() {
        domainAcceptorMethods = new ArrayList<>();
    }

    public void insertDomainAcceptorMethod(DomainAcceptorMethod<? extends T> method) {
        Class<? extends DomainObj> methodToInsertClazz = method.getAcceptedClazz();
        int i = 0;

        // until i >= domainAcceptorMethods.size() || isInsertingMethodClass assignable to currentMethodClass
        while (i < domainAcceptorMethods.size() &&
                !domainAcceptorMethods.get(i).getAcceptedClazz().isAssignableFrom(methodToInsertClazz)) {
            i++;
        }

        if (i < domainAcceptorMethods.size() &&
                domainAcceptorMethods.get(i).getAcceptedClazz().equals(methodToInsertClazz)) {
            domainAcceptorMethods.remove(i);
        }
        domainAcceptorMethods.add(i, method);
    }

    public MethodRoutingTable<T> build() {
        return new MethodRoutingTable<>(new ArrayList<>(domainAcceptorMethods));
    }
}
