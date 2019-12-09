import astar.Calculateur;
import astar.Noeud;
import astar.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Test {

    public static void main(String[] args) {


//        testHeuristique();
        testMatch();

    }

    static void testMatch() {

        int[] t = {1, 5, 25, 290} ;
        int[] t2 = {5, 25, 1, 29} ;
        System.out.println(Arrays.stream(t).allMatch(e -> Arrays.stream(t2).anyMatch(e2 -> e == e2)));


    }

    static void testGetOrDefault() {

        HashMap<Integer, Integer> nn = new HashMap<>() ;
        nn.put(0, 52) ;
        System.out.println(nn.getOrDefault(40, 0));
        System.out.println(nn.getOrDefault(0, 0));

    }

    static void testMain() {
        //        Noeud c = new Noeud(new int[]{25, 1, 10, 4}, 291) ;
//        ArrayList<Noeud> result = c2.lancerAStar() ;
//        result.stream().forEach(e-> System.out.println(e));
        Noeud c2 = new Noeud(new int[]{1, 4, 5, 75, 25, 10}, 291) ;
        ArrayList<ArrayList<Noeud>> results = c2.lancerAStar(3) ;
        results.stream()
                .forEach(e->{
                    e.stream().forEach(e2->System.out.println(e2.toString()));
                    System.out.println("---------------------");
                } );
    }

    static void testHeuristique() {

        Noeud n = new Noeud(new int[]{1, 290, 20}, 291) ;
        Noeud n2 = new Noeud(new int[]{291, 5, 25}, 291) ;

        PriorityQueue<Noeud> pn = new PriorityQueue<>() ;
        pn.add(n) ;
        pn.add(n2) ;

        System.out.println(pn.poll());
    }

    static void testContainsAll() {

        ArrayList<Noeud> ar1 = new ArrayList<>();
        ArrayList<Noeud> ar2 = new ArrayList<>();
        ar1.add(new Noeud(new int[]{1, 5, 25, 10, 300}, 291));
        ar1.add(new Noeud(new int[]{1, 5, 25, 290}, 291));
        ar1.add(new Noeud(new int[]{5, 25, 291}, 291));

        ar2.add(new Noeud(new int[]{1, 5, 25, 10, 300}, 291));
        ar2.add(new Noeud(new int[]{1, 5, 25, 290}, 291));
        ar2.add(new Noeud(new int[]{1, 290, 30}, 291));
        ar2.add(new Noeud(new int[]{30, 291}, 291));

    }


}
