package strategies.golomb;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GolombEncodeStrategy extends GolombStrategy{

    public GolombEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        var bytes = new ByteArrayOutputStream();

        try(var bits = new DefaultBitOutputStream(bytes))
        {
            WriteHeader(bits, "1", Integer.toBinaryString(ENCODING_K));

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
}
