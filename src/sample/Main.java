package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


public class Main  extends Application {

    private LinkedList<Integer> coeur;

    private Tuple randomTuple(LinkedList<Tuple> L, int ordre){
        Random rand = new Random();
        int a = rand.nextInt(ordre);
        int b = rand.nextInt(ordre);
        while(a == b || Tuple.containing(L,a,b)){
            a = rand.nextInt(ordre);
            b = rand.nextInt(ordre);
        }
        return new Tuple(a,b);
    }



    public static HashSet<Tuple> generergraphe(int ordre, float dansité){
        HashSet<Tuple> aleagraphe = new HashSet<Tuple>();
        LinkedList<Integer> L = new LinkedList<>();
        LinkedList<Integer> M[] = new LinkedList[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new LinkedList<>();
        }
        Random rand = new Random();
        int arretes = 0;
        int debut, svt;
        int j;

        while ( (arretes+1f)/ordre < dansité){
            debut = rand.nextInt(ordre);
            while(rand.nextInt(10)<7 && (arretes+1f)/ordre < dansité){
                svt = rand.nextInt(ordre);
                while(svt == debut || M[debut].contains(svt)){
                    svt = rand.nextInt(ordre);
                //    System.out.print("ha \n");
                }
                System.out.print(debut + " " + svt +"\n" );
                aleagraphe.add(new Tuple(debut,svt));
                arretes++;

                j= 0;
                M[svt].add(debut);
                while(  j<M[debut].size()){
                    //System.out.print("ha2 \n");
                    M[svt].add(M[debut].get(j));
                    j++;
                }
                debut = svt;


            }

        }
        return aleagraphe;
    }

  /*  private int dg(HashSet<Tuple> L, int a){
        int k=0;
        Tuple p= null;
        Iterator<Tuple> t = L.iterator();
        while (t.hasNext()){
            p = t.next();
            if(p.getTableau()[0] == a){
                k++;
            }
        }
        return k;
    }


    private LinkedList<Integer> biggerdg(LinkedList<Integer> Liste,HashSet<Tuple> L, int ordre){
        int deg = 0;
        LinkedList<Integer> tableau = new LinkedList<>();
        int i = 0;
        Iterator<Integer> iter = Liste.iterator();
        while (iter.hasNext()){
            i = iter.next();
            if(dg(L,i)>deg){
                deg = dg(L,i);
                tableau.clear();
            }else{
                if(dg(L,i) == deg){
                    tableau.add(i);
                }
            }
        }
        return tableau;
    }
    public LinkedList<Integer> coeurgraphe(HashSet<Tuple> L,int ordre){
        Random rand = new Random();
        LinkedList<Integer> liste = null;
        LinkedList<Integer> tab = null;
        int i= 0;
        int point;
        for (i=0;i<ordre;i++){
            tab.add(i);
        }
        LinkedList<Integer> buffer;
        while (!tab.isEmpty()){
            buffer = biggerdg(tab,L,ordre);
            point = rand.nextInt(buffer.size());
            point = buffer.get(point);
            liste.add(point);
            tab.removeAll(getproches(L,point));
        }


        return liste;
    }*/

    public static int[][] MA(HashSet<Tuple> L, int ordre){
        int A[][] = new int[ordre][ordre];
        Iterator<Tuple> t = L.iterator();
        Tuple tup;
        while (t.hasNext()){
            tup=t.next();
            A[tup.getTableau()[0]][tup.getTableau()[1]] = 1;
        }
        return A;
    }

    public static LinkedList<Integer> sources(HashSet<Tuple> L, int ordre){
        LinkedList<Integer> liste = new LinkedList<>();
        for(int i=0;i<ordre;i++){
            liste.add(i);
        }
        Iterator<Tuple> t = L.iterator();
        Tuple tup;
        while (t.hasNext()){
            tup=t.next();
            if(liste.contains(tup.getTableau()[1])){
                liste.remove((Integer)tup.getTableau()[1]);
            }

        }
        return liste;
    }
    public static LinkedList<Integer>[] suivants(HashSet<Tuple> L, int ordre){
        LinkedList<Integer> M[] = new LinkedList[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new LinkedList<>();

        }
        Iterator<Tuple> t = L.iterator();
        Tuple tup;
        while (t.hasNext()){
            tup=t.next();
            M[tup.getTableau()[0]].add(tup.getTableau()[1]);
        }
        return M;
    }

    public static LinkedList<Integer> getpredecesseurs(HashSet<Tuple> L,int a){
        LinkedList<Integer> liste = new LinkedList<>();
        Iterator<Tuple> i = L.iterator();
        Tuple k;
        while(i.hasNext()){
            k=i.next();
            if(k.getTableau()[1] == a){
                liste.add(k.getTableau()[0]);
            }
        }
        return liste;
    }

    public static LinkedList<Integer> getlistpredecesseurs(HashSet<Tuple> L,LinkedList<Integer> list){
        LinkedList<Integer> A= new LinkedList<>();
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()){
            A.addAll(getpredecesseurs(L, it.next()));
        }
        return A;
    }

    public static int[] niveaux(HashSet<Tuple> L,int ordre){
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
        while ( /*!buffer.isEmpty()*/ lr<ordre){
            to=debuts.iterator();
            //System.out.print(debuts + "\n");
            while (to.hasNext()){
                opo= to.next();
                Iterator<Integer> tt = suivants[opo].iterator();
                while (tt.hasNext()){
                    kl=tt.next();
                    nouvdeb.add(kl);
                    M[kl] = j;
                   // System.out.print(kl + ": " + M[kl] + "\n");
                }

            }
            debuts.clear();
            debuts.addAll(nouvdeb);
            nouvdeb.clear();
            j++;
            lr++;

        }

        /*System.out.print("------------------------------------------------------- \n");
        for(int u=0 ;u <25; u++){

            System.out.print(u +": " + M[u] + "\n");
        }*/

        return M;
    }

    public static LinkedList<Integer>[] Lniveaux(int tableau[],int ordre){
        LinkedList<Integer> M[] = new LinkedList[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new LinkedList<>();
        }
        for (int j=0;j<ordre;j++){
            M[tableau[j]].add(j);
        }

        return M;
    }

    public static LinkedList<Integer> noyeau(HashSet<Tuple> L,int ordre){
        LinkedList<Integer> debuts = new LinkedList<>();
        LinkedList<Integer> suivants = new LinkedList<>();
        LinkedList<Integer> liste = new LinkedList<>();
        LinkedList<Integer> tab[] =Lniveaux(niveaux(L, ordre ), ordre);
        LinkedList<Integer> buffer= new LinkedList<>();
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
            buffer.addAll(getlistpredecesseurs(L,debuts));
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

    public static void main(String[] arg) {
         HashSet<Tuple> Graphe;
        Graphe = Main.generergraphe(25,1f);
        System.out.print("le graphe d'ordre " + 25 + ":\n" + Graphe.size() + "\n");

        System.out.print("les slurces: " + sources(Graphe, 25) + "\n");
        int tab[]=niveaux(Graphe, 25 );
        LinkedList<Integer> tob[] =Lniveaux(tab, 25);
        for (int j=0;j<25;j++){
            System.out.print("niveau " + j + ": " + tob[j] + "\n");
        }
        System.out.print(noyeau(Graphe, 25));


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
