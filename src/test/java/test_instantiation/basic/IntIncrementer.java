package test_instantiation.basic;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Translator;

public class IntIncrementer extends Translator<IntDomain, IntDomain> {

    public IntIncrementer() {
        super(IntDomain.class);
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        emitter.emit(new IntDomain(domainObj.getInt() + 1));
    }
}
