package read;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;

public class ReadVisitor extends BaseTypeVisitor<ReadAnnotatedTypeFactory> {

    public ReadVisitor(BaseTypeChecker checker) {
        super(checker);
    }

}
