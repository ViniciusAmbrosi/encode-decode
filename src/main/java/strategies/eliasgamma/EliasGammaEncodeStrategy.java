package strategies.eliasgamma;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.util.ArrayList;

public class EliasGammaEncodeStrategy extends EliasGammaStrategy {

    public EliasGammaEncodeStrategy() {
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

        //actual encoding logic

        return outputArray;
    }

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {
        bitWriter.write(bit);
    }
}
