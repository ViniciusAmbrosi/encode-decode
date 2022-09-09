import enumerators.EncodeDecodeStrategyEnum;
import enumerators.OperationTypeEnum;
import files.FileUtilsWrapper;
import mediator.EncodeDecodeMediator;

public class App {

    private static EncodeDecodeMediator encodeDecodeMediator = new EncodeDecodeMediator();
    private static files.FileUtilsWrapper fileUtilsWrapper = new FileUtilsWrapper();

    //arg 0 - source file path
    //arg 1 - operation type
    //arg 2 - encode decode.txt strategy
    public static void main(String[] args) throws Exception {
        OperationTypeEnum operationTypeEnum = ConvertOperationType(args[1]);
        EncodeDecodeStrategyEnum encodeDecodeStrategyEnum = ConvertEncodeDecodeStrategy(args[2]);
        byte[] file = fileUtilsWrapper.ReadFromFile(args[0]);

        encodeDecodeMediator.RunOperation(
                operationTypeEnum,
                encodeDecodeStrategyEnum,
                file);
    }

    private static OperationTypeEnum ConvertOperationType (String value)
    {
        try
        {
            return OperationTypeEnum.valueOf(value);
        }
        catch (Exception e)
        {
            System.out.println("Couldn't resolve operation type, doing fallback to encode.");
            return OperationTypeEnum.ENCODE;
        }
    }

    private static EncodeDecodeStrategyEnum ConvertEncodeDecodeStrategy (String value)
    {
        try
        {
            return EncodeDecodeStrategyEnum.valueOf(value);
        }
        catch (Exception e)
        {
            System.out.println("Couldn't resolve encode / decode.txt strategy, doing fallback to golomb.");
            return EncodeDecodeStrategyEnum.GOLOMB;
        }
    }
}
