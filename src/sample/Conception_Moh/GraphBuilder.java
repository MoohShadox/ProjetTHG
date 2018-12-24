package sample.Conception_Moh;

import sample.Tuple;

import java.util.HashSet;

public class GraphBuilder {
    private Graph G = null;
    private int ordre = 0;
    private final int pourcentage_creux = 50;

    public GraphBuilder(int ordre){
        this.ordre = ordre;
    }

    public GraphBuilder withDensite(float densite){
        if(densite*100<pourcentage_creux)
            G = new Graphe_Creux(ordre,densite);
        else
            G = new Graphe_Dense(ordre,densite);
        return this;
    }

    public GraphBuilder withTuples(HashSet<Tuple> tuples){
        float densite = 2*tuples.size()/((ordre-1)*ordre);
        if(densite*100<pourcentage_creux)
            G = new Graphe_Creux(ordre,tuples);
        else
            G = new Graphe_Dense(ordre,tuples);
        return this;
    }

    public Graph build(){
        return G;
    }


}
