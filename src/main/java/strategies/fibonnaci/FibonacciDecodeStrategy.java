package strategies.fibonnaci;

import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import htsjdk.samtools.cram.io.DefaultBitInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FibonacciDecodeStrategy extends FibonacciStrategy{

    private final FileUtilsWrapper fileUtilsWrapper;

    public FibonacciDecodeStrategy() {
        super(OperationTypeEnum.DECODE);

        fileUtilsWrapper = new FileUtilsWrapper();
    }

    @Override
    public void EncodeDecode(byte[] file) {
        List<Integer> charValues = new ArrayList<>();
        ByteArrayInputStream byteArray = new ByteArrayInputStream(file);

        try(var bits = new DefaultBitInputStream(byteArray)) {
            int headerIdentifier = bits.readBits(BYTE_SIZE);
            int headerK = bits.readBits(BYTE_SIZE);

            List<Boolean> mappedNumber = new ArrayList<>();
            boolean lastMappedNumber = false;
            while (bits.available() > 0)
            {
                boolean currentBit = bits.readBit();
                if (currentBit && currentBit == lastMappedNumber) {
                    charValues.add(calculateFibonacciForBooleanList(mappedNumber));
                    mappedNumber.clear();
                    lastMappedNumber = false;
                } else {
                    lastMappedNumber = currentBit;
                    mappedNumber.add(currentBit);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Failure during fibonacci decoding.");
        }

        String resultingString = ConvertResultToString(charValues);
        fileUtilsWrapper.WriteToFile("decode.txt", resultingString);
    }

    @Override
    public ArrayList<Boolean> GenerateBody(byte[] file) {
        return null;
    }

    private int calculateFibonacciForBooleanList(List<Boolean> values) {
        List<Integer> fibonacci = new ArrayList<Integer>(Arrays.asList(1, 2));
        for (int i = 2; i < values.size(); i++) {
            fibonacci.add(fibonacci.get(i - 1) + fibonacci.get(i - 2));
        }
        int count = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i)) {
                count += fibonacci.get(i);
            }
        }
        return count;
    }
}
