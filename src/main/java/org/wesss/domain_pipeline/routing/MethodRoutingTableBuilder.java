package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> the weakest type that this routing table can accept
 */
public class MethodRoutingTableBuilder<T extends DomainObj> {

    List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods;

    public MethodRoutingTableBuilder() {
        domainAcceptorMethods = new ArrayList<>();
    }

    public MethodRoutingTableBuilder insertUnannotatedDomainAcceptorMethod(DomainAcceptorMethod<? extends T> method) {
        domainAcceptorMethods.clear(); // TODO bug prone if called out of order
        domainAcceptorMethods.add(method);

        return this;
    }

    public MethodRoutingTableBuilder insertAnnotatedDomainAcceptorMethod(DomainAcceptorMethod<? extends T> method) {
        Class<? extends DomainObj> methodToInsertClazz = method.getAcceptedClazz();
        int i = 0;

        // until i >= domainAcceptorMethods.size() || isInsertingMethodClass assignable to currentMethodClass
        while (i < domainAcceptorMethods.size() &&
                !domainAcceptorMethods.get(i).getAcceptedClazz().isAssignableFrom(methodToInsertClazz)) {
            i++;
        }

        if (domainAcceptorMethods.get(i).getAcceptedClazz().equals(methodToInsertClazz)) {
            domainAcceptorMethods.remove(i);
        }
        domainAcceptorMethods.add(i, method);

        return this;
    }

    public MethodRoutingTable build() {
        return new MethodRoutingTable(new ArrayList<>(domainAcceptorMethods));
    }
}
