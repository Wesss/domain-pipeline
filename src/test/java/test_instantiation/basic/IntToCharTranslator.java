package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Translator;

public class IntToCharTranslator extends Translator<IntDomainObj, CharDomainObj> {

    public IntToCharTranslator() {
        super(IntDomainObj.class);
    }

    @Override
    public void acceptDomain(IntDomainObj domainObj) {
        emitter.emit(new CharDomainObj(
                Character.forDigit(domainObj.getId() % 10, 10)
        ));
    }
}
