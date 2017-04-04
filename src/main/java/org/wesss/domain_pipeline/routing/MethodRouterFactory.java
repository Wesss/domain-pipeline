package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.domain.MethodRoutingTable;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

import static org.wesss.domain_pipeline.routing.domain.DomainAcceptorAnalyzer.getMethodRoutingTable;

public class MethodRouterFactory {

    public static <T extends DomainObj> MethodRouter<T>
    getMethodRouter(DomainAcceptor<T> domainAcceptor) {

        MethodRoutingTable<T> methodRoutingTable = getMethodRoutingTable(domainAcceptor);

        return new MethodRouter<>(domainAcceptor, methodRoutingTable);
    }
}
