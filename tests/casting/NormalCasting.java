package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import read.qual.UnsafeRead;

public class NormalCasting {
    @SuppressWarnings("unused")
    public void method(int foo) {
        byte fooByte = (byte) foo; // OK
        char fooChar = (char) foo; // OK

        int bar = 1;
        byte barByte = (byte) bar; // OK
        char barChar = (char) bar; // OK
    }

    @SuppressWarnings("unused")
    public void readMethod(@UnsafeRead int inbuff, int unkownInt) throws IOException {
        char unknownSafetyChar_1 = (char) (inbuff + 1); // Should cast up to UnknownSafety, OK
        char unknownSafetyChar_2 = (char) (inbuff + inbuff); // Should cast up to UnknownSafety, OK
        char unknownSafetyChar_3 = (char) (inbuff + unkownInt); // Should cast up to UnknownSafety, OK

        byte unknownSafetyByte_1 = (byte) (inbuff + 1); // Should cast up to UnknownSafety, OK
        byte unknownSafetyByte_2 = (byte) (inbuff + inbuff); // Should cast up to UnknownSafety, OK
        byte unknownSafetyByte_3 = (byte) (inbuff + unkownInt); // Should cast up to UnknownSafety, OK
    }
}
