package strategies.golomb;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class GolombStrategy extends DefaultEncodeDecodeStrategy {

    public GolombStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.GOLOMB);
    }
}
