package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.BitOutputStream;

import java.util.ArrayList;

public interface EncodeDecodeStrategy {

    public OperationTypeEnum GetOperationType();
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy();
    public void EncodeDecode(byte[] file);
    public ArrayList<Boolean> GenerateHeader();
    public ArrayList<Boolean> GenerateBody(byte[] file);
}
