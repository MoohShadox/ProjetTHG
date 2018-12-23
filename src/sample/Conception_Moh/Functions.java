package sample.Conception_Moh;

import sample.Conception_Moh.Node;
import sample.Tuple;

import java.util.*;

public class Functions {

    //Todo corriger l'ordre qui est de plus 1
    public static HashSet<Tuple> randomizedGraph(int ordre, float dansité){
        HashSet<Tuple> aleagraphe = new HashSet<Tuple>();
        LinkedList<Integer> M[] = new LinkedList[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new LinkedList<>();
        }
        Random rand = new Random();
        int arretes = 0;
        int debut, svt,j;
        while ( (arretes+1f)/ordre < dansité){
            debut = rand.nextInt(ordre);
            int nb_max = ordre*(ordre-1);
            while(rand.nextInt(10)<7 && (arretes+1f)/nb_max < dansité){
                svt = rand.nextInt(ordre);
                while(svt == debut || M[debut].contains(svt)){
                    svt = rand.nextInt(ordre);
                }
                aleagraphe.add(new Tuple(debut,svt));
                arretes++;
                j= 0;
                M[svt].add(debut);
                while(  j<M[debut].size()){
                    M[svt].add(M[debut].get(j));
                    j++;
                }
                debut = svt;
            }
        }
        return aleagraphe;
    }

    public static LinkedList<Integer> sources(HashSet<Tuple> L, int ordre){
        /*
        déclaration, allocation et remplissage de la liste des sommets
         */
        LinkedList<Integer> liste = new LinkedList<>();
        for(int i=0;i<ordre;i++){
            liste.add(i);
        }
        Iterator<Tuple> t = L.iterator();
        Tuple tup;
        /*
        on parcours le hashset de graphe afin d'enlever les elements qui ont des arcs entrant
         */
        while (t.hasNext()){
            tup=t.next();
            /*
            si la liste contient encore l'element entrant du tuple l'enlever de la liste
             */
            if(liste.contains(tup.getTableau()[1])){
                liste.remove((Integer)tup.getTableau()[1]);
            }

        }
        return liste;
    }
    /*
    methode retournant un tableau de liste ou l'indice est le sommet et l'element est une liste des suivants de ce sommet
     */

    public static LinkedList<Integer>[] suivants(HashSet<Tuple> L, int ordre){
        /*
        allocation du tableau de liste qu'on retournera plus tard
         */
        LinkedList<Integer> M[] = new LinkedList[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new LinkedList<>();

        }
        Iterator<Tuple> t = L.iterator();
        Tuple tup;
        /*
        parcour du tuple a chauqe tuple de type (debut,suivant) on on inserera le suivant dans la liste du debut
        M[debut].add(suivant)
         */
        while (t.hasNext()){
            tup=t.next();
            M[tup.getTableau()[0]].add(tup.getTableau()[1]);
        }
        /*
        on retournera ensuite ce tablau
         */
        return M;
    }


    public static int[] niveaux(HashSet<Tuple> L,int ordre){
        long current = System.currentTimeMillis();
        int M[] = new int[ordre];
        LinkedList<Integer> debuts = sources(L, ordre);
        LinkedList<Integer>[] suivants = suivants(L, ordre);
        LinkedList<Integer> nouvdeb = new LinkedList<>();
        nouvdeb.addAll(debuts);
        Iterator<Integer> to;
        int j=1;
        int opo;
        int lr=0;
        int kl;
        while ( lr<ordre){
            to=debuts.iterator();
            while (to.hasNext()){
                opo= to.next();
                Iterator<Integer> tt = suivants[opo].iterator();
                while (tt.hasNext()){
                    kl=tt.next();
                    nouvdeb.add(kl);
                    M[kl] = j;
                }
            }
            debuts.clear();
            debuts.addAll(nouvdeb);
            nouvdeb.clear();
            j++;
            lr++;

        }
        current= System.currentTimeMillis()-current;
        System.out.println("la decomposition aura pris " + current);
        return M;
    }

    Graph G ;
    HashSet<Tuple> t = new HashSet<>();


    //Test Unitaire
    public static void main(String[] str){
        HashSet<Tuple> t = new HashSet<>();
        t.add(new Tuple(1,2));
        t.add(new Tuple(1,3));
        t.add(new Tuple(3,4));
        t.add(new Tuple(4,5));
        t.add(new Tuple(4,6));
        t.add(new Tuple(2,4));
        Graph G = new Graph(20,0.95f);
        System.out.println(niveaux(G.getArcs(),20));
        System.out.println(G.decomposerNiveaux());
    }
}
