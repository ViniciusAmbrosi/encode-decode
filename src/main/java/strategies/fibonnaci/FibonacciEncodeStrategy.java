package strategies.fibonnaci;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FibonacciEncodeStrategy extends FibonacciStrategy {

    public FibonacciEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        var bytes = new ByteArrayOutputStream();

        try(var bits = new DefaultBitOutputStream(bytes)) {
            WriteHeader(bits, "8", null);

            var charArray = new String(file).chars().toArray();

            //actual code

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch (Exception ex)
        {
            System.out.println("Failure during fibonacci encoding.");
            return false;
        }

        return true;
    }
}
