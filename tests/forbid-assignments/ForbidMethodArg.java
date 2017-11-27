package read;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class ForbidMethodArg {
    static int readInt;

    public void compliantSolution() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        int localReadInt;
        localReadInt = in.read();
        // :: error: (method.arg.unsafe)
        storeData(localReadInt);
    }

    void storeData( int readInt ) {
        byte data;
        data = (byte) readInt;
    } 
}
