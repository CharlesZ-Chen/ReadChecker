// This test case is a real code from https://github.com/teamed/quiz/blob/master/Parser.java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class BadParser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }
  public synchronized File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      // :: error: (cast.unsafe)
      output += (char) data;
    }
    // Return type is incompatible as output was refined to @UnsafeRead
    // :: error: (return.type.incompatible)
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
    FileInputStream i = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = i.read()) > 0) {
      if (data < 0x80) {
        // :: error: (cast.unsafe)
        output += (char) data;
      }
    }
    // Return type is incompatible as output was refined to @UnsafeRead
    // :: error: (return.type.incompatible)
    return output;
  }
  public void saveContent(String content) throws IOException {
    FileOutputStream o = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      o.write(content.charAt(i));
    }
  }
}
