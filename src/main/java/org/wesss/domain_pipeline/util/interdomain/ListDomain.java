package org.wesss.domain_pipeline.util.interdomain;

import org.wesss.domain_pipeline.DomainObj;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListDomain<T> extends DomainObj {

    private List<T> list;

    public ListDomain(List list) {
        Objects.requireNonNull(list);
        this.list = Collections.unmodifiableList(list);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
