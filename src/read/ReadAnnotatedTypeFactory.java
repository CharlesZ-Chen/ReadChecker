package read;

import javax.lang.model.element.AnnotationMirror;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.UnaryTree;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.javacutil.AnnotationBuilder;

import read.qual.SafeRead;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;
import read.qual.UnsafeRead;

public class ReadAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<CFValue, CFStore, ReadTransfer, ReadAnalysis> {
    protected AnnotationMirror SAFETY_BOTTOM;
    protected AnnotationMirror UNSAFE_READ;
    protected AnnotationMirror SAFE_READ;
    protected AnnotationMirror UNKNOWN_SAFETY;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationBuilder.fromClass(elements, SafetyBottom.class);
        UNSAFE_READ = AnnotationBuilder.fromClass(elements, UnsafeRead.class);
        SAFE_READ = AnnotationBuilder.fromClass(elements, SafeRead.class);
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

        @Override
        public Void visitUnary(UnaryTree node, AnnotatedTypeMirror type) {
            type.replaceAnnotation(UNKNOWN_SAFETY);
            return null;
        }

        @Override
        public Void visitBinary(BinaryTree node, AnnotatedTypeMirror type) {
            type.replaceAnnotation(UNKNOWN_SAFETY);
            return null;
        }
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
