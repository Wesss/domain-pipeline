package org.wesss.domain_pipeline.util.interdomain;

import org.wesss.domain_pipeline.DomainObj;

public class IntDomain extends DomainObj {

    private int integer;

    public IntDomain(int id) {
        this.integer = id;
    }

    public int getInt() {
        return integer;
    }
}
