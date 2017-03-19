package org.wesss.domain_pipeline.emitter.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class PostAnalysisDomainAcceptor<T extends DomainObj> {

    // Sorted in an order such that each class is to the left of all its ancestors
    private final List<Class<? extends T>> acceptedDomainObjs;
    // A complete map of every possible domain acceptor/domain obj class combo to the accepting method
    private final Map<Class<? extends T>, Method> methodDeterminerToMethod;
}
