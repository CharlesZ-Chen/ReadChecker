package read;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class NormalCasting {
    @SuppressWarnings("unused")
    public void method(int foo) {
        byte fooByte = (byte) foo; // OK
        char fooChar = (char) foo; // OK

        int bar = 1;
        byte barByte = (byte) bar; // OK
        char barChar = (char) bar; // OK
    }

    @SuppressWarnings("unused")
    public void readMethod() throws IOException {
        Reader byteIn = new FileReader("afile");
        int byteInbuff = byteIn.read();
        byteIn.close();

        InputStream charIn = new FileInputStream("afile");
        int charInbuff = charIn.read();
        charIn.close();

        int bar = byteInbuff++;
        byte barByte = (byte) bar; // OK
        char barChar = (char) bar; // OK

        bar = byteInbuff + 1;
        barByte = (byte) bar; // OK
        barChar = (char) bar; // OK

        int foo = charInbuff++;
        byte fooByte = (byte) foo; // OK
        char fooChar = (char) foo; // OK

    }
}
