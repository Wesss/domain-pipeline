package test_instantiation.inheritance_based_consumption;

import java.util.Objects;

/**
 * Represents a single consumption event by a consumer
 */
public class DomainConsumption {
    private final Class<? extends DomainObjRoot> acceptedClass;
    private final Class<? extends DomainObjRoot> receivedClass;

    public DomainConsumption(Class<? extends DomainObjRoot> acceptedClass,
                             Class<? extends DomainObjRoot> receivedClass) {
        this.acceptedClass = acceptedClass;
        this.receivedClass = receivedClass;
    }

    public Class<? extends DomainObjRoot> getAcceptedClass() {
        return acceptedClass;
    }

    public Class<? extends DomainObjRoot> getReceivedClass() {
        return receivedClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainConsumption that = (DomainConsumption) o;
        return Objects.equals(acceptedClass, that.acceptedClass) &&
                Objects.equals(receivedClass, that.receivedClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceptedClass, receivedClass);
    }
}
