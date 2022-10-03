package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import noise.CRCNoiseStrategy;
import noise.NoiseStrategy;
import org.apache.commons.io.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.lang.Math.floor;

public abstract class DefaultEncodeDecodeStrategy <T> implements EncodeDecodeStrategy <T>{

    private OperationTypeEnum operationType;
    private EncodeDecodeStrategyEnum encodeDecodeStrategy;
    private String headerType;
    private String binaryK;
    private NoiseStrategy crcStrategy = new CRCNoiseStrategy();
    private NoiseStrategy hammingStrategy;

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

    private byte[] ToByteArray(List<Boolean> list, int size)
    {
        var bitArray = new byte[size];
        var bitArrayPos = 0;

        for (Boolean bool : list) {
            bitArray[bitArrayPos++] = bool ? (byte) 1 : 0;
        }

        return bitArray;
    }

    protected void Encode(byte[] file)
    {
        var header = this.GenerateHeader();
        var body = this.GenerateBody(file);

        WriteCodFile(header, body);

        var noiseBytes = new ByteArrayOutputStream();
        try (var bits = new DefaultBitOutputStream(noiseBytes))
        {
            header.forEach(bits::write);
            var headerBytes = ToByteArray(header, 16);

            //crc
            var crcBytes= crcStrategy.encode(headerBytes);
            for (int crcBit : crcBytes) {
                bits.write(crcBit);
            }
            //crc ends

            //hamming
            for (List<T> bodyCodeword : GetBodyCodeWordsForHamming(body))
            {
                for (boolean hammingBit : GenerateHammingCodeword(bodyCodeword)) {
                    bits.write(hammingBit);
                    System.out.print(hammingBit ? "1" : "0");
                }
            }

            //hamming ends

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.ecc"),
                    noiseBytes.toByteArray());
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

    private void WriteCodFile(ArrayList<Boolean> header, ArrayList<T> body)
    {
        var bytes = new ByteArrayOutputStream();
        try (var bits = new DefaultBitOutputStream(bytes)) {

            header.forEach(bits::write);
            body.forEach(bit -> this.WriteBit(bit, bits));

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

    private ArrayList<List<T>> GetBodyCodeWordsForHamming(List<T> bodyBytes)
    {
        var bodyCodewords = new ArrayList<List<T>>();

        var currentPosition = 0;
        while(currentPosition < bodyBytes.size()){
            var lastPosition = Math.min(bodyBytes.size(), currentPosition + 4);
            var bodyCodeword = bodyBytes.subList(currentPosition, lastPosition);

            bodyCodewords.add(bodyCodeword);
            currentPosition += 4;
        }

        return bodyCodewords;
    }

    private boolean[] GenerateHammingCodeword(List<T> bodyCodeword)
    {
        var hammingCodewordsPos = 0;
        var hammingCodewords = new boolean[7];

        for (T word : bodyCodeword) {
            hammingCodewords[hammingCodewordsPos++] = GetBooleanBitValue(word);
        }

        hammingCodewords[4] = (hammingCodewords[0] ^ hammingCodewords[1] ^ hammingCodewords[2]);
        hammingCodewords[5] = (hammingCodewords[1] ^ hammingCodewords[2] ^ hammingCodewords[3]);
        hammingCodewords[6] = (hammingCodewords[0] ^ hammingCodewords[2] ^ hammingCodewords[3]);

        return hammingCodewords;
    }

    private byte[] GetBytesForBody(ArrayList<T> list)
    {
        var bytes = new ByteArrayOutputStream();
        try (var bits = new DefaultBitOutputStream(bytes))
        {
            list.forEach(bit -> this.WriteBit(bit, bits));
            return bytes.toByteArray();
        }
        catch(Exception e)
        {
            System.out.println(
                    "Failure during " +
                            this.GetEncodeDecodeStrategy().toString() +
                            " " +
                            this.GetOperationType().toString());
        }

        return new byte[0];
    }

    private char ConvertIntToChar(int charValue)
    {
        return (char) charValue;
    }
}
