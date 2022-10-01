package noise;

public interface NoiseStrategy {
    public byte[] encode(byte[] dataToEncode);
    public void decode(byte[] crcCode, byte[] dataToDecode);
}
