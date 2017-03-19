package org.wesss.domain_pipeline.emitter;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.emitter.domain.DomainAcceptorMethod;
import org.wesss.domain_pipeline.emitter.domain.PostAnalysisDomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainAcceptor;
import org.wesss.domain_pipeline.workers.DomainEmitter;

import java.lang.reflect.Method;
import java.util.*;

import static org.wesss.domain_pipeline.workers.DomainAcceptor.ACCEPT_DOMAIN_METHOD_NAME;

public class EmitterFactory {

    private EmitterFactory() {

    }

    /**
     * Returns an emitter that will pass objects given to it to given domainAcceptors
     */
    public static <T extends DomainObj> Emitter<T>
            getEmitter(DomainEmitter<T> domainEmitter, Set<DomainAcceptor<T>> domainAcceptors) {
        Set<PostAnalysisDomainAcceptor<T>> analyzedDomainAcceptors = new HashSet<>();

        for (DomainAcceptor domainAcceptor : domainAcceptors) {
            List<DomainAcceptorMethod> domainAcceptorMethods = new ArrayList<>();
            Class<? extends DomainObj> acceptedClazz = domainAcceptor.getAcceptedClass();

            // generate class ancestor list from (Object.class, this.getClass]
            List<Class<?>> clazzList = new ArrayList<>();
            Class<?> curClazz = domainAcceptor.getClass();
            while (!curClazz.equals(Object.class)) {
                clazzList.add(0, curClazz);
                curClazz = curClazz.getSuperclass();
            }

            // for every class this class is or extends
            for (Class<?> clazz : clazzList) {
                Method[] allClassMethods = clazz.getDeclaredMethods();

                // add the acceptDomain(T domainObj) method, overriding as necessary
                for (Method classMethod : allClassMethods) {
                    if (isAcceptDomainMethod(classMethod, acceptedClazz)) {
                        domainAcceptorMethods.clear();
                        domainAcceptorMethods.add(new DomainAcceptorMethod(
                                acceptedClazz,
                                classMethod
                        ));
                    }
                }
                // add annotated methods, overriding as necessary
                for (Method classMethod : allClassMethods) {
                    if (classMethod.isAnnotationPresent(Accepts.class)) {
                        Accepts annotation = classMethod.getAnnotation(Accepts.class);

                        /* TODO temporary bugfix Reflective annotation processing
                        this is for bug where we receive the seemingly non-existent acceptDomain(DomainObj domainObj)
                        which is annotated by @Accepts(T). Perhaps change here when we do further checking in the
                        builder
                         */
                        if (!annotation.value().equals(classMethod.getParameterTypes()[0])) {
                            // skip in case of error
                            continue;
                        }

                        DomainAcceptorMethod<? extends DomainObj> acceptorMethod =
                                new DomainAcceptorMethod<>(annotation.value(), classMethod);

                        // insert into list in order
                        insertIntoAcceptorMethodList(domainAcceptorMethods, acceptorMethod);
                    }
                }
            }

            analyzedDomainAcceptors.add(new PostAnalysisDomainAcceptor<>(
                    domainAcceptor,
                    domainAcceptorMethods
            ));
        }

        return new Emitter<>(analyzedDomainAcceptors);
    }

    private static boolean isAcceptDomainMethod(Method method,
                                                Class<? extends DomainObj> acceptedClazz) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        return method.getName().equals(ACCEPT_DOMAIN_METHOD_NAME) &&
                parameterTypes.length == 1 &&
                parameterTypes[0].equals(acceptedClazz);
    }

    private static void insertIntoAcceptorMethodList(List<DomainAcceptorMethod> domainAcceptorMethods,
                                                     DomainAcceptorMethod<? extends DomainObj> methodToInsert) {
        Class<? extends DomainObj> methodToInsertClazz = methodToInsert.getAcceptedClazz();
        int i = 0;

        // until i >= domainAcceptorMethods.size() || isInsertingMethodClass assignable to currentMethodClass
        while (i < domainAcceptorMethods.size() &&
                !domainAcceptorMethods.get(i).getAcceptedClazz().isAssignableFrom(methodToInsertClazz)) {
            i++;
        }

        if (domainAcceptorMethods.get(i).getAcceptedClazz().equals(methodToInsertClazz)) {
            domainAcceptorMethods.remove(i);
        }
        domainAcceptorMethods.add(i, methodToInsert);
    }

    static <T extends DomainObj> Emitter<T> getStubEmitter() {
        return new Emitter<>(new HashSet<>());
    }
}
