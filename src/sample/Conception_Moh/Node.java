package sample.Conception_Moh;

import sample.Tuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Predicate;


public class Node {
    private LinkedList<Node> liens_sortants = new LinkedList<>();
    private LinkedList<Node> liens_entrants = new LinkedList<>();
    private int number;

    public Node(int number,Graph graphe){
        HashSet<Tuple> arcs= graphe.getArcs();
        graphe.addNoeud(this,number);
        this.number = number;
        for(Tuple t:arcs){
            if(t.getTableau()[0]==number)
            {
                liens_sortants.add(graphe.getOrCreateNoeud(t.getTableau()[1]));
            }
            if(t.getTableau()[1]==number){
                liens_entrants.add(graphe.getOrCreateNoeud(t.getTableau()[0]));
            }
        }
    }


    public void deconnecter(int n) {
        liens_entrants.removeIf(node -> node.number == n);
        liens_sortants.removeIf(node -> node.number == n);
    }

    public int getNumber() {
        return number;
    }

    //TODO Optimisation temps machine de ceci en utilisant un StringBuilder
    @Override
    public String toString() {
        String str = "Node"+number+" ";
        /*String str = "Noeud nomme " + number + " a pour precedents : ";
        for(Node n:liens_entrants){
            str += n.number + " , ";
        }*/
        return str;
    }

    public boolean isSource(){
        return liens_entrants.size()==0;
    }

    public boolean isPuit(){
        return liens_sortants.size()==0;
    }



}