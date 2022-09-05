package mediator;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import strategies.EncodeDecodeStrategy;
import strategies.golomb.GolombDecodeStrategy;
import strategies.golomb.GolombEncodeStrategy;

import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeMediator {

    List<EncodeDecodeStrategy> encodeDecodeStrategyList;

    public EncodeDecodeMediator () {
        this.encodeDecodeStrategyList = new ArrayList();

        this.encodeDecodeStrategyList.add(new GolombEncodeStrategy());
        this.encodeDecodeStrategyList.add(new GolombDecodeStrategy());
    }

    public void RunOperation(
            OperationTypeEnum operationTypeEnum,
            EncodeDecodeStrategyEnum encodeDecodeStrategyEnum,
            byte[] file) {

        var matchingStrategy = encodeDecodeStrategyList.stream()
                .filter(strategy -> strategy.GetOperationType() == operationTypeEnum)
                .filter(strategy -> strategy.GetEncodeDecodeStrategy() == encodeDecodeStrategyEnum)
                .findFirst();

        if(matchingStrategy.isPresent())
        {
            matchingStrategy.get().EncodeDecode(file);
        }

        System.out.printf(
            "Couldn't find according strategy for operation type [%s] and encode/decode.txt strategy [%s]",
            operationTypeEnum.toString(),
            encodeDecodeStrategyEnum.toString());
    }
}
