package strategies.delta;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import java.util.ArrayList;

public class DeltaEncodeStrategy extends DeltaStrategy {

    public DeltaEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public void EncodeDecode(byte[] file) {
        super.Encode(file);
    }

    @Override
    public ArrayList<Integer> GenerateBody(byte[] file) {
        var inputArray = new String(file).chars().toArray();
        var outputArray = new ArrayList<Integer>();

        for (int value : inputArray) {
            int len, lengthOfLen = 0;

            len = 1 + CalculateBinaryLog(value);  // calculate 1+floor(log2(num))
            lengthOfLen = CalculateBinaryLog(len); // calculate floor(log2(len))

            for (int i = lengthOfLen; i > 0; --i)
                outputArray.add(0);
            for (int i = lengthOfLen; i >= 0; --i)
                outputArray.add((len >> i) & 1);
            for (int i = len-2; i >= 0; i--)
                outputArray.add((value >> i) & 1);
        }

        return outputArray;
    }

    @Override
    public void WriteBit(Integer bit, DefaultBitOutputStream bitWriter) {
        bitWriter.write(bit);
    }
}
