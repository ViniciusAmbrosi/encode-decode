package strategies.delta;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;

public class DeltaEncodeStrategy extends DeltaStrategy{

    public DeltaEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        var charArray = new String(file).chars().toArray();

        try(var bits = new DefaultBitOutputStream(bytes)){
            WriteHeader(bits, "4", null);

            for (int value : charArray) {
                int len, lengthOfLen = 0;

                len = 1 + CalculateBinaryLog(value);  // calculate 1+floor(log2(num))
                lengthOfLen = CalculateBinaryLog(len); // calculate floor(log2(len))

                for (int i = lengthOfLen; i > 0; --i)
                    bits.write(0);
                for (int i = lengthOfLen; i >= 0; --i)
                    bits.write((len >> i) & 1);
                for (int i = len-2; i >= 0; i--)
                    bits.write((value >> i) & 1);
            }

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch(Exception ex)
        {
            System.out.println("Failure during delta encoding.");
            return false;
        }

        return true;
    }
}
