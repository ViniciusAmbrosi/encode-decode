package noise;

import htsjdk.samtools.cram.io.DefaultBitInputStream;
import htsjdk.samtools.cram.io.DefaultBitOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CRCNoiseStrategy extends AbstractNoiseStrategy{

    private static final byte[] CRC8 = {1, 0, 0, 0, 0, 0, 1, 1, 1}; //x^8 + x^2 + x + 1 = 1 0000 0111

    @Override
    public byte[] encode(byte[] dataToEncode) {
        int[] data = mergeArrays(dataToEncode, CRC8);

        for (int i = 0; i <= data.length - CRC8.length; ) {

            for (int j = 0; j < CRC8.length; j++) {
                int bit = data[i + j] ^ CRC8[j];
                data[i + j] = bit;
            }

            while (i < data.length && data[i] != 1) {
                i++;
            }
        }

        var bytes = new ByteArrayOutputStream();
        try (var bits = new DefaultBitOutputStream(bytes)){
            for (int i = data.length - CRC8.length + 1; i < data.length; i++) {
                bits.write(data[i]);
            }
        }

        return bytes.toByteArray();
    }

    public int[] mergeArrays(byte[] array, byte[] secondArray)
    {
        int mergePos = 0;
        int[] mergedData = new int[(array.length + secondArray.length) - 1];

        for (byte value : array) {
            mergedData[mergePos++] = value;
        }
        for (byte value : secondArray) {
            mergedData[mergePos++] = value;
        }

        return mergedData;
    }

    @Override
    public void decode(byte[] crcCode, byte[] dataToDecode) {
        ByteArrayInputStream outputBytes = new ByteArrayInputStream(encode(dataToDecode));

        try(var bits = new DefaultBitInputStream(outputBytes))
        {
            for (int i = 0; i < crcCode.length; i++) {
                boolean bit = bits.readBit();
                if ((bit ? 1 : 0) != crcCode[i]) {
                    throw new IllegalArgumentException("The CRC doesn't match.");
                }
            }
        } catch (IOException e) {
            System.out.println("Failure during crc decoding.");
        }
    }
}
