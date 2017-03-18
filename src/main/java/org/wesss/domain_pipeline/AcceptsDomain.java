package org.wesss.domain_pipeline;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD}) // TODO try without array braces
public @interface AcceptsDomain {
    Class<? extends DomainObj> clazz();
}
