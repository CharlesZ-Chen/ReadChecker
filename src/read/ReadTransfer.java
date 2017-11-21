package read;

import java.util.Collections;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.dataflow.analysis.RegularTransferResult;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.cfg.node.AssignmentNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.javacutil.AnnotationBuilder;

import read.qual.NarrowerReadInt;
import read.qual.ReadInt;
import read.qual.UnknownSafety;

public class ReadTransfer extends CFAbstractTransfer<CFValue, CFStore, ReadTransfer>{
    protected final AnnotationMirror READ_INT;
    protected final AnnotationMirror NARROWER_READ_INT;
    protected final AnnotationMirror UNKNOWN_SAFETY;

    public ReadTransfer(ReadAnalysis analysis) {
        super(analysis);
        READ_INT = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), ReadInt.class);
        NARROWER_READ_INT = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), NarrowerReadInt.class);
        UNKNOWN_SAFETY = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnknownSafety.class);
    }

    public ReadTransfer(CFAbstractAnalysis<CFValue, CFStore, ReadTransfer> analysis) {
        super(analysis);
        READ_INT = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), ReadInt.class);
        NARROWER_READ_INT = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), NarrowerReadInt.class);
        UNKNOWN_SAFETY = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnknownSafety.class);
    }

    @Override
    public TransferResult<CFValue, CFStore> visitAssignment(AssignmentNode n, TransferInput<CFValue, CFStore> in) {
        TransferResult<CFValue, CFStore> result = super.visitAssignment(n, in);
        Node target = n.getTarget();
        Node expression = n.getExpression();
        CFValue expressionValue = getValueFromFactory(expression.getTree(), expression);
        CFValue originalTargetValue = getValueFromFactory(target.getTree(), target);
        TypeMirror targetType = originalTargetValue.getUnderlyingType();

//        System.err.println(" ---- expression: " +expression + " value: " + expressionValue);
        if (expressionValue.getAnnotations().contains(READ_INT) &&
                (targetType.getKind() == TypeKind.CHAR || targetType.getKind() == TypeKind.BYTE)) {
            CFValue updateTargetValue = analysis.createAbstractValue(Collections.singleton(NARROWER_READ_INT), targetType);
            CFStore updateRegularStore = result.getRegularStore();
            updateRegularStore.updateForAssignment(target, updateTargetValue);
            TransferResult<CFValue, CFStore> updatedRes = new RegularTransferResult<CFValue, CFStore>(updateTargetValue, updateRegularStore);
            return updatedRes;
        }

        return result;
    }
}
