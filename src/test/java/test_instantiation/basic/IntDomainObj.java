package test_instantiation.basic;

import org.wesss.domain_pipeline.DomainObj;

public class IntDomainObj extends DomainObj {
    private int id;

    public IntDomainObj(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
