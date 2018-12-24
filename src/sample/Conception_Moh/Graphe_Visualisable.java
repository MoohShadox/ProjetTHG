package sample.Conception_Moh;

import sample.Tuple;

import java.util.HashMap;
import java.util.HashSet;

public class Graphe_Visualisable extends Graph {

    protected HashMap<Integer,Node> noeud_graphe = new HashMap<>();

    public Node getOrCreateNoeud(int number){
        if(number>ordre)
            return null;
        if(!noeud_graphe.containsKey(number))
        {
            noeud_graphe.put(number,new Node(number,this));
        }
        return getNoeud(number);
    }

    public void addNoeud(Node n, int number){
        noeud_graphe.put(number,n);
    }

    public Node getNoeud(int number){
        return noeud_graphe.get(number);
    }

    public void SetReferences(){
        for(Tuple t:arcs){
            if(t.getTableau()[0] < ordre)
                getOrCreateNoeud(t.getTableau()[0]);
        }
        for(int i=1;i<=ordre;i++){
            getOrCreateNoeud(i);
        }
    }


    public Graphe_Visualisable(int ordre, HashSet<Tuple> arcs) {
        super(ordre, arcs);
    }

    public Graphe_Visualisable(int ordre, float densite) {
        super(ordre, densite);
    }

    @Override
    public boolean isConnected(int i, int j) {
        return false;
    }

    @Override
    public void setStructure() {
        SetReferences();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Integer i:noeud_graphe.keySet()){
            str.append(noeud_graphe.get(i).toString()).append("\n");
        }
        return str.toString();
    }

}
