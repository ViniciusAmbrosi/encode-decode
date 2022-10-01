package mediator;

import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import noise.NoiseStrategy;
import strategies.EncodeDecodeStrategy;
import strategies.delta.DeltaDecodeStrategy;
import strategies.delta.DeltaEncodeStrategy;
import strategies.eliasgamma.EliasGammaDecodeStrategy;
import strategies.eliasgamma.EliasGammaEncodeStrategy;
import strategies.fibonnaci.FibonacciDecodeStrategy;
import strategies.fibonnaci.FibonacciEncodeStrategy;
import strategies.golomb.GolombDecodeStrategy;
import strategies.golomb.GolombEncodeStrategy;
import strategies.unary.UnaryDecodeStrategy;
import strategies.unary.UnaryEncodeStrategy;
import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeMediator {

    List<EncodeDecodeStrategy> encodeDecodeStrategyList;


    public EncodeDecodeMediator () {
        this.encodeDecodeStrategyList = new ArrayList<>();

        this.encodeDecodeStrategyList.add(new GolombEncodeStrategy());
        this.encodeDecodeStrategyList.add(new GolombDecodeStrategy());

        this.encodeDecodeStrategyList.add(new UnaryEncodeStrategy());
        this.encodeDecodeStrategyList.add(new UnaryDecodeStrategy());

        this.encodeDecodeStrategyList.add(new DeltaEncodeStrategy());
        this.encodeDecodeStrategyList.add(new DeltaDecodeStrategy());

        this.encodeDecodeStrategyList.add(new FibonacciEncodeStrategy());
        this.encodeDecodeStrategyList.add(new FibonacciDecodeStrategy());

        this.encodeDecodeStrategyList.add(new EliasGammaEncodeStrategy());
        this.encodeDecodeStrategyList.add(new EliasGammaDecodeStrategy());
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
        else
        {
            System.out.printf(
                    "Couldn't find according strategy for operation type [%s] and encode/decode.txt strategy [%s]",
                    operationTypeEnum.toString(),
                    encodeDecodeStrategyEnum.toString());
        }
    }
}
