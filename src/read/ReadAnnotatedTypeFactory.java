package read;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.javacutil.AnnotationBuilder;

import read.qual.NarrowerReadInt;
import read.qual.ReadInt;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;

public class ReadAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<CFValue, CFStore, ReadTransfer, ReadAnalysis> {
    protected final AnnotationMirror SAFETY_BOTTOM;
    protected final AnnotationMirror READ_INT;
    protected final AnnotationMirror NARROWER_READ_INT;
    protected final AnnotationMirror UNKNOWN_SAFETY;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationBuilder.fromClass(elements, SafetyBottom.class);
        READ_INT = AnnotationBuilder.fromClass(elements, ReadInt.class);
        NARROWER_READ_INT = AnnotationBuilder.fromClass(elements, NarrowerReadInt.class);
        UNKNOWN_SAFETY = AnnotationBuilder.fromClass(elements, UnknownSafety.class);
    }

    @Override
    public ReadTransfer createFlowTransferFunction(CFAbstractAnalysis<CFValue, CFStore, ReadTransfer> analysis) {
        return new ReadTransfer(analysis);
    }
    protected class ReadTreeAnnotator extends TreeAnnotator {

        public ReadTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
            super(atypeFactory);
        }

//        @Override
//        public Void visitUnary(UnaryTree node, AnnotatedTypeMirror type) {
//            System.err.println(" --- node: " + node + " type: " + type);
//            type.replaceAnnotation(UNKNOWN_SAFETY);
//            System.err.println(" === node: " + node + " type: " + type);
//            return null;
//        }

//        @Override
//        public Void visitBinary(BinaryTree node, AnnotatedTypeMirror type) {
//            type.replaceAnnotation(UNKNOWN_SAFETY);
//            return null;
//        }
    }

    @Override
    protected TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(
                new PropagationTreeAnnotator(this),
                new ImplicitsTreeAnnotator(this),
                new ReadTreeAnnotator(this)
                );
    }
}
