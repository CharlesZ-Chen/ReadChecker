package read.qual;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@ConditionalPostconditionAnnotation(qualifier = SafeRead.class)
@InheritedAnnotation
public @interface EnsureSafeIf {
    /**
     * The Java expressions for which the qualifier holds if the method
     * terminates with return value {@link #result()}.
     *
     * @checker_framework.manual #java-expressions-as-arguments Syntax of Java expressions
     */
    String[] expression();

    /**
     * The return value of the method that needs to hold for the postcondition
     * to hold.
     */
    boolean result();
}

