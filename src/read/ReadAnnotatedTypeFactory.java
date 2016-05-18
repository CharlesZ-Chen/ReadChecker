package read;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.Pair;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.UnaryTree;

import read.qual.SafeByte;
import read.qual.SafeChar;
import read.qual.SafetyBottom;
import read.qual.UnknownSafety;
import read.qual.UnsafeByte;
import read.qual.UnsafeChar;

public class ReadAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<CFValue, CFStore, ReadTransfer, ReadAnalysis> {
    protected AnnotationMirror SAFETY_BOTTOM;
    protected AnnotationMirror UNSAFE_BYTE;
    protected AnnotationMirror UNSAFE_CHAR;
    protected AnnotationMirror SAFE_BYTE;
    protected AnnotationMirror SAFE_CHAR;
    protected AnnotationMirror UNKNOWN_SAFETY;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationUtils.fromClass(elements, SafetyBottom.class);
        UNSAFE_BYTE = AnnotationUtils.fromClass(elements, UnsafeByte.class);
        UNSAFE_CHAR = AnnotationUtils.fromClass(elements, UnsafeChar.class);
        SAFE_BYTE = AnnotationUtils.fromClass(elements, SafeByte.class);
        SAFE_CHAR = AnnotationUtils.fromClass(elements, SafeChar.class);
        UNKNOWN_SAFETY = AnnotationUtils.fromClass(elements, UnknownSafety.class);
    }

    @Override
    protected ReadAnalysis createFlowAnalysis(List<Pair<VariableElement, CFValue>> fieldValues) {
        return new ReadAnalysis(checker, this, fieldValues);
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
                new ReadTreeAnnotator(this),
                new PropagationTreeAnnotator(this),
                new ImplicitsTreeAnnotator(this)
                );
    }
}
