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

import read.qual.UnsafeByte;
import read.qual.UnsafeChar;
import read.qual.SafeByte;
import read.qual.SafeChar;

public class ReadTransfer extends CFAbstractTransfer<CFValue, CFStore, ReadTransfer>{
    protected AnnotationMirror UNSAFE_BYTE;
    protected AnnotationMirror UNSAFE_CHAR;
    protected AnnotationMirror SAFE_BYTE;
    protected AnnotationMirror SAFE_CHAR;

    public ReadTransfer(ReadAnalysis analysis) {
        super(analysis);
        UNSAFE_BYTE = AnnotationUtils.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnsafeByte.class);
        UNSAFE_CHAR = AnnotationUtils.fromClass(analysis.getTypeFactory()
                .getElementUtils(), UnsafeChar.class);
        SAFE_BYTE = AnnotationUtils.fromClass(analysis.getTypeFactory()
                .getElementUtils(), SafeByte.class);
        SAFE_CHAR = AnnotationUtils.fromClass(analysis.getTypeFactory()
                .getElementUtils(), SafeChar.class);
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
            boolean isReadByteOrChar = false;
            AnnotationMirror SAFE_ANNOTATION = null;

            if (secondValue.getType().hasAnnotation(UNSAFE_BYTE)) {
                isReadByteOrChar = true;
                SAFE_ANNOTATION = SAFE_BYTE;
            } else if (secondValue.getType().hasAnnotation(UNSAFE_CHAR)) {
                isReadByteOrChar = true;
                SAFE_ANNOTATION = SAFE_CHAR;
            } else {
                isReadByteOrChar = false;
            }

            if (!isReadByteOrChar) {
                return res; //this Equal To doesn't check a read byte or char, thus do nothing.
            }

            assert SAFE_ANNOTATION != null;

            CFStore thenStore = res.getThenStore();
            CFStore elseStore = res.getElseStore();

            List<Node> secondParts = splitAssignments(secondNode);
            for (Node secondPart : secondParts) { // should we insert SAFE_ANNOTATION to all secondParts?
                Receiver secondInternal = FlowExpressions.internalReprOf(
                        analysis.getTypeFactory(), secondPart);
                if (CFStore.canInsertReceiver(secondInternal)) {
                    thenStore = thenStore == null ? res.getThenStore()
                            : thenStore;
                    elseStore = elseStore == null ? res.getElseStore()
                            : elseStore;
                    if (notEqualTo) {
                        thenStore.insertValue(secondInternal, SAFE_ANNOTATION);
                    } else {
                        elseStore.insertValue(secondInternal, SAFE_ANNOTATION);
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
