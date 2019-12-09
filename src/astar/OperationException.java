package astar;

public class OperationException extends ArithmeticException{

    public OperationException() {

        super("Operation inutile") ;

    }

    public OperationException(String s) {
        super(s);
    }
}
