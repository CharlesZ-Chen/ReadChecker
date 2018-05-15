package read.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultFor;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

@DefaultFor({TypeUseLocation.UPPER_BOUND, TypeUseLocation.EXCEPTION_PARAMETER, TypeUseLocation.OTHERWISE})
@DefaultQualifierInHierarchy
@ImplicitFor(literals=LiteralKind.ALL)
@SubtypeOf({UnsafeRead.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface SafeRead {

}
