package read;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.dataflow.analysis.ConditionalTransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions.Receiver;
import org.checkerframework.dataflow.cfg.node.IntegerLiteralNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.javacutil.AnnotationUtils;

import read.qual.UnsafeRead;
import read.qual.SafeRead;

public class ReadTransfer extends CFAbstractTransfer<CFValue, CFStore, ReadTransfer>{
    protected AnnotationMirror UNSAFE_READ;
    protected AnnotationMirror SAFE_READ;

    public ReadTransfer(ReadAnalysis analysis) {
        super(analysis);
        UNSAFE_READ = AnnotationUtils.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnsafeRead.class);
        SAFE_READ = AnnotationUtils.fromClass(analysis.getTypeFactory()
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

            if (!secondValue.getType().hasAnnotation(UNSAFE_READ)) {
                return res; //this Equal To doesn't check a read byte or char, thus do nothing.
            }

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
}
