import java.io.File;

import org.checkerframework.framework.test.CheckerFrameworkPerFileTest;
import org.junit.runners.Parameterized.Parameters;

public class ReadTest extends CheckerFrameworkPerFileTest {

    public ReadTest(File testFile) {
        super(testFile, 
                read.ReadChecker.class,
                "",
                "-Anomsgtext");
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[]{"read-typeHierarchy", "post-condition", "binaryOpRefine", "noc-examples",
                "cs-examples", "casting", "teamed-quiz", "wtfCodeSOD061102"};
    }


}
