package sample.Conception_Moh;

import sample.Tuple;

import java.util.*;

public abstract class Graph {

    protected int ordre = 0;
    protected HashSet<Tuple> arcs;

    public abstract boolean isConnected(int i,int j);
    public abstract void setStructure();

    public  Collection<Integer> getPredecesseur(int a){
        HashSet<Integer> liste = new HashSet<>();
        Iterator<Tuple> i =arcs.iterator();
        Tuple k;
        while(i.hasNext()){
            k=i.next();
            if(k.getTableau()[1] == a){
                liste.add(k.getTableau()[0]);
            }
        }
        return liste;
    }

    public HashSet<Integer> getListPredecesseurs(HashSet<Integer> i){
        HashSet<Integer> H = new HashSet<>();
        for (int j:i){
            H.addAll(getPredecesseur(j));
        }
        return H;
    }

    public int getOrdre() {
        return ordre;
    }



    public HashSet<Tuple> getArcs() {
        return arcs;
    }



    public void setArcs(HashSet<Tuple> arcs){
        this.arcs = arcs;

    }

    public void setDensite(float densite){
        setArcs(Core_Calculator.randomizedGraph(ordre,densite));
    }



    public Graph(int ordre,HashSet<Tuple> arcs){
        this.ordre = ordre;
        setArcs(arcs);
    }


    public Graph(int ordre,float densite){
        this.ordre = ordre;
        setDensite(densite);
    }





}
