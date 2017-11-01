package atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NucleusComponent {

    String name() default "";

    NucleusScope scope() default NucleusScope.UNSPECIFIED;
}
