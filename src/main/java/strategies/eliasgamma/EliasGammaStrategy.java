package strategies.eliasgamma;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class EliasGammaStrategy extends DefaultEncodeDecodeStrategy <Boolean> {

    public EliasGammaStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.ELIASGAMMA,"16", null);
    }

    @Override
    public boolean GetBooleanBitValue(Boolean bit) {
        return bit;
    }
}
