import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;

public class Foo {
    public void unsafeWayOfCastingByte() throws IOException {
        InputStream in = new FileInputStream("afile");
        @SuppressWarnings("unused")
        byte data;
        //:: error: (cast.unsafe)
        while ((data = (byte) in.read()) != -1) {
            //...
        }
        in.close();
    }

    public void unsafeWayOfCastingChar() throws IOException {
        Reader in = new FileReader("afile");
        @SuppressWarnings("unused")
        char data;
        //:: error: (cast.unsafe)
        while ((data = (char) in.read()) != -1) {
            //...
        }
        in.close();
    }

    public void compliantSolutionByte() throws IOException {
        InputStream in = new FileInputStream("afile");
        int inbuff;
        @SuppressWarnings("unused")
        byte data;
        while ((inbuff = in.read()) != -1) {
            data = (byte) inbuff; // OK
        }
        in.close();
    }

    public void compliantSolutionChar() throws IOException {
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