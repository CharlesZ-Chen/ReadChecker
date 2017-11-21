package read;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.dataflow.analysis.ConditionalTransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions.Receiver;
import org.checkerframework.dataflow.cfg.node.GreaterThanOrEqualNode;
import org.checkerframework.dataflow.cfg.node.IntegerLiteralNode;
import org.checkerframework.dataflow.cfg.node.LessThanNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.javacutil.AnnotationBuilder;

import read.qual.UnsafeRead;
import read.qual.SafeRead;

public class ReadTransfer extends CFAbstractTransfer<CFValue, CFStore, ReadTransfer>{
    protected AnnotationMirror UNSAFE_READ;
    protected AnnotationMirror SAFE_READ;

    public ReadTransfer(ReadAnalysis analysis) {
        super(analysis);
        UNSAFE_READ = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnsafeRead.class);
        SAFE_READ = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), SafeRead.class);
    }

    public ReadTransfer(CFAbstractAnalysis<CFValue, CFStore, ReadTransfer> analysis) {
        // TODO Auto-generated constructor stub
        super(analysis);
        UNSAFE_READ = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnsafeRead.class);
        SAFE_READ = AnnotationBuilder.fromClass(analysis.getTypeFactory()
                .getElementUtils(), SafeRead.class);
    }

    @Override
    protected TransferResult<CFValue, CFStore> strengthenAnnotationOfEqualTo (
            TransferResult<CFValue, CFStore> res, Node firstNode,
            Node secondNode, CFValue firstValue, CFValue secondValue,
            boolean notEqualTo) {
        res = super.strengthenAnnotationOfEqualTo(res, firstNode, secondNode,
                firstValue, secondValue, notEqualTo);

        if (firstNode instanceof IntegerLiteralNode &&
                ((IntegerLiteralNode) firstNode).getValue() == -1) {

            // This is a trade off for implementing the post-condition @EnsureSafeIf.
            // If we refine a qualifier to @SafeRead only if the related value has @UnsafeRead,
            // then a programmer has to write a @UnsafeRead annotation to the method's argument
            // he/she wants to ensure about. The good part of this is we may only want
            // the passing argument has type of @SafeRead, which is reasonable. However, the
            // bad part of this is it needs explicit annotation from the programmer side.
            // Because programmers don't like writing annotations (let's admit that we all don't like annotations:-<)
            // Thus here I loosing the restriction, that is to refine all types to @SafeRead as long as
            // it check against with -1.
//            if (!secondValue.getType().hasAnnotation(UNSAFE_READ)) {
//                return res; //this Equal To doesn't check a read byte or char, thus do nothing.
//            }

            CFStore thenStore = res.getThenStore();
            CFStore elseStore = res.getElseStore();

            List<Node> secondParts = splitAssignments(secondNode);
            for (Node secondPart : secondParts) {
                Receiver secondInternal = FlowExpressions.internalReprOf(
                        analysis.getTypeFactory(), secondPart);
                if (CFStore.canInsertReceiver(secondInternal)) {
                    thenStore = thenStore == null ? res.getThenStore()
                            : thenStore;
                    elseStore = elseStore == null ? res.getElseStore()
                            : elseStore;
                    if (notEqualTo) {
                        thenStore.insertValue(secondInternal, SAFE_READ);
                    } else {
                        elseStore.insertValue(secondInternal, SAFE_READ);
                    }
                }
            }

            if (thenStore != null) {
                return new ConditionalTransferResult<>(res.getResultValue(),
                        thenStore, elseStore);
            }
        }

        return res;
    }

    @Override
    public TransferResult<CFValue, CFStore> visitLessThan(LessThanNode n,
            TransferInput<CFValue, CFStore> p) {
        TransferResult<CFValue, CFStore> res = super.visitLessThan(n, p);

        Node leftN = n.getLeftOperand();
        Node rightN = n.getRightOperand();
        CFValue leftV = p.getValueOfSubNode(leftN);
        CFValue rightV = p.getValueOfSubNode(rightN);

        // case: leftN < 0
        res = strengthenAnnotationOfLessThan(res, leftN, rightN, leftV, rightV, false);

        return res;
    }

    @Override
    public TransferResult<CFValue, CFStore> visitGreaterThanOrEqual(GreaterThanOrEqualNode n,
            TransferInput<CFValue, CFStore> p) {
        TransferResult<CFValue, CFStore> res = super.visitGreaterThanOrEqual(n, p);

        Node leftN = n.getLeftOperand();
        Node rightN = n.getRightOperand();
        CFValue leftV = p.getValueOfSubNode(leftN);
        CFValue rightV = p.getValueOfSubNode(rightN);

        // case: leftN >= 0
        res = strengthenAnnotationOfLessThan(res, leftN, rightN, leftV, rightV, true);

        return res;
    }

    /**
     * For expression leftN < rightN or leftN >= rightN:
     * refine the annotation of {@code leftN} if {@code rightN} is integer literal 0.
     * @param res
     *              The previous result
     * @param leftN
     * @param rightN
     * @param firstValue
     *              not used in ReadTransfer. leave for possible up-integration
     * @param secondValue
     *              not used in ReadTransfer. leave for possible up-integration
     * @param notLessThan
     *              If true, indicates the logic is flipped i.e (GreaterOrEqualThan)
     * @return
     */
    protected TransferResult<CFValue, CFStore> strengthenAnnotationOfLessThan (
            TransferResult<CFValue, CFStore> res, Node leftN,
            Node rightN, CFValue firstValue, CFValue secondValue,
            boolean notLessThan) {
        // case: firstNode < 0
        if (rightN instanceof IntegerLiteralNode &&
                ((IntegerLiteralNode) rightN).getValue() == 0) {
            CFStore thenStore = res.getThenStore();
            CFStore elseStore = res.getElseStore();
            List<Node> secondParts = splitAssignments(leftN);
            for (Node secondPart : secondParts) {
                Receiver secondInternal = FlowExpressions.internalReprOf(
                        analysis.getTypeFactory(), secondPart);
                if (CFStore.canInsertReceiver(secondInternal)) {
                    thenStore = thenStore == null ? res.getThenStore()
                            : thenStore;
                    elseStore = elseStore == null ? res.getElseStore()
                            : elseStore;
                    if (notLessThan) {
                        thenStore.insertValue(secondInternal, SAFE_READ);
                    } else {
                        elseStore.insertValue(secondInternal, SAFE_READ);
                    }
                }
            }

            // case: 0 < rightN
            // currently I don't process this part, because of two reasons:
            // 1. Doesn't find real code need this case
            // 2. although 0 < rightN would imply rightN is safe for casting,
            //  it still has a bug: the 0x00 byte or 0x0000 char will be missed

            // TODO: should ReadChecker issue a warning like `warning.missed.read.zero`
            // in this case? i.e. code like {@code while ((intbuff = in.read()) > 0) {...}}

            if (thenStore != null) {
                return new ConditionalTransferResult<>(res.getResultValue(),
                        thenStore, elseStore);
            }
        }

        return res;
    }
}
