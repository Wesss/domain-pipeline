package test_instantiation.basic;

import org.wesss.domain_pipeline.util.interdomain.CharDomain;
import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Translator;

public class IntToCharTranslator extends Translator<IntDomain, CharDomain> {

    public IntToCharTranslator() {
        super(IntDomain.class);
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        emitter.emit(new CharDomain(
                Character.forDigit(domainObj.getInt() % 10, 10)
        ));
    }
}
