package org.wesss.domain_pipeline;

/**
 * Responsible for emitting domain objects to the next worker in a domain pipeline
 */
public class Emitter<T extends DomainObj> {

    public void emit(T domainObj) {
    }
}
