package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.general_utils.reflection.RefectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @param <T> the weakest accepted domain obj type
 */
public class PostAnalysisDomainAcceptor<T extends DomainObj> {

    private final DomainAcceptor<T> domainAcceptor;
    private final MethodRoutingTable<T> methodRoutingTable;

    public PostAnalysisDomainAcceptor(DomainAcceptor<T> domainAcceptor,
                                      MethodRoutingTable<T> methodRoutingTable) {
        this.domainAcceptor = domainAcceptor;
        this.methodRoutingTable = methodRoutingTable;
    }

    public void acceptDomain(T domainObj) {
        Method acceptingMethod =
                methodRoutingTable.getMethodToAccept((Class<? extends T>) domainObj.getClass());

        RefectionUtils.invokeRethrowingInRuntimeException(
                acceptingMethod,
                domainAcceptor,
                domainObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostAnalysisDomainAcceptor<?> that = (PostAnalysisDomainAcceptor<?>) o;
        return Objects.equals(domainAcceptor, that.domainAcceptor) &&
                Objects.equals(methodRoutingTable, that.methodRoutingTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainAcceptor, methodRoutingTable);
    }

    @Override
    public String toString() {
        return "PostAnalysisDomainAcceptor{" +
                "domainAcceptor=" + domainAcceptor +
                ", methodRoutingTable=" + methodRoutingTable +
                '}';
    }
}
