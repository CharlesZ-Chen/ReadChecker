package read;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.javacutil.Pair;

public class ReadAnalysis extends CFAbstractAnalysis<CFValue, CFStore, ReadTransfer>{

    public ReadAnalysis(BaseTypeChecker checker,
            ReadAnnotatedTypeFactory factory,
            List<Pair<VariableElement, CFValue>> fieldValues) {
        super(checker, factory, fieldValues);
    }

    @Override
    public ReadTransfer createTransferFunction() {
        return new ReadTransfer(this);
    }

    @Override
    public CFStore createEmptyStore(boolean sequentialSemantics) {
        return new CFStore(this, sequentialSemantics);
    }

    @Override
    public CFStore createCopiedStore(CFStore s) {
        return new CFStore(this, s);
    }

    @Override
    public CFValue createAbstractValue(Set<AnnotationMirror> annotations, TypeMirror underlyingType) {
        return defaultCreateAbstractValue(this, annotations, underlyingType);
    }

}
