package read;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;

import com.sun.source.tree.TypeCastTree;

import read.qual.SafetyBottom;
import read.qual.UnsafeByte;
import read.qual.UnsafeChar;

public class ReadVisitor extends BaseTypeVisitor<ReadAnnotatedTypeFactory> {

    protected AnnotationMirror UNSAFE_BYTE;
    protected AnnotationMirror UNSAFE_CHAR;

    public ReadVisitor(BaseTypeChecker checker) {
        super(checker);
        UNSAFE_BYTE = AnnotationUtils.fromClass(elements, UnsafeByte.class);
        UNSAFE_CHAR = AnnotationUtils.fromClass(elements, UnsafeChar.class);
    }

    @Override
    public ReadAnnotatedTypeFactory createTypeFactory() {
        return new ReadAnnotatedTypeFactory(checker);
    }

    @Override
    protected void checkTypecastSafety(TypeCastTree node, Void p) {
        AnnotatedTypeMirror castType = atypeFactory.getAnnotatedType(node);
        AnnotatedTypeMirror exprType = atypeFactory.getAnnotatedType(node.getExpression());
        if ((castType.getUnderlyingType().getKind() == TypeKind.BYTE && castType.hasAnnotation(UNSAFE_BYTE)) ||
                (castType.getUnderlyingType().getKind() == TypeKind.CHAR && castType.hasAnnotation(UNSAFE_CHAR))) {
//            checker.report(Result.warning("cast.unsafe", exprType, castType), node);
            checker.report(Result.failure("cast.unsafe", exprType, castType), node);
        }

        super.checkTypecastSafety(node, p);
    }
}
