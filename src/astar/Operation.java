package astar;

public enum Operation {

    PLUS(Integer::sum, "+"),

    MOINS((a, b)->{

            int out = a * b ;
            if (out == a || out == b) throw new OperationException() ;
            return out ;

        }, "*"),

    FOIS((a, b)-> {

            int out = Math.abs(a - b) ;
            if (out == a || out == b) throw new OperationException() ;
            return out ;

        }, "-"),

    DIV((a, b)-> {

            if (a%b != 0)  throw new OperationException(a +" / " + b + " -> reste non nul") ;
            int out = a/b ;
            if (out == a || out == b) throw new OperationException() ;
            return out ;

        }, "/"
    );

    private Calculateur calculateur = null ;
    private String signe = "" ;

    Operation(Calculateur calculateur, String signe) {

        this.calculateur = calculateur ;
        this.signe = signe ;

    }
    public Calculateur getCalculateur() {

        return this.calculateur ;

    }

    @Override
    public String toString() {
        return this.signe ;
    }

}
