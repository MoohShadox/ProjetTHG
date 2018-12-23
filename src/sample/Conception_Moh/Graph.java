package sample.Conception_Moh;

import sample.Tuple;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Graph {
    private HashMap<Integer,Node> noeud_graphe = new HashMap<>();
    private int ordre = 0;
    private HashSet<Tuple> arcs;

    public Graph(int ordre,HashSet<Tuple> arcs){
        this.arcs = arcs;
        this.ordre = ordre;
        for(Tuple t:arcs){
            if(t.getTableau()[0] < ordre)
                getOrCreateNoeud(t.getTableau()[0]);
        }
        for(int i=1;i<=ordre;i++){
            getOrCreateNoeud(i);
        }
    }

    public Graph(int ordre,float densite){
        this.arcs = Functions.randomizedGraph(ordre,densite);
        this.ordre = ordre;
        for(Tuple t:arcs){
            if(t.getTableau()[0] < ordre)
                getOrCreateNoeud(t.getTableau()[0]);
        }
        for(int i=1;i<=ordre;i++){
            getOrCreateNoeud(i);
        }
    }


    public Graph copyGraph(){
        return new Graph(ordre,arcs);
    }

    public HashSet<Tuple> getArcs() {
        return arcs;
    }

    public void addNoeud(Node n, int number){
        noeud_graphe.put(number,n);
    }

    public Node getNoeud(int number){
        return noeud_graphe.get(number);
    }

    public Node getOrCreateNoeud(int number){
        if(number>ordre)
            return null;
        if(!noeud_graphe.containsKey(number))
        {
            noeud_graphe.put(number,new Node(number,this));
        }
        return getNoeud(number);
    }

    public LinkedList<Node> listeSources(){
        LinkedList<Node> L = new LinkedList<>();
        for(Integer i:noeud_graphe.keySet()){
            if(noeud_graphe.get(i).isSource())
                L.add(noeud_graphe.get(i));
        }
        return L;
    }

    public void supprimerNoeud(Node n){
        if(ordre==0)
            {
                System.out.println("Impossible de supprimer un noeud d'un graphe deja vide");
                return;
            }
        for(Integer i:noeud_graphe.keySet()){
            noeud_graphe.get(i).deconnecter(n.getNumber());
        }
        noeud_graphe.remove(n.getNumber());
        ordre = ordre - 1;
    }

    public HashMap<Integer,HashSet<Node>> decomposerNiveaux(){
        long current = System.currentTimeMillis();
        HashMap<Integer,HashSet<Node>> ll = new HashMap<>();
        Graph G = this.copyGraph();
        LinkedList<Node> liste_sources;
        int cpt= 0;
        while(G.ordre > 0)
        {
            liste_sources = G.listeSources();
            for (Node n:liste_sources)
                G.supprimerNoeud(n);
            ll.put(cpt,new HashSet<>(liste_sources));
            cpt = cpt + 1;
        }
        current= System.currentTimeMillis()-current;
        System.out.println("la decomposition aura pris " + current);
        return ll;
    }


    @Override
    public String toString() {
        String str = "";
        for(Integer i:noeud_graphe.keySet()){
            str += (noeud_graphe.get(i).toString()) + "\n";
        }
        return str;
    }


}
