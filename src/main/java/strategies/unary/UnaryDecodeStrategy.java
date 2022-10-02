package strategies.unary;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

            var hammingCodewordPosition = 0;
            var hammingCodeword = new byte[7];

            while(bits.available() > 0) {
                if(hammingCodewordPosition < 7)
                {
                    hammingCodeword[hammingCodewordPosition++] = bits.readBit() ? (byte) 1 : 0;
                }
                else
                {
                    hammingCodewordPosition = 0;
                    var actualBits = Arrays.copyOfRange(hammingCodeword, 0, 4);

                    for (byte currentBit : actualBits) {
                        System.out.print(currentBit);

                        if (currentBit == 0) {
                            numberOfZeroes++;
                        }
                        if (currentBit == 1) {
                            chars.add(numberOfZeroes);
                            numberOfZeroes = 0;
                        }
                    }
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

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {

    }
}
