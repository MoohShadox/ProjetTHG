package sample;

import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Tuple {
    private int tableau[];

    public Tuple(int a, int b) {
        tableau = new int[2];
        tableau[0] = a;
        tableau[1] = b;
    }

    public boolean comparing(int a, int b){
        if(tableau[0] == a && tableau [1] == b){
            return true;
        }else{
            return false;
        }
    }
    static public boolean containing(LinkedList<Tuple> L, int a, int b){
        Iterator<Tuple> i = L.iterator();
        while (i.hasNext()){
            if(i.next().comparing(a,b)){
                return true;
            }
        }
        return false;
    }
    public int[] getTableau() {
        return tableau;
    }

    @Override
    public String toString() {
        return "Tuple{" + this.tableau[0] + ',' + this.tableau[1] +
                "} \n";
    }
}
