package read.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({UnknownSafetyLiterals.class, SafeRead.class})
@ImplicitFor(literals = { LiteralKind.NULL })
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface SafetyBottom {

}
