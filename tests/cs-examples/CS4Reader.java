package read;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CS4Reader {
    public void compliantSolution() throws IOException {
        Reader in = new FileReader("afile");
        int inbuff;
        @SuppressWarnings("unused")
        char data;
        while ((inbuff = in.read()) != -1) {
            data = (char) inbuff; // OK
        }
        in.close();
    }
}
