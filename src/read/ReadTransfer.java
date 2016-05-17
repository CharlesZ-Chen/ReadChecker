package read;

import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;

public class ReadTransfer extends CFAbstractTransfer<CFValue, CFStore, ReadTransfer>{

    public ReadTransfer(ReadAnalysis analysis) {
        super(analysis);
    }

}
