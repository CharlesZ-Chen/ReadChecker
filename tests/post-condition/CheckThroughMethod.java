package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import read.qual.EnsureSafeIf;
import read.qual.UnsafeRead;

public class CheckThroughMethod {

    @EnsureSafeIf(expression="#1", result=true)
    public boolean isSafe(int inbuff) {
        return inbuff != -1;
    }


    public void checkThroughMethodOfInputStreamA(InputStream in) throws IOException {
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        char charData;
        while (true) {
            inbuff = in.read();
            if(!isSafe(inbuff)) {
                break;
            }
            data = (byte) inbuff; // OK
            charData = (char) inbuff; // OK
        }
    }

}
