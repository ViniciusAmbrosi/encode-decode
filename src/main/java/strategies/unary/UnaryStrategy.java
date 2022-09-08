package strategies.unary;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class UnaryStrategy extends DefaultEncodeDecodeStrategy {

    public UnaryStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.UNARY);
    }
}
