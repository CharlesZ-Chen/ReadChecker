package read.typeHierarchy;

import read.qual.SafeByte;
import read.qual.SafeChar;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;
import read.qual.UnsafeByte;
import read.qual.UnsafeChar;

// javac-dev -processorpath ./bin:/Users/charleszhuochen/Programming/UWaterloo/jsr308/checker-framework/checker/dist/checker.jar -processor read.ReadChecker -cp ./bin tests/read-typeHierarchy/TypeHierarchy.java

public class TypeHierarchy {

    @UnsafeByte int produceReadByte() {
        return -1;
    }

    void consumeReadByte(@UnsafeByte int inbuff) { }

    void testMethod(int i, @UnsafeByte int inbuff) {

        //:: error: (argument.type.incompatible)
        consumeReadByte(i); // ERROR

        consumeReadByte(produceReadByte()); // OK

        int j = 1;

        //:: error: (argument.type.incompatible)
        consumeReadByte(j); // ERROR

        int k = produceReadByte();

        consumeReadByte(k); // OK

        k = - 1; // -1 is @SafetyBottom

        consumeReadByte(k);

        @UnknownSafety int p = 9;

        k = p; // This is also Ok, why? Because Declaritive type is always top
    }

}
