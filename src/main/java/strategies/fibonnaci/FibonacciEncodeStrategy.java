package strategies.fibonnaci;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FibonacciEncodeStrategy extends FibonacciStrategy {

    private final List<Integer> STARTING_FIBONACCI_VALUES = new ArrayList<>(Arrays.asList(1,2));

    public FibonacciEncodeStrategy() {
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
            var fibonacciSequenceForChar = FindLargestFibSequenceFor(charValue, STARTING_FIBONACCI_VALUES);
            this.FlagBitsForUsedFibonacciNumbers(charValue, fibonacciSequenceForChar).forEach(outputArray::add);
        }

        return outputArray;
    }

    @Override
    public void WriteBit(Boolean bit, DefaultBitOutputStream bitWriter) {
        bitWriter.write(bit);
    }

    private List<Integer> FindLargestFibSequenceFor(int valueToEncode, List<Integer> fibonacciList) {
        int lastFibonacciNumberIndex = fibonacciList.size() - 1;
        int nextFibonacciNumber = fibonacciList.get(lastFibonacciNumberIndex) + fibonacciList.get(lastFibonacciNumberIndex - 1);

        if (nextFibonacciNumber >= valueToEncode) {
            return fibonacciList;
        }
        else
        {
            fibonacciList.add(nextFibonacciNumber);
            return this.FindLargestFibSequenceFor(valueToEncode, fibonacciList);
        }
    }

    private List<Boolean> FlagBitsForUsedFibonacciNumbers(int valueToEncode, List<Integer> fibonacciNumbers) {
        List<Boolean> flaggedBits = new ArrayList<>();

        for (int i = fibonacciNumbers.size() - 1; i >= 0; i--)
        {
            int fibonacciNumber = fibonacciNumbers.get(i);

            if (valueToEncode >= fibonacciNumber) {
                valueToEncode = valueToEncode - fibonacciNumber;
                flaggedBits.add(true);
            }
            else {
                flaggedBits.add(false);
            }
        }

        Collections.reverse(flaggedBits);
        flaggedBits.add(true); //stop bit

        return flaggedBits;
    }
}
