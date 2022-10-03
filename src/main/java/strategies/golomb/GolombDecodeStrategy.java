package strategies.golomb;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class GolombDecodeStrategy extends GolombStrategy{

    private static final boolean STOP_BIT = Boolean.TRUE;
    private final FileUtilsWrapper fileUtilsWrapper;

    public GolombDecodeStrategy() {
        super(OperationTypeEnum.DECODE);
        this.fileUtilsWrapper = new FileUtilsWrapper();
    }

    @Override
    public void EncodeDecode(byte[] file) {
        List<Integer> charValues = new ArrayList<>();
        ByteArrayInputStream byteArray = new ByteArrayInputStream(file);

        try(var bits = new DefaultBitInputStream(byteArray)){
            int numberOfZeroes = 0;

            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);
            int crcCode = bits.readBits(BYTE_SIZE);

            while(bits.available() > 0)
            {
                boolean currentBit = bits.readBit();
                System.out.print(currentBit ? "1" : "0");

                if (!currentBit) {
                    numberOfZeroes++;
                }
                if (STOP_BIT == currentBit) {
                    int rest = bits.readBits((int) (Math.log10(headerK) / Math.log10(2)));
                    charValues.add((numberOfZeroes * headerK) + rest);
                    numberOfZeroes = 0;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Failure during golomb decoding.");
        }

        String resultingString = ConvertResultToString(charValues);
        fileUtilsWrapper.WriteToFile("decode.txt", resultingString);
    }

    @Override
    public ArrayList<Boolean> GenerateBody(byte[] file) {
        return null;
    }

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {

    }
}
