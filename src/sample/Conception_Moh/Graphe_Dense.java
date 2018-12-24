package sample.Conception_Moh;

import sample.Tuple;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class Graphe_Dense extends Graph{

    private int[][] matrice_adjacence;

    public Graphe_Dense(int ordre, HashSet<Tuple> arcs) {
        super(ordre, arcs);
    }

    public Graphe_Dense(int ordre, float densite) {
        super(ordre, densite);
    }

    public boolean isConnected(int i,int j){
        return  matrice_adjacence[i][j]==1;
    }

    @Override
    public void setStructure() {
        getMatrice_adjacence();
    }

    @Override
    public Collection<Integer> getPredecesseur(int n) {
        LinkedList<Integer> C = new LinkedList<>();
        for(int i=0;i<ordre;i++){
            if(matrice_adjacence[i][n]==1)
                C.add(i);
        }
        return C;
    }

    public int[][] getMatrice_adjacence() {
        if(matrice_adjacence==null){
            matrice_adjacence = new int[ordre][ordre];
            for(Tuple t:getArcs())
                matrice_adjacence[t.getTableau()[0]][t.getTableau()[1]] = 1;
        }
        for(int i =0;i<ordre;i++){
            for(int j=0;j<ordre;j++)
                System.out.print(matrice_adjacence[i][j] + "\t");
            System.out.println("");
        }
        return matrice_adjacence;
    }

}
