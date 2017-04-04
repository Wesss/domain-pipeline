package org.wesss.domain_pipeline.routing.domain;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.MethodRouter;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.general_utils.exceptions.IllegalUseException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DomainAcceptorAnalyzer {

    public static <T extends DomainObj> MethodRoutingTable<T>
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

    /**
     * Returns all methods that are able to accept a domain obj
     */
    private static <T extends DomainObj> DeclaredDomainAcceptorMethods<T>
    getDomainAcceptorMethods(Class<?> domainAcceptorClazz, Class<? extends T> weakestAcceptedDomainClazz) {

        // TODO possibly use clazz.getMethods() to avoid scanning hierarchy of classes

        DomainAcceptorMethod<T> unannotatedDomainAcceptorMethod = null;
        List<DomainAcceptorMethod<? extends T>> annotatedDomainAcceptorMethods = new ArrayList<>();

        Method[] allClassMethods = domainAcceptorClazz.getDeclaredMethods();

        // add the acceptDomain(T domainObj) method
        for (Method classMethod : allClassMethods) {
            if (DomainAcceptorMethod.isUnannotatedAcceptDomainMethod(classMethod)) {
                unannotatedDomainAcceptorMethod =
                        new DomainAcceptorMethod<>((Class<T>) weakestAcceptedDomainClazz, classMethod);
            }
        }

        // add annotated methods, overriding the default acceptDomain method if possible
        for (Method classMethod : allClassMethods) {
            // ignore bridge methods
            if (classMethod.isAnnotationPresent(Accepts.class) && !classMethod.isBridge()) {
                Accepts annotation = classMethod.getAnnotation(Accepts.class);
                Class<? extends T> acceptedDomainClazz = (Class<? extends T>) annotation.value();

                if (acceptedDomainClazz.equals(weakestAcceptedDomainClazz)) {
                    unannotatedDomainAcceptorMethod = null;
                }

                annotatedDomainAcceptorMethods.add(
                        new DomainAcceptorMethod<>(acceptedDomainClazz, classMethod)
                );
            }
        }

        validateAcceptMethods(annotatedDomainAcceptorMethods,
                domainAcceptorClazz,
                weakestAcceptedDomainClazz);

        return new DeclaredDomainAcceptorMethods<>(unannotatedDomainAcceptorMethod, annotatedDomainAcceptorMethods);
    }

    /**
     * @throws IllegalUseException if the given annotated methods are not properly used
     */
    private static <T extends DomainObj> void
    validateAcceptMethods(List<DomainAcceptorMethod<? extends T>> annotatedDomainAcceptorMethods,
                          Class<?> domainAcceptorClazz,
                          Class<? extends T> weakestAcceptedDomainClazz) {
        Set<DomainAcceptorMethod<? extends T>> checkedMethods = new HashSet<>();

        for (DomainAcceptorMethod<? extends T> acceptorMethod : annotatedDomainAcceptorMethods) {
            Class<? extends T> acceptedClazz = acceptorMethod.getAcceptedClazz();
            Method method = acceptorMethod.getMethod();

            // make sure class type is accepted
            if (!weakestAcceptedDomainClazz.isAssignableFrom(acceptedClazz)) {
                throw new IllegalUseException(new StringBuilder()
                        .append("Method ")
                        .append(domainAcceptorClazz.getSimpleName())
                        .append(".")
                        .append(method.getName())
                        .append("(...) cannot accept arguments of type ")
                        .append(acceptedClazz.getSimpleName())
                        .append(" because ")
                        .append(domainAcceptorClazz.getSimpleName())
                        .append(" has been declared to accept arguments of type ")
                        .append(weakestAcceptedDomainClazz.getSimpleName())
                        .append(".")
                        .toString());
            }

            // make sure method accepts given type exactly
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1 || !parameterTypes[0].equals(acceptedClazz)) {
                StringBuilder error = new StringBuilder()
                        .append("Method ")
                        .append(domainAcceptorClazz.getSimpleName())
                        .append(".")
                        .append(method.getName())
                        .append("(...) must have a single parameter of the type it has been declared to accept");
                if (parameterTypes.length == 0) {
                    error.append(" (No parameters are present).");
                } else if (parameterTypes.length > 1) {
                    error.append(" (Too many parameters).");
                } else {
                    error.append(" (Parameter is of type ")
                            .append(parameterTypes[0].getSimpleName())
                            .append(" while declared to accept type ")
                            .append(acceptedClazz.getSimpleName())
                            .append(").");
                }
                throw new IllegalUseException(error.toString());
            }

            // make sure each class does not declare multiple methods to accept the same type
            for (DomainAcceptorMethod<? extends T> prevAcceptorMethod : checkedMethods) {
                if (prevAcceptorMethod.getAcceptedClazz().equals(acceptedClazz)) {
                    throw new IllegalUseException(new StringBuilder()
                            .append("Methods ")
                            .append(domainAcceptorClazz.getSimpleName())
                            .append(".")
                            .append(method.getName())
                            .append("(...) and ")
                            .append(domainAcceptorClazz.getSimpleName())
                            .append(".")
                            .append(prevAcceptorMethod.getMethod().getName())
                            .append("(...) have both been annotated to accept the type ")
                            .append(acceptedClazz.getSimpleName())
                            .append(".")
                            .toString());
                }
            }

            checkedMethods.add(acceptorMethod);
        }
    }
}
