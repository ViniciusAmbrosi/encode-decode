package strategies.delta;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DeltaDecodeStrategy extends DeltaStrategy{

    private final FileUtilsWrapper fileUtilsWrapper;
    
    public DeltaDecodeStrategy() {
        super(OperationTypeEnum.DECODE);
        fileUtilsWrapper = new FileUtilsWrapper();
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        List<Long> charValues = new ArrayList<>();
        ByteArrayInputStream byteArray = new ByteArrayInputStream(file);

        try(var bits = new DefaultBitInputStream(byteArray)) {
            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);

            int numberOfZeroes = 0; //length Of Len

            var readBits = bits.readAllBytes();

            int currentBitIndex = 0;
            while (currentBitIndex < readBits.length) {
                var currentBit = readBits[currentBitIndex++];

                long num = 1;
                long len = 1;

                if (currentBit == 0) {
                    numberOfZeroes++;
                } else {
                    for (int j = 0; j < numberOfZeroes; j++) {
                        len <<= 1;
                        if (currentBitIndex < readBits.length && readBits[currentBitIndex++] == 1)
                            len |= 1;
                    }
                    for (int j = 0; j < len - 1; j++) {
                        num <<= 1;
                        if (currentBitIndex < readBits.length && readBits[currentBitIndex++] == 1)
                            num |= 1;
                    }

                    charValues.add(num);
                    numberOfZeroes = 0;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Failure during delta decoding.");
            return false;
        }

        String resultingString = ConvertResultToStringLong(charValues);
        fileUtilsWrapper.WriteToFile("decode.txt", resultingString);

        return true;
    }
}
