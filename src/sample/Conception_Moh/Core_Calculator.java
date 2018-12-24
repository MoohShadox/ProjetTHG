package sample.Conception_Moh;

import javafx.util.Callback;
import sample.Conception_Moh.Node;
import sample.Tuple;

import java.util.*;
import java.util.function.Function;

public class Core_Calculator {

    private Graph G;
    private int[] niveaux = null;

    Core_Calculator(Graph graphe){
        G = graphe;
    }

    public int applyDecomposition(){
        if(niveaux==null)
        {
            niveaux = decomposerNiveaux(G);
        }
        return 0;
    }

    public int applyRandomizedGraphGeneration(int ordre,int densite){
        G.setArcs(randomizedGraph(ordre,densite));
        return 0;
    }



    static HashSet<Tuple> randomizedGraph(int ordre, float densite){
        HashSet<Tuple> tuples = new HashSet<>();
        //Calculer le nombre d'arcs necessaires
        int nb_arcs = (int) ((ordre*(ordre-1))/2*densite);
        int nb = 0;
        Random rand = new Random(System.currentTimeMillis()%19);
        while(nb < nb_arcs) {
            for (int i = 0; i < ordre && nb<nb_arcs; i++) {
                for (int j = i+1; j < ordre && nb<nb_arcs; j++) {
                    if (rand.nextInt(100) < 55)
                    {
                        tuples.add(new Tuple(i, j));
                        nb++;
                    }
                }
            }
        }
        System.out.println("Le randomized aura genere  " + tuples.size() + " tuples en tous");
        return tuples;
    }


    private static HashSet<Integer>[] Tab_to_List(int tableau[],int ordre){
        HashSet<Integer> M[] = new HashSet[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new HashSet<>();
        }
        for (int j=0;j<ordre;j++){
            M[tableau[j]].add(j);
        }

        return M;
    }




    private HashSet<Integer> noyeau(){
        if(niveaux==null)
            applyDecomposition();
        int ordre = G.getOrdre();
        HashSet<Tuple> L = G.getArcs();
        HashSet<Integer> debuts = new HashSet<>();
        HashSet<Integer> suivants = new HashSet<>();
        HashSet<Integer> liste = new HashSet<>();
        HashSet<Integer> tab[] =Tab_to_List(niveaux, G.getOrdre());
        HashSet<Integer> buffer= new HashSet<>();
        int i = ordre-1;
        while (tab[i].isEmpty()){
            i--;
        }
        debuts.addAll(tab[i]);
        liste.addAll(debuts);
        buffer.addAll(debuts);
        while (i>=0){
            while (tab[i].isEmpty()){
                i--;
            }
            buffer.addAll(G.getListPredecesseurs(debuts));
            suivants.addAll(tab[i]);
            suivants.removeAll(buffer);
            liste.addAll(suivants);
            debuts.clear();
            debuts.addAll(suivants);
            suivants.clear();
            buffer.addAll(debuts);
            i--;
        }
        return liste;
    }


    public int[] decomposerNiveauxSossi(){
        HashSet<Tuple> L = G.getArcs();
        int ordre = G.getOrdre();
        /*
        declaration et allocation du tableau que nous allons retourner
         */
        int M[] = new int[ordre];
        /*
        declaration de liste des debuts qui contient initalement les sources du graphe
         */
        HashSet<Integer> debuts = G.getSources();
        /*
         Declaration du tableau de liste des suivatns que nous avons fait plutot
         */
        HashSet<Integer>[] suivants = G.getListeTableauSuccesseurs();
        /*
        allocation d'un buffer de listes de debuts
         */
        HashSet<Integer> nouvdeb = new HashSet<>();
        /*
        le buffer reçoit les debuts actuels
         */
        nouvdeb.addAll(debuts);
        Iterator<Integer> to;
        /*
        déclarations de variables qui vont nous aider lors du traitement
         */
        int j=1;
        int opo;
        int lr=0;
        int kl;
        /*
        nous allons effectuer cette action ordre-1 fois car on sait que la plus grand niveau est inferieur ou egal a ordre - 1
         */
        while ( lr<ordre){
            to=debuts.iterator();
            /*
            on parcoure la liste des debuts et pour chaque debut, tout ses suivant recoivent j, un entier qui s'incrémente de 1 a chaque fois
             */
            while (to.hasNext()){
                opo= to.next();
                Iterator<Integer> tt = suivants[opo].iterator();
                // System.out.print("et un suivant! \n");
                /*
                parcour des suivants du debut "opo"
                 */
                while (tt.hasNext()){
                    kl=tt.next();
                    /*
                    on ajoute a nouvdeb le suivant "kl" afin qu'il devienne un debut lors de ka prochaine itération
                     */
                    nouvdeb.add(kl);
                    /*
                    on actualise le niveau du suivant "kl" a j
                     */
                    M[kl] = j;
                }

            }
            /*
            on vide debuts pour le remplacer par nouveaux debuts et on vide ensuite nouvdeb
             */
            debuts.clear();
            debuts.addAll(nouvdeb);
            nouvdeb.clear();
            /*
            on icrémente j qui symbolise le niveau actuel
             */
            j++;
            lr++;

        }
        /*
        on retourne enfin le tableau des niveaux
         */
        return M;
    }



    private static int[] decomposerNiveaux(Graph G){
        G.setStructure();
        long current = System.currentTimeMillis();
        int[] ll  = new int[G.getOrdre()];
        for (int i=0;i<G.getOrdre();i++){
            ll[i] = -1;
        }
        int cpt = -1;
        boolean B = true;
        boolean s;
        Set<Integer> L = new HashSet<>();
        while(B){
            B = false;
            cpt = cpt + 1;
            for(int i=0;i<G.getOrdre();i++){
                if(ll[i]==-1){
                    B = true;
                    s = true;
                    for(int j = 0; j<G.getOrdre()&&s; j++){
                        if(ll[j]==-1 && G.isConnected(j,i))
                        {
                            s = false;
                        }
                    }
                    if(s)
                        L.add(i);

                }
            }
            for (int w:L){
                ll[w] = cpt;
            }
            L.clear();
        }
        current= System.currentTimeMillis()-current;
        System.out.println("la decomposition aura pris " + current);
        return ll;
    }


    //Test Unitaire
    public static void main(String[] str){
        HashSet<Tuple> t = new HashSet<>();
        t.add(new Tuple(3,4));
        t.add(new Tuple(4,5));
        t.add(new Tuple(4,6));
        t.add(new Tuple(2,4));
        t.add(new Tuple(1,2));
        t.add(new Tuple(1,3));
        /*Graph G = new Graphe_Creux(1500,0.98f);
        System.out.println("avec une liste: ");
        G.setStructure();
        for (int i:decomposerNiveaux(G)){

        }
        long d = G.getArcs().size()+G.ordre+1;
        System.out.println("La structure contient des lors " + d +  " cellules ");
        System.out.println("avec une matrice: ");
        Graph G1 = new Graphe_Dense(1500,0.98f);
        G1.setStructure();
        for (int i:decomposerNiveaux(G1)){

        }
        System.out.println("La structure contiens des lors " + G1.ordre*G1.ordre + " cellules ");*/
        Graph G1 = new Graphe_Creux(7,t);
        G1.setStructure();
        System.out.println(G1.getPredecesseur(4));
        Core_Calculator CC = new Core_Calculator(G1);
        CC.applyDecomposition();
        System.out.println(CC.noyeau());
    }
}
