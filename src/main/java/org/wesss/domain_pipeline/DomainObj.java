package org.wesss.domain_pipeline;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This represents a state to be passed along through a domain pipeline.
 * Extensions of this object must remain immutable.
 */
public abstract class DomainObj {

    /**
     * @return true iff all non-transient and non-static fields of this and given object are equal
     * via their respective equals methods
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field[] fields = getFieldsReflectively();

        boolean isEqual = true;
        for (Field field : fields) {
            try {
                 if (!Objects.equals(field.get(this), field.get(o))) {
                     isEqual = false;
                     break;
                 }
            } catch (IllegalAccessException e) {
                // this should be impossible
                throw new InternalError(e);
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        Field[] fields = getFieldsReflectively();

        Object[] fieldObjects = Arrays.stream(fields)
                .map(field -> {
                    try {
                        return field.get(this);
                    } catch (IllegalAccessException e) {
                        // this should be impossible
                        throw new InternalError(e);
                    }
                })
                .toArray();

        return Objects.hash(fieldObjects);
    }

    // TODO move getFieldsReflectively into reflection general utils
    private Field[] getFieldsReflectively() {
        Field[] rawFields = getClass().getDeclaredFields();
        AccessibleObject.setAccessible(rawFields, true);

        return Arrays.stream(rawFields)
                .filter(field -> field.getName().indexOf('$') == -1)
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .toArray(Field[]::new);
    }
}
