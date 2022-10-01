package strategies.golomb;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.util.ArrayList;

public class GolombEncodeStrategy extends GolombStrategy {

    public GolombEncodeStrategy() {
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
            var quotient = Math.floor(charValue / ENCODING_K);
            var rest = charValue % ENCODING_K;

            GenerateCodeWord(outputArray, quotient, rest);
        }

        return outputArray;
    }

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {
        bitWriter.write(bit);
    }

    //generate <Quotient Code><Rest Code>
    private void GenerateCodeWord(ArrayList<Boolean> outputArray, double quotient, int rest)
    {
        GenerateQuotient(outputArray, quotient);
        GenerateRest(outputArray, rest);
    }

    private void GenerateQuotient(ArrayList<Boolean> outputArray, double quotient)
    {
        for (int j = 0; j < quotient; j++) {
            outputArray.add(false);
        }

        outputArray.add(true);
    }

    private void GenerateRest(ArrayList<Boolean> outputArray, int rest)
    {
        String restString = Integer.toBinaryString(rest);

        for (int j = 0; j < BINARY_LOG_OF_K - restString.length(); j++) {
            outputArray.add(false);
        }

        for (int j = 0; j < restString.length(); j++) {
            outputArray.add(restString.charAt(j) == '1');
        }
    }
}
