package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Translator;

public class IntIncrementer extends Translator<IntDomainObj, IntDomainObj> {

    public IntIncrementer() {
        super(IntDomainObj.class);
    }

    @Override
    public void acceptDomain(IntDomainObj domainObj) {
        emitter.emit(new IntDomainObj(domainObj.getId() + 1));
    }
}
