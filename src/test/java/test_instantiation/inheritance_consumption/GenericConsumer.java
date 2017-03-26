package test_instantiation.inheritance_consumption;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class GenericConsumer<T extends DomainObj> extends Consumer<T> {

    private List<T> receivedDomainObjects;

    /**
     * @param acceptedClazz the class that is accepted by this consumer
     */
    public GenericConsumer(Class<T> acceptedClazz) {
        super(acceptedClazz);
        receivedDomainObjects = new ArrayList<>();
    }

    public List<T> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }

    @Override
    public void acceptDomain(T domainObj) {
        receivedDomainObjects.add(domainObj);
    }
}
