package strategies.delta;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class DeltaStrategy extends DefaultEncodeDecodeStrategy {

    public DeltaStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.DELTA,"4", null);
    }
}
