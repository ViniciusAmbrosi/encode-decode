package noise;

import files.FileUtilsWrapper;

public abstract class AbstractNoiseStrategy implements NoiseStrategy{

    private static files.FileUtilsWrapper fileUtilsWrapper = new FileUtilsWrapper();

    public byte[] LoadEncodedFile(String fileSource)
    {
        byte[] file = fileUtilsWrapper.ReadFromFile(fileSource);
        return file;
    }
}
