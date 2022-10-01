package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.util.ArrayList;

public interface EncodeDecodeStrategy <T> {

    public OperationTypeEnum GetOperationType();
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy();
    public void EncodeDecode(byte[] file);
    public ArrayList<Boolean> GenerateHeader();
    public ArrayList<T> GenerateBody(byte[] file);
    void WriteBit(T bit, DefaultBitOutputStream bitWriter);
}
