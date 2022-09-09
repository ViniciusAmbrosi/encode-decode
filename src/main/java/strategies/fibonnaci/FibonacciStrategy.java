package strategies.fibonnaci;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class FibonacciStrategy extends DefaultEncodeDecodeStrategy {

    public FibonacciStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.FIBONACCI);
    }

    @Override
    public boolean EncodeDecode(byte[] file) {
        return false;
    }
}
