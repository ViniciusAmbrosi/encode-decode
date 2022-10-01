package strategies.eliasgamma;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class EliasGammaDecodeStrategy extends EliasGammaStrategy {

    private final FileUtilsWrapper fileUtilsWrapper;

    public EliasGammaDecodeStrategy() {
        super(OperationTypeEnum.DECODE);

        fileUtilsWrapper = new FileUtilsWrapper();
    }

    @Override
    public void EncodeDecode(byte[] file) {
        List<Integer> charValues = new ArrayList<>();
        ByteArrayInputStream byteArray = new ByteArrayInputStream(file);

        try(var bits = new DefaultBitInputStream(byteArray)) {
            int numberOfZeroes = 0;

            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);

            while (bits.available() > 0)
            {
                //actual code
            }
        }
        catch (Exception ex)
        {
            System.out.println("Failure during elias gamma decoding.");
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
