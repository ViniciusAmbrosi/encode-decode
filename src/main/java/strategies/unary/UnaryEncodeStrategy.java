package strategies.unary;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.util.ArrayList;

public class UnaryEncodeStrategy extends UnaryStrategy{

    public UnaryEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public void EncodeDecode(byte[] file) {
        super.Encode(file);
    }

    @Override
    public ArrayList<Boolean> GenerateBody(byte[] file) {
        var inputArray = new String(file).chars().toArray();
        var outputArray = new ArrayList<Boolean>();

        for (int charValue : inputArray) {
            PopulateUnaryEncodingForChar(outputArray, charValue);
        }

        return outputArray;
    }

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {
        bitWriter.write(bit);
    }

    private void PopulateUnaryEncodingForChar(ArrayList<Boolean> bits, int charValue) {
        for (int j = 0; j < charValue; j++) {
            bits.add(false);
        }

        bits.add(true);
    }
}
