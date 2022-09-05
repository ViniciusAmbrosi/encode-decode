package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.BitInputStream;

public interface EncodeDecodeStrategy {

    public OperationTypeEnum GetOperationType();
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy();
    public boolean EncodeDecode(byte[] file);
}
