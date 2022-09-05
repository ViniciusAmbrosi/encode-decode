package strategies;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;

public abstract class DefaultEncodeDecodeStrategy implements EncodeDecodeStrategy{

    private OperationTypeEnum operationType;
    private EncodeDecodeStrategyEnum encodeDecodeStrategy;

    public static final int BYTE_SIZE = 8;

    public DefaultEncodeDecodeStrategy(OperationTypeEnum operationType, EncodeDecodeStrategyEnum encodeDecodeStrategy)
    {
        this.operationType = operationType;
        this.encodeDecodeStrategy = encodeDecodeStrategy;
    }

    @Override
    public OperationTypeEnum GetOperationType() {
        return operationType;
    }

    @Override
    public EncodeDecodeStrategyEnum GetEncodeDecodeStrategy() {
        return encodeDecodeStrategy;
    }
}
