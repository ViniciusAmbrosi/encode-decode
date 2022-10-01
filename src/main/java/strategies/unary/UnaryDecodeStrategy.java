package strategies.unary;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class UnaryDecodeStrategy extends UnaryStrategy{

    private final FileUtilsWrapper fileUtilsWrapper;

    public UnaryDecodeStrategy() {
        super(OperationTypeEnum.DECODE);
        this.fileUtilsWrapper = new FileUtilsWrapper();
    }

    @Override
    public void EncodeDecode(byte[] file) {
        var byteArray = new ByteArrayInputStream(file);
        try(var bits = new DefaultBitInputStream(byteArray))
        {
            //fetching header information to allow proper decoding
            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);

            List<Integer> chars = new ArrayList<>();
            int numberOfZeroes = 0;

            while(bits.available() > 0) {
                boolean currentBit = bits.readBit();

                if (!currentBit) {
                    numberOfZeroes++;
                }
                if (currentBit) {
                    chars.add(numberOfZeroes);
                    numberOfZeroes = 0;
                }
            }

            String resultingString = ConvertResultToString(chars);
            fileUtilsWrapper.WriteToFile("decode.txt", resultingString);
        }
        catch (Exception ex)
        {
            System.out.println("Failure during unary decoding.");
        }
    }

    @Override
    public ArrayList<Boolean> GenerateBody(byte[] file) {
        return null;
    }
}
