package read;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class NOCE4Reader {
    public void unsafeWayOfCasting() throws IOException {
        Reader in = new FileReader("afile");
        @SuppressWarnings("unused")
        char data;
        // :: error: (cast.unsafe)
        while ((data = (char) in.read()) != -1) {
            //...
        }
        in.close();
    }
}
