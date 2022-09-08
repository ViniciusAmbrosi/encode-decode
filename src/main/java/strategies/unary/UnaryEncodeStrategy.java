package strategies.unary;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class UnaryEncodeStrategy extends UnaryStrategy{

    public UnaryEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        var bytes = new ByteArrayOutputStream();
        var charArray = new String(file).chars().toArray();

        try (var bits = new DefaultBitOutputStream(bytes)){
            WriteHeader(bits, "2", null);

            for (int charValue : charArray) {
                PopulateUnaryEncodingForChar(bits, charValue);
            }

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch (Exception ex)
        {
            System.out.println("Failure during unary encoding.");
            return false;
        }

        return true;
    }

    private void PopulateUnaryEncodingForChar(DefaultBitOutputStream bits, int charValue) {
        for (int j = 0; j < charValue; j++) {
            bits.write(false);
        }

        bits.write(true);
    }
}
