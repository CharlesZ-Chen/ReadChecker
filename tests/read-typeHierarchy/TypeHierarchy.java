package read.typeHierarchy;

import read.qual.SafeRead;
import read.qual.UnsafeRead;

// javac-dev -processorpath ./bin:./build-deps/framework.jar -processor read.ReadChecker -cp ./bin tests/read-typeHierarchy/TypeHierarchy.java
/**
 * type hierarchy is:
 *
 * SafeRead <: UnsafeRead
 *
 * @author charleszhuochen
 *
 */
public class TypeHierarchy {

    void testMethod(@UnsafeRead int unsafeRead, @SafeRead int safeRead) {

        // :: error: (assignment.type.incompatible)
        safeRead = unsafeRead; // ERROR: violate type rule SafeRead <: UnsafeRead

        unsafeRead = safeRead; // OK: SafeRead <: UnsafeRead
    }

}
