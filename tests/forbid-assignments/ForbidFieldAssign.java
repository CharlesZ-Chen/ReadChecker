package read;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class ForbidFieldAssign {
    static int readInt;

    public void compliantSolution() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        int localReadInt;
        localReadInt = in.read();
    }

    void readData(InputStream in) throws IOException {
        // :: error: (assignment.unsafe)
        readInt = in.read();
    }
}
