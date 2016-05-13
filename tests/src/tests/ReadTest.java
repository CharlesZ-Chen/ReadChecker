package tests;
import java.io.File;

import javax.annotation.processing.AbstractProcessor;

import org.checkerframework.framework.test.CheckerFrameworkTest;

public class ReadTest extends CheckerFrameworkTest {

    public ReadTest(File testFile) {
        
        super(testFile, 
                read.ReadChecker.class, "read-typeHierarchy",
                "-Anomsgtext");
    }

}
