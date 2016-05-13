package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class CheckThroughMethod {

    public boolean isSafe(int inbuff) {
        if (inbuff != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void checkThroughMethodOfInputStreamA() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while (true) {
            inbuff = in.read();
            if(!isSafe(inbuff)) {
                break;
            }
            data = (byte) inbuff; // OK
        }
        in.close();
    }

    public void checkThroughMethodOfInputStreamB() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while (true) {
            inbuff = in.read();
            if(isSafe(inbuff)) {
                data = (byte) inbuff; // OK
            } else {
                break;
            }
        }
        in.close();
    }

    public void checkThroughMethodOfReaderA() throws IOException {
        Reader in = new FileReader("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while (true) {
            inbuff = in.read();
            if(!isSafe(inbuff)) {
                break;
            }
            data = (byte) inbuff; // OK
        }
        in.close();
    }

    public void checkThroughMethodOfReaderB() throws IOException {
        Reader in = new FileReader("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while (true) {
            inbuff = in.read();
            if(isSafe(inbuff)) {
                data = (byte) inbuff; // OK
            } else {
                break;
            }
        }
        in.close();
    }

}
