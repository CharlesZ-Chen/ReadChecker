package read;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.javacutil.AnnotationBuilder;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;

import read.qual.NarrowerReadInt;
import read.qual.ReadInt;

public class ReadVisitor extends BaseTypeVisitor<ReadAnnotatedTypeFactory> {

    protected AnnotationMirror READ_INT;
    protected AnnotationMirror NARROWER_READ_INT;

    public ReadVisitor(BaseTypeChecker checker) {
        super(checker);
        READ_INT = AnnotationBuilder.fromClass(elements, ReadInt.class);
        NARROWER_READ_INT = AnnotationBuilder.fromClass(elements, NarrowerReadInt.class);
    }

    @Override
    public ReadAnnotatedTypeFactory createTypeFactory() {
        return new ReadAnnotatedTypeFactory(checker);
    }

    @Override
    public Void visitBinary(BinaryTree node, Void p) {
        ExpressionTree lhs = node.getLeftOperand();
        ExpressionTree rhs = node.getRightOperand();
        if (node.getKind() == Kind.NOT_EQUAL_TO || node.getKind() == Kind.EQUAL_TO) {
            if ((isReadCharOrByte(lhs) && isEOFIntLiteral(rhs))
                    || (isReadCharOrByte(rhs) && isEOFIntLiteral(lhs))) {
                checker.report(Result.failure("unsafe.eof.comparision"), node);
            }
        }
        return super.visitBinary(node, p);
    }

    private boolean isReadCharOrByte(Tree node) {
        return atypeFactory.getAnnotatedType(node).hasAnnotation(NARROWER_READ_INT);
    }

    private boolean isEOFIntLiteral(Tree node) {
        return node.getKind() == Kind.INT_LITERAL && ((LiteralTree) node).getValue().equals(Integer.valueOf(-1));
    }

}
