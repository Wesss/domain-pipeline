package test_instantiation;

import org.wesss.domain_pipeline.DomainObj;

public class TestIntDomainObj extends DomainObj {
    private int id;

    public TestIntDomainObj(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
