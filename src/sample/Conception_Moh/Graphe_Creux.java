package sample.Conception_Moh;

import sample.Tuple;

import java.util.*;

public class Graphe_Creux extends Graph {
    private int[] PS;
    private int[] LS;

    public Graphe_Creux(int ordre, HashSet<Tuple> arcs) {
        super(ordre, arcs);
    }

    public Graphe_Creux(int ordre, float densite) {
        super(ordre, densite);
    }

    @Override
    public boolean isConnected(int i, int j) {
        for (int w=PS[i];w<PS[i+1];w++){
            if(LS[w]==j)
                return true;
        }
        return false;
    }

    public HashSet<Integer> getSources(){
        //TODO Definir une methode qui retourne les sources en utilisant une liste
        return null;
    }


    public HashSet<Integer> getSuccesseurs(int sommet){
        //TODO definir une methode qui retourne les successeurs en utilisant une liste
        return null;
    }




    @Override
    public void setStructure() {
        PS = new int[ordre+1];
        LS = new int[arcs.size()+1];
        SortedSet<Tuple> st = new TreeSet<>(arcs);
        Iterator<Tuple> I = st.iterator();
        Tuple tuple_courant = I.next();
        int cpt =0;
        for (int i=0;i<ordre;i++){
            PS[i] = cpt;
            while(tuple_courant!=null && tuple_courant.getTableau()[0]==i){
                LS[cpt] = tuple_courant.getTableau()[1];
                cpt++;
                if(I.hasNext())
                    tuple_courant = I.next();
                else
                    break;
            }
        }
    }

    @Override
    public Collection<Integer> getPredecesseur(int n) {
        HashSet<Integer> H=new HashSet<>();
        for(int i=0;i<ordre;i++){
            for(int j=PS[i];j<PS[i+1];j++){
                if(LS[j]==n)
                {
                    H.add(j);
                    break;
                }
            }
        }
        return H;
    }


}
