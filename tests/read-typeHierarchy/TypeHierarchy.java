package read.typeHierarchy;

import read.qual.SafeRead;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;
import read.qual.UnsafeRead;

// javac-dev -processorpath ./bin:./build-deps/framework.jar -processor read.ReadChecker -cp ./bin tests/read-typeHierarchy/TypeHierarchy.java
/**
 * type hierarchy is:
 *
 * UnsafeRead <: UnknownSafety
 * SafeRead <: UnsafeRead
 * SafetyBottom <: SafeRead
 * SafetyBottom <: UnknownSafetyLiterals
 *
 * @author charleszhuochen
 *
 */
public class TypeHierarchy {

    void testMethod(int i, @UnsafeRead int unsafeRead, @SafeRead int safeRead, @SafetyBottom int safetyBottom) {

        //:: error: (assignment.type.incompatible)
        unsafeRead = i; // ERROR: violate type rule UnsafeRead <: UnknownSafety

        //:: error: (assignment.type.incompatible)
        safeRead = unsafeRead; // ERROR: violate type rule SafeRead <: UnsafeRead

        //:: error: (assignment.type.incompatible)
        safetyBottom = safeRead; // ERROR: violate type rule SafetyBottom <: SafeRead

        int a = unsafeRead; // OK: UnsafeRead <: UnknownSafety

        unsafeRead = safeRead; // OK: SafeRead <: UnsafeRead

        safeRead = safetyBottom; // OK: SafetyBottom <: SafeRead

    }

}
