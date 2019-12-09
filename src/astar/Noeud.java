package astar;

import java.util.*;

public class Noeud implements Comparable {

    private int[] vals = null;

    public int[] getVals() {
        return vals;
    }

    private int but = -1;

    public int getBut() {
        return but;
    }

    private Antecedent antecedent = null;

    public Antecedent getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(Antecedent antecedent) {
        this.antecedent = antecedent;
    }


    public Noeud(int[] vals, int but, Antecedent antecedent) {

        this.vals = vals;
        this.but = but;
        this.antecedent = antecedent;

    }

    public Noeud(int[] vals, int but) {

        this(vals, but, null);

    }

    public ArrayList<Noeud> getSucc() {

        ArrayList<Noeud> out = new ArrayList<>();
        int len = this.vals.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {

                lancerOperation(i, j, Operation.PLUS, out);
                lancerOperation(i, j, Operation.FOIS, out);
                lancerOperation(i, j, Operation.MOINS, out);
//                Calculateur division = Operation.DIV ;
                lancerOperation(i, j, Operation.DIV, out);
                lancerOperation(j, i, Operation.DIV, out);


            }
        }

        return out;

    }

    private void lancerOperation(int index1, int index2, Operation operation, ArrayList<Noeud> out) {

        int len = this.vals.length;
        int[] new_vals = new int[len - 1];
        for (int k = 0, m = 0; k < len && m < len - 2; k++) // copie des données
            if (k != index1 && k != index2) new_vals[m++] = vals[k];

        try {

            int a = vals[index1], b = vals[index2];
            new_vals[len - 2] = operation.getCalculateur().calculer(a, b);
            Noeud fils = new Noeud(new_vals, this.getBut(), new Antecedent(this, operation, a, b, new_vals[len - 2]));
            out.add(fils);

        } catch (ArithmeticException opEx) {
        }


    }

    public ArrayList<Noeud> lancerAStar() {

        PriorityQueue<Noeud> open_list = new PriorityQueue<>();
        ArrayList<Noeud> close_list = new ArrayList<>();

        open_list.addAll(this.getSucc());

        while (!open_list.isEmpty()) {

            Noeud best_node = open_list.poll();
            close_list.add(best_node);
            if (best_node.isFinished()) {

                return best_node.retroGetPred();

            }

            best_node.getSucc().stream().filter(elmt -> !open_list.contains(elmt) && !close_list.contains(elmt))
                    .forEach(elmt -> open_list.add(elmt));


        }

        return null;

    }

    public ArrayList<ArrayList<Noeud>> lancerAStar(int nbrSolution, Writer writer) {

        ArrayList<ArrayList<Noeud>> out = new ArrayList<>(nbrSolution) ;

        PriorityQueue<Noeud> open_list = new PriorityQueue<>();
        ArrayList<Noeud> close_list = new ArrayList<>();

        open_list.addAll(this.getSucc());

        for (int i = 0; !open_list.isEmpty() && i<nbrSolution;) {

            Noeud best_node = open_list.poll();
            close_list.add(best_node);
            if (best_node.isFinished()) {

                ArrayList<Noeud> new_solution = best_node.retroGetPred() ;
                boolean exist = false ;
                for (ArrayList<Noeud> noeuds : out) {
                    exist = false ;
                    for (int j = 0; j < noeuds.size()-1; j++) {//contains all but the last
                        if (new_solution.contains(noeuds.get(j)) || noeuds.get(j).isContainedBy(new_solution)) {
                            exist = true;
                        }
                    }
                    if (exist) break ;
                }

                if (!exist) {
                    out.add( new_solution ) ;
                    if (writer != null) writer.write(new_solution, i);
                    i++ ;
                }

            }

            else best_node.getSucc().stream().filter(elmt -> !open_list.contains(elmt) && !close_list.contains(elmt))
                    .forEach(elmt -> open_list.add(elmt));


        }

        return out;

    }

    public ArrayList<ArrayList<Noeud>> lancerAStar(int nbrSolution) {

        return this.lancerAStar(nbrSolution, null) ;

    }

    private ArrayList<Noeud> retroGetPred() {

        HashMap<Integer,  Integer> nbr_occur = new HashMap<>() ;//pour compter le nombre de répétition
        ArrayList<Noeud> out_reverse = new ArrayList<>();
        Noeud temp = this ;
        while (temp != null && temp.getAntecedent() != null) {

            int clef = temp.getAntecedent().getResult() ;
            nbr_occur.put(clef, nbr_occur.getOrDefault(clef, 0) +  1) ;

            out_reverse.add(temp);
            temp = temp.getAntecedent().getPred() ;

        }

        int len = out_reverse.size() ;
        ArrayList<Noeud> out = new ArrayList<>();
        for (int i = len - 1; i >= 0; i--) {

            int val_i = out_reverse.get(i).getAntecedent().getResult() ;
            for (int j = i - 1; j >= 0 ; j--) {

                int ant_1 = out_reverse.get(j).getAntecedent().getVal1(),
                    ant_2 = out_reverse.get(j).getAntecedent().getVal2();
                if (val_i == ant_1 || val_i == ant_2) {
                    int new_val = nbr_occur.get(val_i) - 1;
                    nbr_occur.put(val_i, new_val) ;
                }

            }
            if (nbr_occur.get(val_i) == 0 || i == 0) //on enleve les éléments inutiles
                out.add(out_reverse.get(i)) ;

        }



        return out ;

    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {

        Noeud autre = (Noeud) o ;

        if (autre.getBut() != this.getBut()) return false ;
        if (autre.getVals().length != this.getVals().length) return false ;
        if (!this.sameAntecedentProc(autre)) return false ;

        return Arrays.stream(this.getVals()).
                allMatch(e -> Arrays.stream(autre.getVals()).anyMatch(e2-> e == e2)) ;


    }

    public boolean isContainedBy(ArrayList<Noeud> container) {

        for (Noeud elmt : container) {
            if (elmt.sameAntecedentProc(this)) return true ;
        }
        return false ;

    }

    public boolean sameAntecedent(Noeud autre) {

        if (autre.getAntecedent() == null && this.getAntecedent() == null) return true ;
        else return autre.getAntecedent().equals(this.getAntecedent())  ;

    }

    public boolean sameAntecedentProc(Noeud autre) {

        if (autre.getAntecedent() == null && this.getAntecedent() == null) return true ;
        else return autre.getAntecedent().sameProc(this.getAntecedent()) ;

    }

    @Override
    public int compareTo(Object o) {//zay kely no prioritaire

        Noeud autre = (Noeud) o ;
        return  this.getHeuristique() - autre.getHeuristique() ;

    }

    private int getTotal() {

        int out = 0 ;
        for (int i = 0; i < vals.length; i++) {
            out += vals[i] ;
        }
        return out ;

    }

    public int getHeuristique() {

        int min = 0 ;
        for (int i = 1; i < this.getVals().length; i++) {

            int old = Math.abs(vals[min] - getBut()),
                vao = Math.abs(vals[i] - getBut());

            if (old>vao) min = i ;
        }

        return Math.abs(vals[min] - getBut()) ;
//        return Math.abs(this.getTotal() - this.getBut()) ;

    }

    public boolean isFinished() {

        return Arrays.stream(this.vals).anyMatch(e -> e == this.getBut()) ;

    }

    @Override
    public String toString() {
        return "Noeud{" +
                "vals=" + Arrays.toString(vals) +
                ", but=" + but +
                ", antecedent:" + antecedent +
                '}';
    }

    public class Antecedent{

        private Noeud pred = null ;
        private Operation operation = null ;
        private int val1, val2, result;

        public Antecedent(Noeud pred, Operation operation, int val1, int val2, int res) {

            this.pred = pred;
            this.operation = operation;
            this.val1 = val1;
            this.val2 = val2;
            this.result = res ;

        }

        @Override
        public boolean equals(Object o) {

            Antecedent autre = (Antecedent) o ;

            return this.pred.equals(autre.pred) && this.operation.equals(autre.operation)
                    && ((val1 == autre.val1 && val2 == autre.val2) ||(val1 == autre.val2 && val2 == autre.val1)) ;

        }

        public boolean sameProc(Antecedent autre) {

            return this.operation.equals(autre.operation)
                    && ((val1 == autre.val1 && val2 == autre.val2) ||(val1 == autre.val2 && val2 == autre.val1)) ;

        }


        @Override
        public String toString() {

            int a = Math.min(val1, val2),
                b = Math.max(val1, val2);
            return String.format("%d %s %d = %d", b, operation.toString(), a, result) ;

        }


        public Noeud getPred() {
            return pred;
        }

        public void setPred(Noeud pred) {
            this.pred = pred;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getVal1() {
            return val1;
        }

        public void setVal1(int val1) {
            this.val1 = val1;
        }

        public int getVal2() {
            return val2;
        }

        public void setVal2(int val2) {
            this.val2 = val2;
        }
    }

}
