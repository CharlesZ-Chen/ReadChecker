import java.io.File;
import java.util.List;

import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runners.Parameterized.Parameters;

public class ReadTest extends CheckerFrameworkPerDirectoryTest {

    public ReadTest(List<File> testFiles) {
        super(testFiles,
                read.ReadChecker.class,
                "",
                "-Anomsgtext");
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[]{
                "read-typeHierarchy",
                "binaryOpRefine",
                "customize-reader",
                "noc-examples",
                "cs-examples",
                "casting",
                "teamed-quiz",
                "wtfCodeSOD061102",
                "false-positives", // Test on previous false positives found in projects. Read Checker should
                                   // not issue any warnings on these code.
                };
    }
}
