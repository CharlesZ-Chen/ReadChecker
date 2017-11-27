package read;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.TypeCastTree;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationBuilder;
import org.checkerframework.javacutil.TreeUtils;

import read.qual.UnsafeRead;

public class ReadVisitor extends BaseTypeVisitor<ReadAnnotatedTypeFactory> {

    protected AnnotationMirror UNSAFE_READ;

    public ReadVisitor(BaseTypeChecker checker) {
        super(checker);
        UNSAFE_READ = AnnotationBuilder.fromClass(elements, UnsafeRead.class);
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

    @Override
    public Void visitAssignment(AssignmentTree node, Void p) {
      ExpressionTree variableTree = node.getVariable();
      AnnotatedTypeMirror variableType = atypeFactory.getAnnotatedType(variableTree);
      AnnotatedTypeMirror expressionType = atypeFactory.getAnnotatedType(node.getExpression());

      Element variableDeclartion = TreeUtils.elementFromUse(variableTree);

      if (variableDeclartion.getKind() == ElementKind.FIELD
              && variableType.getUnderlyingType().getKind() == TypeKind.INT
              && expressionType.hasAnnotation(UNSAFE_READ)) {
          checker.report(Result.failure("assignment.unsafe", variableType, expressionType), node);
      }

      return super.visitAssignment(node, p);
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, Void p) {
        List<? extends ExpressionTree> arguments = node.getArguments();

        for (ExpressionTree argument : arguments) {
            AnnotatedTypeMirror argType = atypeFactory.getAnnotatedType(argument);
            if (argType.getUnderlyingType().getKind() == TypeKind.INT
                    && argType.hasAnnotation(UNSAFE_READ)) {
                checker.report(Result.failure("method.arg.unsafe", argType), argument);
            }
        }

        return super.visitMethodInvocation(node, p);
    }
}
