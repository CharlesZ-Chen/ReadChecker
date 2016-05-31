package read;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;

import com.sun.source.tree.TypeCastTree;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;

import read.qual.UnsafeRead;

public class ReadVisitor extends BaseTypeVisitor<ReadAnnotatedTypeFactory> {

    protected AnnotationMirror UNSAFE_READ;

    public ReadVisitor(BaseTypeChecker checker) {
        super(checker);
        UNSAFE_READ = AnnotationUtils.fromClass(elements, UnsafeRead.class);
    }

    @Override
    public ReadAnnotatedTypeFactory createTypeFactory() {
        return new ReadAnnotatedTypeFactory(checker);
    }

    @Override
    protected void checkTypecastSafety(TypeCastTree node, Void p) {
        AnnotatedTypeMirror castType = atypeFactory.getAnnotatedType(node);
        AnnotatedTypeMirror exprType = atypeFactory.getAnnotatedType(node.getExpression());

        if (((castType.getUnderlyingType().getKind() == TypeKind.BYTE || castType.getUnderlyingType().getKind() == TypeKind.CHAR) &&
                castType.hasAnnotation(UNSAFE_READ))) {
            checker.report(Result.failure("cast.unsafe", exprType, castType), node);
        }

        super.checkTypecastSafety(node, p);
    }
}
