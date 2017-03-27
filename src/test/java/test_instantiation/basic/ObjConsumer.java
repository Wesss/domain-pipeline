package test_instantiation.basic;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class ObjConsumer extends Consumer<DomainObj> {

    private List<DomainObj> receivedDomainObjects;

    public ObjConsumer() {
        super(DomainObj.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(DomainObj domainObj) {
        receivedDomainObjects.add(domainObj);
    }

    public List<DomainObj> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
