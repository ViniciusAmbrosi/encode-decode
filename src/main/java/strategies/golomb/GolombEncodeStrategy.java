package strategies.golomb;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.BitOutputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class GolombEncodeStrategy extends GolombStrategy{

    //private static final boolean STOP_BIT = Boolean.TRUE;

    public GolombEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        var bytes = new ByteArrayOutputStream();

        try(var bits = new DefaultBitOutputStream(bytes))
        {
            writeHeader(bits);

            var charArray = new String(file).chars().toArray();

            for (int charValue : charArray) {
                var quotient = Math.floor(charValue / ENCODING_K);
                var rest = charValue % ENCODING_K;

                GenerateCodeWord(bits, quotient, rest);
            }

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch(Exception e)
        {
            System.out.println("Failure during golomb encoding.");
            return false;
        }

        return true;
    }

    //generate <Quotient Code><Rest Code>
    private void GenerateCodeWord(DefaultBitOutputStream bits, double quotient, int rest)
    {
        GenerateQuotient(bits, quotient);
        GenerateRest(bits, rest);
    }

    private void GenerateQuotient(DefaultBitOutputStream bits, double quotient)
    {
        for (int j = 0; j < quotient; j++) {
            bits.write(false);
        }

        bits.write(true);
    }

    private void GenerateRest(DefaultBitOutputStream bits, int rest)
    {
        String restString = Integer.toBinaryString(rest);

        for (int j = 0; j < BINARY_LOG_OF_K - restString.length(); j++) {
            bits.write(false);
        }

        for (int j = 0; j < restString.length(); j++) {
            bits.write(restString.charAt(j) == '1');
        }
    }

    private void writeHeader(BitOutputStream bits) {
        String header = "1";
        String binaryK = Integer.toBinaryString(ENCODING_K);

        AddCodeTypeHeader(bits, header);
        AddEncodingKHeader(bits, binaryK);
    }

    private void AddCodeTypeHeader(BitOutputStream bits, String codeType)
    {
        for (int i = 0; i < BYTE_SIZE - codeType.length(); i++) {
            bits.write(false);
        }

        for (int i = 0; i < codeType.length(); i++) {
            bits.write(codeType.charAt(i) == '1');
        }
    }

    private void AddEncodingKHeader(BitOutputStream bits, String binaryK)
    {
        for (int j = 0; j < BYTE_SIZE - binaryK.length(); j++) {
            bits.write(false);
        }

        for (int i = 0; i < binaryK.length(); i++) {
            bits.write(binaryK.charAt(i) == '1');
        }
    }
}
