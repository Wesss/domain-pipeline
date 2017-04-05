package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.domain.MethodRoutingTable;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.general_utils.reflection.RefectionUtils;

import java.lang.reflect.Method;

/**
 * @param <T> the weakest accepted domain obj type
 */
public class MethodRouter<T extends DomainObj> {

    private DomainAcceptor<T> domainAcceptor;
    private MethodRoutingTable<T> methodRoutingTable;

    public MethodRouter(DomainAcceptor<T> domainAcceptor,
                        MethodRoutingTable<T> methodRoutingTable) {
        this.domainAcceptor = domainAcceptor;
        this.methodRoutingTable = methodRoutingTable;
    }

    public void changeTo(MethodRouter<T> other) {
        this.domainAcceptor = other.domainAcceptor;
        this.methodRoutingTable = other.methodRoutingTable;
    }

    public void acceptDomain(T domainObj) {
        Method acceptingMethod =
                methodRoutingTable.getMethodToAccept((Class<? extends T>) domainObj.getClass());

        RefectionUtils.invokeRethrowingInRuntimeException(
                acceptingMethod,
                domainAcceptor,
                domainObj);
    }

    /**
     * @return a methodRouter that routes to nothing
     */
    public static <T extends DomainObj> MethodRouter<T> getStubMethodRouter() {
        // TODO actually return stub method router
        return new MethodRouter<T>(null, null);
    }
}
