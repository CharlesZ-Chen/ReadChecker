// code snippet from WTF code SOD 2016-11-02: http://thedailywtf.com/articles/Please_Supply_a_Test_Case
import java.io.IOException;
import java.io.InputStream;

public class WTFCodeSOD061102 {
    public void test(boolean _validConnection, InputStream _inputStream) throws IOException {
        while (!_validConnection) {
            StringBuffer _stringBuffer = new StringBuffer();
            try {
                while (true) {
                    char _char;
                    _stringBuffer.append(
                        // :: error: (cast.unsafe) :: error: (argument.type.incompatible)
                        _char = (char)_inputStream.read());
                    if (_char == -1) {
                        break;
                    } else if (_char == '\r') {
                        _stringBuffer.append(
                             // :: error: (cast.unsafe) :: error: (argument.type.incompatible)
                            _char = (char)_inputStream.read());
                        if (_char == -1) {
                            break;
                        } else if (_char == '\n') {
                            _stringBuffer.append(
                                // :: error: (cast.unsafe) :: error: (argument.type.incompatible)
                                _char = (char)_inputStream.read());
                            if (_char == -1) {
                                break;
                            } else if (_char == '\r') {
                                _stringBuffer.append(
                                      // :: error: (cast.unsafe):: error: (argument.type.incompatible)
                                    _char = (char)_inputStream.read());
                                if (_char == -1) {
                                    break;
                                } else if (_char == '\n') {
                                    _inputStream.read(
                                        new byte[_inputStream.available()]);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (OutOfMemoryError error) {
                // received a bad response, try it again!
                continue;
            }
        }
    }
}
