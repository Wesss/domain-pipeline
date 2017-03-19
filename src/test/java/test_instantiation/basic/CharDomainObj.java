package test_instantiation.basic;

import org.wesss.domain_pipeline.DomainObj;

public class CharDomainObj extends DomainObj {
    private char id;

    public CharDomainObj(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }
}
