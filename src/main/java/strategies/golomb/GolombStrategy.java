package strategies.golomb;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class GolombStrategy extends DefaultEncodeDecodeStrategy {

    protected static final int ENCODING_K = 4;
    protected final int BINARY_LOG_OF_K = CalculateBinaryLogOfK(ENCODING_K);

    public GolombStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.GOLOMB, "1", Integer.toBinaryString(ENCODING_K));
    }

    protected int CalculateBinaryLogOfK(int k)
    {
        return CalculateBinaryLog(ENCODING_K);
    }
}
