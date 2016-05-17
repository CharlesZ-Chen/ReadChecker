package read;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.javacutil.AnnotationUtils;

import read.qual.SafetyBottom;

public class ReadAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {
    protected AnnotationMirror SAFETY_BOTTOM;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationUtils.fromClass(elements, SafetyBottom.class);
    }

}
