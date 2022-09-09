package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.BitOutputStream;

import java.util.List;

import static java.lang.Math.floor;

public abstract class DefaultEncodeDecodeStrategy implements EncodeDecodeStrategy{

    private OperationTypeEnum operationType;
    private EncodeDecodeStrategyEnum encodeDecodeStrategy;

    public static final int BYTE_SIZE = 8;

    public DefaultEncodeDecodeStrategy(OperationTypeEnum operationType, EncodeDecodeStrategyEnum encodeDecodeStrategy)
    {
        this.operationType = operationType;
        this.encodeDecodeStrategy = encodeDecodeStrategy;
    }

    @Override
    public OperationTypeEnum GetOperationType() {
        return operationType;
    }

    @Override
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy() {
        return encodeDecodeStrategy;
    }

    protected int CalculateBinaryLog(int value)
    {
        return (int) floor((Math.log10(value) / Math.log10(2)));
    }

    protected String ConvertResultToString(List<Integer> bitCharValues)
    {
        return bitCharValues.stream()
                .mapToInt(Integer::intValue)
                .mapToObj(this::ConvertIntToChar)
                .map(Object::toString)
                .reduce((acc, e) -> acc  + e)
                .get();
    }

    protected String ConvertResultToStringLong(List<Long> bitCharValues)
    {
        return bitCharValues.stream()
                .mapToInt(Long::intValue)
                .mapToObj(this::ConvertIntToChar)
                .map(Object::toString)
                .reduce((acc, e) -> acc  + e)
                .get();
    }

    protected void WriteHeader(BitOutputStream bits, String headerType, String binaryK)
    {
        AddCodeTypeHeader(bits, headerType);
        AddEncodingKHeader(bits, binaryK);
    }

    protected void AddCodeTypeHeader(BitOutputStream bits, String codeType)
    {
        for (int i = 0; i < BYTE_SIZE - codeType.length(); i++) {
            bits.write(false);
        }

        for (int i = 0; i < codeType.length(); i++) {
            bits.write(codeType.charAt(i) == '1');
        }
    }

    protected void AddEncodingKHeader(BitOutputStream bits, String binaryK)
    {
        //prevents adding binaryK for encodings w/o binary k.
        var binaryKSize = binaryK == null ? 0 : binaryK.length();

        for (int j = 0; j < BYTE_SIZE - binaryKSize; j++) {
            bits.write(false);
        }

        for (int i = 0; i < binaryKSize; i++) {
            bits.write(binaryK.charAt(i) == '1');
        }
    }

    private char ConvertIntToChar(int charValue)
    {
        return (char) charValue;
    }
}
