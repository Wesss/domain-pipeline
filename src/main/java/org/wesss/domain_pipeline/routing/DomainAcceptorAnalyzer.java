package org.wesss.domain_pipeline.routing;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.DomainAcceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DomainAcceptorAnalyzer {

    public static <T extends DomainObj> PostAnalysisDomainAcceptor<T>
            analyzeDomainAcceptor(DomainAcceptor<T> domainAcceptor) {

        MethodRoutingTable<T> methodRoutingTable = getMethodRoutingTable(domainAcceptor);

        return new PostAnalysisDomainAcceptor<>(domainAcceptor, methodRoutingTable);
    }

    private static <T extends DomainObj> MethodRoutingTable<T>
            getMethodRoutingTable(DomainAcceptor<T> domainAcceptor) {

        MethodRoutingTableBuilder<T> methodRoutingTableBuilder =
                new MethodRoutingTableBuilder<>();
        Class<? extends DomainObj> acceptedDomainClazz = domainAcceptor.getAcceptedClass();

        // generate class ancestor list from (Object.class, this.getClass]
        List<Class<?>> clazzList = getClassHierarchy(domainAcceptor);

        // for every class this class is or extends
        for (Class<?> clazz : clazzList) {
            List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods =
                    getDomainAcceptorMethods(clazz, acceptedDomainClazz);

            for (DomainAcceptorMethod<? extends T> domainAcceptorMethod : domainAcceptorMethods) {
                methodRoutingTableBuilder.insertDomainAcceptorMethod(domainAcceptorMethod);
            }
        }

        return methodRoutingTableBuilder.build();
    }

    /**
     * @return a list containing all of the classes of given object from most ancestral
     * to most specific. Object.class is excluded from the front of the list
     */
    private static List<Class<?>> getClassHierarchy(Object object) {
        List<Class<?>> clazzList = new ArrayList<>();
        Class<?> curClazz = object.getClass();
        while (!curClazz.equals(Object.class)) {
            clazzList.add(0, curClazz);
            curClazz = curClazz.getSuperclass();
        }
        return clazzList;
    }

    private static <T extends DomainObj> List<DomainAcceptorMethod<? extends T>>
    getDomainAcceptorMethods(Class<?> clazz, Class<? extends DomainObj> acceptedDomainClazz) {
        List<DomainAcceptorMethod<? extends T>> domainAcceptorMethods = new ArrayList<>();

        // ignore generated bridge methods
        Method[] allClassMethods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> !method.isBridge())
                .toArray(Method[]::new);

        // add the acceptDomain(T domainObj) method, overriding as necessary
        for (Method classMethod : allClassMethods) {
            if (DomainAcceptorMethod.isUnannotatedAcceptDomainMethod(classMethod, acceptedDomainClazz)) {
                domainAcceptorMethods.add(new DomainAcceptorMethod(acceptedDomainClazz, classMethod));
            }
        }

        // add annotated methods, overriding as necessary
        for (Method classMethod : allClassMethods) {
            if (classMethod.isAnnotationPresent(Accepts.class)) {
                Accepts annotation = classMethod.getAnnotation(Accepts.class);

                domainAcceptorMethods.add(
                        new DomainAcceptorMethod<>((Class<? extends T>)annotation.value(), classMethod)
                );
            }
        }

        // TODO error checking here

        return domainAcceptorMethods;
    }
}
