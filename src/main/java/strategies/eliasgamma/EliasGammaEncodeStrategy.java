package strategies.eliasgamma;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class EliasGammaEncodeStrategy extends EliasGammaStrategy {

    public EliasGammaEncodeStrategy() {
        super(OperationTypeEnum.ENCODE);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        var bytes = new ByteArrayOutputStream();

        try(var bits = new DefaultBitOutputStream(bytes)) {
            WriteHeader(bits, "16", null);

            var charArray = new String(file).chars().toArray();

            //actual code

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.cod"),
                    bytes.toByteArray());
        }
        catch (Exception ex)
        {
            System.out.println("Failure during elias gamma encoding.");
            return false;
        }

        return true;
    }
}
