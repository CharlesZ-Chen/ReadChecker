package read.qual;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({SafeByte.class, SafeChar.class})
public @interface SafetyBottom {

}
