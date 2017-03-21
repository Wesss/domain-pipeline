package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class CharConsumer extends Consumer<CharDomainObj> {

    private List<Character> receivedDomainObjects;

    public CharConsumer() {
        super(CharDomainObj.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(CharDomainObj domainObj) {
        receivedDomainObjects.add(domainObj.getId());
    }

    public List<Character> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
