package strategies.golomb;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.BitInputStream;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.util.RuntimeEOFException;

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
    public boolean EncodeDecode(byte[] file) {
        List<Integer> charValues = new ArrayList<>();
        ByteArrayInputStream byteArray = new ByteArrayInputStream(file);

        try(var bits = new DefaultBitInputStream(byteArray)){
            int numberOfZeroes = 0;

            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);

            while(bits.available() > 0)
            {
                boolean currentBit = bits.readBit();

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
            return false;
        }

        String resultingString = ConvertResultToString(charValues);
        fileUtilsWrapper.WriteToFile("decode.txt", resultingString);

        return true;
    }

    private String ConvertResultToString(List<Integer> bitCharValues)
    {
        return bitCharValues.stream()
            .mapToInt(Integer::intValue)
            .mapToObj(this::ConvertIntToChar)
            .map(Object::toString)
            .reduce((acc, e) -> acc  + e)
            .get();
    }

    private char ConvertIntToChar(int charValue)
    {
        return (char) charValue;
    }
}
