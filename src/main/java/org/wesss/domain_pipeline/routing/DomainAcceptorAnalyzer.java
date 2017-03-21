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
        Class<? extends T> acceptedDomainClazz = domainAcceptor.getAcceptedClass();

        // generate class hierarchy from (Object.class, this.getClass]
        List<Class<?>> clazzList = getClassHierarchy(domainAcceptor);

        // for every class this class is or extends
        for (Class<?> clazz : clazzList) {
            DeclaredDomainAcceptorMethods<T> declaredAcceptorMethods =
                    getDomainAcceptorMethods(clazz, acceptedDomainClazz);

            if (declaredAcceptorMethods.isUnannotatedAcceptorMethodPresent()) {
                methodRoutingTableBuilder.insertDomainAcceptorMethod(
                        declaredAcceptorMethods.getUnannotatedMethod()
                );
            }
            for (DomainAcceptorMethod<? extends T> domainAcceptorMethod
                    : declaredAcceptorMethods.getAnnotatedMethods()) {
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

    private static <T extends DomainObj> DeclaredDomainAcceptorMethods<T>
            getDomainAcceptorMethods(Class<?> clazz, Class<? extends T> acceptedDomainClazz) {

        DomainAcceptorMethod<T> unannotatedDomainAcceptorMethod = null;
        List<DomainAcceptorMethod<? extends T>> annotatedDomainAcceptorMethods = new ArrayList<>();

        // ignore generated bridge methods
        Method[] allClassMethods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> !method.isBridge())
                .toArray(Method[]::new);

        // add the acceptDomain(T domainObj) method, overriding as necessary
        for (Method classMethod : allClassMethods) {
            if (DomainAcceptorMethod.isUnannotatedAcceptDomainMethod(classMethod, acceptedDomainClazz)) {
                unannotatedDomainAcceptorMethod =
                        new DomainAcceptorMethod(acceptedDomainClazz, classMethod);
            }
        }

        // add annotated methods, overriding as necessary
        for (Method classMethod : allClassMethods) {
            if (classMethod.isAnnotationPresent(Accepts.class)) {
                Accepts annotation = classMethod.getAnnotation(Accepts.class);

                // TODO error checking here
                // TODO override default accept method here

                annotatedDomainAcceptorMethods.add(
                        new DomainAcceptorMethod<>((Class<? extends T>)annotation.value(), classMethod)
                );
            }
        }


        return new DeclaredDomainAcceptorMethods<>(unannotatedDomainAcceptorMethod, annotatedDomainAcceptorMethods);
    }
}
