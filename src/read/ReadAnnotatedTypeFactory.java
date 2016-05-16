package read;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;

import read.qual.SafetyBottom;
import read.qual.UnknownSafety;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

public class ReadAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {
    protected AnnotationMirror SAFETY_BOTTOM;

    public ReadAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
        SAFETY_BOTTOM = AnnotationUtils.fromClass(elements, SafetyBottom.class);
    }

    @Override
    protected void annotateImplicit(Tree tree, AnnotatedTypeMirror type, boolean iUseFlow) {
        if (tree instanceof LiteralTree) {
            LiteralTree literalTree = (LiteralTree) tree;
            if (literalTree.getKind() == Tree.Kind.INT_LITERAL) {
                if ((int) literalTree.getValue() == -1) { // is there any better way to replace default qualifier by implicit o
                    if (type.hasAnnotation(UnknownSafety.class)) {
                        type.removeAnnotation(UnknownSafety.class);
                    }
                    type.addAnnotation(SafetyBottom.class);
                }
            }
        }
        super.annotateImplicit(tree, type, iUseFlow);
    }

}
