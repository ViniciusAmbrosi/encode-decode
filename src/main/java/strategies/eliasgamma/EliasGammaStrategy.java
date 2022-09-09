package strategies.eliasgamma;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.DefaultEncodeDecodeStrategy;

public abstract class EliasGammaStrategy extends DefaultEncodeDecodeStrategy {

    public EliasGammaStrategy(OperationTypeEnum operationType) {
        super(operationType, EncodeDecodeStrategyEnum.ELIASGAMMA);
    }
}
