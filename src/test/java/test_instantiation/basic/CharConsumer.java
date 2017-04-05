package test_instantiation.basic;

import org.wesss.domain_pipeline.util.interdomain.CharDomain;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class CharConsumer extends Consumer<CharDomain> {

    private List<Character> receivedDomainObjects;

    public CharConsumer() {
        super(CharDomain.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(CharDomain domainObj) {
        receivedDomainObjects.add(domainObj.getChar());
    }

    public List<Character> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
