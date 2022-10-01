package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.floor;

public abstract class DefaultEncodeDecodeStrategy implements EncodeDecodeStrategy{

    private OperationTypeEnum operationType;
    private EncodeDecodeStrategyEnum encodeDecodeStrategy;
    private String headerType;
    private String binaryK;

    public static final int BYTE_SIZE = 8;

    public DefaultEncodeDecodeStrategy(
            OperationTypeEnum operationType,
            EncodeDecodeStrategyEnum encodeDecodeStrategy,
            String headerType,
            String binaryK)
    {
        this.operationType = operationType;
        this.encodeDecodeStrategy = encodeDecodeStrategy;
        this.headerType = headerType;
        this.binaryK = binaryK;
    }

    @Override
    public ArrayList<Boolean> GenerateHeader()
    {
        var bitsArray = new ArrayList<Boolean>();

        AddCodeTypeHeader(bitsArray, this.headerType);
        AddEncodingKHeader(bitsArray, this.binaryK);

        return bitsArray;
    }

    @Override
    public OperationTypeEnum GetOperationType() {
        return operationType;
    }

    @Override
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy() {
        return encodeDecodeStrategy;
    }

    public void Encode(byte[] file)
    {
        var header = this.GenerateHeader();
        //generate crc for header

        var body = this.GenerateBody(file);
        //generate hamming for body

        var bytes = new ByteArrayOutputStream();
        try (var bits = new DefaultBitOutputStream(bytes)) {

            header.forEach(bits::write);
            body.forEach(bits::write);

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch(Exception e)
        {
            System.out.println(
                    "Failure during " +
                            this.GetEncodeDecodeStrategy().toString() +
                            " " +
                            this.GetOperationType().toString());
        }
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

    protected void AddCodeTypeHeader(ArrayList<Boolean> bits, String codeType)
    {
        for (int i = 0; i < BYTE_SIZE - codeType.length(); i++) {
            bits.add(false);
        }

        for (int i = 0; i < codeType.length(); i++) {
            bits.add(codeType.charAt(i) == '1');
        }
    }

    protected void AddEncodingKHeader(ArrayList<Boolean> bits, String binaryK)
    {
        //prevents adding binaryK for encodings w/o binary k.
        var binaryKSize = binaryK == null ? 0 : binaryK.length();

        for (int j = 0; j < BYTE_SIZE - binaryKSize; j++) {
            bits.add(false);
        }

        for (int i = 0; i < binaryKSize; i++) {
            bits.add(binaryK.charAt(i) == '1');
        }
    }

    private char ConvertIntToChar(int charValue)
    {
        return (char) charValue;
    }
}
