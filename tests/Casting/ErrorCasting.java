package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import read.qual.UnsafeRead;

public class ErrorCasting {
 
    @SuppressWarnings("unused")
    public void readCharMethod(@UnsafeRead int unsafeReadBuff, int unknownInt) {
        int bar = unknownInt = unsafeReadBuff;

        //:: error: (cast.unsafe)
        char unSafeChar_1 = (char) bar;
        //:: error: (cast.unsafe)
        byte unsafeByte_1 = (byte) bar;

        int foo = unsafeReadBuff = unsafeReadBuff;
        //:: error: (cast.unsafe)
        char unSafeChar_2 = (char) foo;
        //:: error: (cast.unsafe)
        byte unsafeByte_2 = (byte) foo;
    }

}
