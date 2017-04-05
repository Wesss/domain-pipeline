package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.domain.DomainAcceptorAnalyzer;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

public class MethodRouterFactory {
    public static <T extends DomainObj> MethodRouter<T> getMethodRouter(DomainAcceptor<T> domainAcceptor) {
        return new MethodRouter<>(
                domainAcceptor,
                DomainAcceptorAnalyzer.getMethodRoutingTable(domainAcceptor)
        );
    }
}
