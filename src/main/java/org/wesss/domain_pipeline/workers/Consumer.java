package org.wesss.domain_pipeline.workers;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.DomainAcceptorMethod;
import org.wesss.domain_pipeline.routing.MethodRoutingTable;
import org.wesss.domain_pipeline.routing.MethodRoutingTableBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @param <T> the type of domain obj consumed
 */
public abstract class Consumer<T extends DomainObj> implements DomainAcceptor<T> {

    private Class<T> acceptedClazz;

    public Consumer(Class<T> acceptedClazz) {
        this.acceptedClazz = acceptedClazz;
    }

    @Override
    public Class<T> getAcceptedClass() {
        return acceptedClazz;
    }

    @Override
    public abstract void acceptDomain(T domainObj);

    @Override
    public MethodRoutingTable<T> getMethodRoutingTable() {
        MethodRoutingTableBuilder<T> methodRoutingTableBuilder =
                new MethodRoutingTableBuilder<>();
        Class<? extends DomainObj> acceptedClazz = getAcceptedClass();

        // generate class ancestor list from (Object.class, this.getClass]
        List<Class<?>> clazzList = new ArrayList<>();
        Class<?> curClazz = this.getClass();
        while (!curClazz.equals(Object.class)) {
            clazzList.add(0, curClazz);
            curClazz = curClazz.getSuperclass();
        }

        // for every class this class is or extends
        for (Class<?> clazz : clazzList) {
            // ignore generated bridge methods
            Method[] allClassMethods = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> !method.isBridge())
                    .toArray(Method[]::new);

            // add the acceptDomain(T domainObj) method, overriding as necessary
            for (Method classMethod : allClassMethods) {
                if (DomainAcceptorMethod.isUnannotatedAcceptDomainMethod(classMethod, acceptedClazz)) {
                    methodRoutingTableBuilder.insertUnannotatedDomainAcceptorMethod(
                            new DomainAcceptorMethod(acceptedClazz, classMethod)
                    );
                }
            }
            // add annotated methods, overriding as necessary
            for (Method classMethod : allClassMethods) {
                if (classMethod.isAnnotationPresent(Accepts.class)) {
                    Accepts annotation = classMethod.getAnnotation(Accepts.class);

                    DomainAcceptorMethod<? extends T> acceptorMethod =
                            new DomainAcceptorMethod<>((Class<? extends T>)annotation.value(), classMethod);

                    // insert into list in order
                    methodRoutingTableBuilder.insertAnnotatedDomainAcceptorMethod(acceptorMethod);
                }
            }
        }

        return methodRoutingTableBuilder.build();
    }
}
