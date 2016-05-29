package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import read.qual.UnsafeChar;
import read.qual.UnsafeByte;

public class ErrorCasting {
 
    @SuppressWarnings("unused")
    public void readCharMethod(@UnsafeChar int charBuff, @UnsafeByte int byteBuff, int unknownInt) {
        int bar = unknownInt = charBuff;
        int bar_2 = unknownInt = byteBuff;
        //:: error: (cast.unsafe)
        char unSafeChar_1 = (char) bar;
        //:: error: (cast.unsafe)
        byte unsafeByte_1 = (byte) bar_2;

        int foo = charBuff = charBuff;
        //:: error: (cast.unsafe)
        char unSafeChar_3 = (char) foo;
        //:: error: (cast.unsafe)
        byte unknownSafetyByte_3 = (byte) foo;

        int foo_2 = byteBuff = byteBuff;
        //:: error: (cast.unsafe)
        byte unknownSafetyByte_4 = (byte) foo_2;
        //:: error: (cast.unsafe)
        char unSafeChar_4 = (char) foo_2;
    }

}
