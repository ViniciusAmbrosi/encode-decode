package strategies.golomb;

import enumerators.OperationTypeEnum;
import htsjdk.samtools.cram.io.BitOutputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class GolombEncodeStrategy extends GolombStrategy{

    //private static final boolean STOP_BIT = Boolean.TRUE;
    private static final int k = 4;

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
                int remainder = charValue % k;
                double quotient = Math.floor(charValue / k);

                for (int j = 0; j < quotient; j++) {
                    bits.write(false);
                }

                bits.write(true);

                int length = (int) (Math.log10(k) / Math.log10(2));
                String remainderInBinary = Integer.toBinaryString(remainder);

                for (int j = 0; j < length - remainderInBinary.length(); j++) {
                    bits.write(false);
                }

                for (int j = 0; j < remainderInBinary.length(); j++) {
                    bits.write(remainderInBinary.charAt(j) == '1');
                }

            }

            FileUtils.writeByteArrayToFile(
                    new File("C:\\Project\\encoder\\encoder\\resources\\encode.ecc"),
                    bytes.toByteArray());
        }
        catch(Exception e)
        {
            System.out.println("Failure during golomb encoding.");
            return false;
        }

        return true;
    }

    private void writeHeader(BitOutputStream bits) {
        String header = "1";

        for (int i = 0; i < BYTE_SIZE - header.length(); i++) {
            bits.write(false);
        }

        for (int i = 0; i < header.length(); i++) {
            bits.write(header.charAt(i) == '1');
        }

        String binaryK = Integer.toBinaryString(k);

        for (int j = 0; j < BYTE_SIZE - binaryK.length(); j++) {
            bits.write(false);
        }

        for (int i = 0; i < binaryK.length(); i++) {
            bits.write(binaryK.charAt(i) == '1');
        }
    }
}
