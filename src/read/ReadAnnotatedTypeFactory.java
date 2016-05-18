package read;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.Pair;

import read.qual.SafetyBottom;

public class ReadAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<CFValue, CFStore, ReadTransfer, ReadAnalysis> {
    protected AnnotationMirror SAFETY_BOTTOM;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationUtils.fromClass(elements, SafetyBottom.class);
    }

    @Override
    protected ReadAnalysis createFlowAnalysis(List<Pair<VariableElement, CFValue>> fieldValues) {
        return new ReadAnalysis(checker, this, fieldValues);
    }

}
