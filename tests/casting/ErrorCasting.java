package read;

import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import read.qual.UnsafeRead;

//@skip-test
public class ErrorCasting {
 
    @SuppressWarnings("unused")
    public void readCharMethod(@UnsafeRead int unsafeReadBuff, int unknownInt) {
        int bar = unknownInt = unsafeReadBuff;

        // :: error: (cast.unsafe)
        char unSafeChar_1 = (char) bar;
        // :: error: (cast.unsafe)
        byte unsafeByte_1 = (byte) bar;

        int foo = unsafeReadBuff = unsafeReadBuff;
        // :: error: (cast.unsafe)
        char unSafeChar_2 = (char) foo;
        // :: error: (cast.unsafe)
        byte unsafeByte_2 = (byte) foo;
    }

    @SuppressWarnings("unused")
    public void postPreIncrementDecrement(@UnsafeRead int inbuff, int unknownInt) {
        // :: error: (cast.unsafe)
        char unknownSafetyChar_1 = (char) (inbuff++); // postIncrement would return original value of inbuff, unsafe
        char unknownSafetyChar_2 = (char) (++inbuff); // Should cast up to UnknownSafety, OK
        // :: error: (cast.unsafe)
        char unknownSafetyChar_3 = (char) (inbuff--); // postDecrement would return original value of inbuff, unsafe
        char unknownSafetyChar_4 = (char) (--inbuff); // Should cast up to UnknownSafety, OK

        // :: error: (cast.unsafe)
        byte unknownSafetyByte_1 = (byte) (inbuff++); // postIncrement would return original value of inbuff, unsafe
        byte unknownSafetyByte_2 = (byte) (++inbuff); // Should cast up to UnknownSafety, OK
        // :: error: (cast.unsafe)
        byte unknownSafetyByte_3 = (byte) (inbuff--); // postDecrement would return original value of inbuff, unsafe
        byte unknownSafetyByte_4 = (byte) (--inbuff); // Should cast up to UnknownSafety, OK
    }

}
