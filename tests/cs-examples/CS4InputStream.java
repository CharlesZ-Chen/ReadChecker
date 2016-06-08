package read;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CS4InputStream {
    public void compliantSolution() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while ((inbuff = in.read()) != -1) {
            data = (byte) inbuff; // OK
        }
        in.close();
    }

}
