package strategies.fibonnaci;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class FibonacciStrategy extends DefaultEncodeDecodeStrategy <Boolean> {

    public FibonacciStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.FIBONACCI,"8", null);
    }
}
