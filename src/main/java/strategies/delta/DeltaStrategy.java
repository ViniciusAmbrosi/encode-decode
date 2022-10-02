package strategies.delta;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class DeltaStrategy extends DefaultEncodeDecodeStrategy <Integer> {

    public DeltaStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.DELTA,"4", null);
    }

    @Override
    public boolean GetBooleanBitValue(Integer bit) {
        return bit == 1;
    }
}
