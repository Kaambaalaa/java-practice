package api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlForeignMapping {
    String field();
    Class entityType();
    String foreignField();
    String relationName();
    boolean many() default false;
    boolean orphanRemoval() default false;
}
