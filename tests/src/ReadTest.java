import java.io.File;

import org.checkerframework.framework.test.CheckerFrameworkTest;
import org.junit.runners.Parameterized.Parameters;

public class ReadTest extends CheckerFrameworkTest {

    public ReadTest(File testFile) {
        super(testFile, 
                read.ReadChecker.class,
                "",
                "-Anomsgtext");
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[]{"read-typeHierarchy", "NOC-examples"};
    }


}
