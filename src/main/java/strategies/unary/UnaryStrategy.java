package strategies.unary;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class UnaryStrategy extends DefaultEncodeDecodeStrategy <Boolean> {

    public UnaryStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.UNARY, "2", null);
    }
}
