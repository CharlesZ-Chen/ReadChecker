package read.typeHierarchy;

import read.qual.ReadInt;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;
import read.qual.UnknownSafetyLiterals;
import read.qual.NarrowerReadInt;

// javac-dev -processorpath ./bin:./build-deps/framework.jar -processor read.ReadChecker -cp ./bin tests/read-typeHierarchy/TypeHierarchy.java
/**
 * type hierarchy is:
 *
 * NarrowerReadInt <: UnknowSafety
 * ReadInt <: NarrowerReadInt
 * UnknownSafetyLiterals <: ReadInt
 * SafetyBottom <: UnknownSafetyLiterals
 *
 * @author charleszhuochen
 *
 */
public class TypeHierarchy {

    void testMethod(int i, @ReadInt int readInt, @NarrowerReadInt byte narrowerReadInt,
            @SafetyBottom int safetyBottom,  byte readData) {
        int a = readInt; // OK: ReadInt <: UnknownSafety

        a = narrowerReadInt; // OK: NarrowerReadInt <: UnknownSafety

        a = -1; // OK: UnknownSafetyLiterals <: UnknownSafety

        readData = (byte) readInt; // OK: ReadInt <: UnknownSafety, and lhs will be refined by dataflow to NarrowerReadInt

        // :: error: (assignment.type.incompatible)
        readInt = i; // ERROR: violate type rule ReadInt <: UnknownSafety

        // :: error: (assignment.type.incompatible)
        readInt = narrowerReadInt; // ERROR: violate type rule ReadInt <: NarrowerReadInt

        readInt = -1; // OK: UnknownSafetyLiterals <: ReadInt

        narrowerReadInt = (byte) readInt; // OK: ReadInt <: NarrowerReadInt

        // :: error: (assignment.type.incompatible)
        safetyBottom = readInt; //ERROR: violate type rule SafetyBottom <: ReadInt

        // :: error: (assignment.type.incompatible)
        safetyBottom = narrowerReadInt; // ERROR: violate type rule SafetyBottom <: NarrowerReadInt

        // :: error: (assignment.type.incompatible)
        safetyBottom = 1; // ERROR: violate type rule SafetyBottom <: UnknownSafetyLiterals
    }
}
