package sample;

import sample.Tuple;

import java.util.*;


public class Main {

    private LinkedList<Integer> coeur;

    /*TODO: Methode pour générer de manière aléatoire un graphe.
    Le principe de cet algoriyhme et de générer des chemins
    */

    private static HashSet<Integer>[] MAJ(HashSet<Integer>[] M, int a, int ordre, HashSet<Integer> H){
        HashSet<Integer> Hash[]=M;
        for (int i=0;i<ordre;i++){
            if(Hash[i].contains(a)){
                Hash[i].addAll(H);
            }
        }
        return Hash;
    }


    public static HashSet<Tuple> generergraphe(int ordre, float dansité){
        //j'alloue le graphe:
        HashSet<Tuple> aleagraphe = new HashSet<Tuple>();
        /*J'alloue un tableau ee liste ou chaque indice est un sommet du graphe
        et M[element] est la liste X, ou quelque soit s appartenant a X, il existe
        un chemain entre s et element.
         */
        HashSet<Integer> M[] = new HashSet[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new HashSet<>();
        }
        Random rand = new Random();
        /*
        compte le nombres d'arrêtes créées afin de vérifier la condition de densité
         */
        int arretes = 0;
        /*
        debut et svt sont des variables que j'utiliserais pour construire un tuple
        un tuple représente un arc allant de debut vers svt
         */
        int debut, svt;
        /*
        un compteur simple que j"utiliserais plus tard
         */
        int j;
            /*
            pour la densite
             */
        int nb_max = ordre*(ordre-1)/2;
        /*
        nous procéderons tant que le nombre d'arrêtes sur l'ordre ne dépasse pas la densité voulue
         */
        int taille = aleagraphe.size();

        while ( (arretes+1f)/nb_max < dansité){
            /*
            je génère aléatoirement un sommet debut
             */
            debut = rand.nextInt(ordre);
            boolean chfin=true;
            if(M[debut].size()!=ordre-1){
                chfin=false;
            }
            /*
            dans cette boucle il s'agira de créer un chemain allant de debut vers des suivants,
            on utilisera une probabilité de 0,7 pour continuer a générer le chemain et on s'arretera
            biensur dans le cas ou le nombre d'arrête sur l'ordre dépasse la densité
             */
            while(rand.nextInt(10)<7 && (arretes+1f)/nb_max < dansité & !chfin){
                /*
                je génère un suivant tant que le suivant est égale a au début ou que la matrice de début
                contient ce suivant. on s'arretera quand aucune des deux conditions n'est vérifiée car
                il est clair que le suivant doit être différent du debut sinon on créera une boucle
                aussi ce suivant doit être différent des elements qui mènenet vers début sinon on générera
                un circuit.
                 */
                //System.out.print("avant! \n");
                //System.out.print("la taille est;" + M[debut].size() + "\n");
                svt = rand.nextInt(ordre);
                while(svt == debut || M[debut].contains(svt)){
                    svt = rand.nextInt(ordre);
                }
                // System.out.print(M[debut]);
                System.out.print(debut + " " + svt +"\n" );
                /*
                une fois avoir trouvé un suivant valide, on ajoute un arc allant de debut vers suivant a notre grapje
                 */
                aleagraphe.add(new Tuple(debut,svt));
                /*
                bien sur il faut incrémenter le nombre d'arrêtes
                 */
                if(taille<aleagraphe.size()){
                    arretes++;
                    taille=aleagraphe.size();
                }
                /*
                il s'agira ensuite d'actualiser la liste des elements ayant un chemain menant vers ce suivant la procedure
                est simple, il s'agit déja d'ajouter debut et ensuite d'ajouter tout le sommets menant vers debuts car
                ceux ci mènenent maintenant aussi a suivant
                 */
                M[svt].add(debut);
                Iterator<Integer> iterator = M[debut].iterator();
                while( iterator.hasNext()){
                    M[svt].add(iterator.next());
                }
                M=MAJ(M,svt,ordre,M[svt]);


                /*
                le somet de debut devient le suivant afin de continuer a générer un chemin
                 */
                debut = svt;
                chfin=true;
                if(M[debut].size()!=ordre-1){
                    chfin=false;
                }

            }
            /*
            quand on termine de générer un chemin et que la proprétée de densité n'est toujours pas vérifiée
            on continue de générer des chemins
             */
        }
        /*
        enfin on retourne le graphe
         */
        return aleagraphe;
    }

    /*
    methode qui ne sert a rien, elle transforme notre représentation sous forme de Hashset en matrice d'adjascence
     */
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

    /*
    Methode permetant d'extraire les sommets sources d'un graphe
     */
    public static HashSet<Integer> sources(HashSet<Tuple> L, int ordre){
        /*
        déclaration, allocation et remplissage de la liste des sommets
         */
        HashSet<Integer> liste = new HashSet<>();
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
    public static HashSet<Integer>[] suivants(HashSet<Tuple> L, int ordre){
        /*
        allocation du tableau de liste qu'on retournera plus tard
         */
        HashSet<Integer> M[] = new HashSet[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new HashSet<>();

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
    /*
    methode retournant tout les predecesseurs d'un sommet a
     */
    public static HashSet<Integer> getpredecesseurs(HashSet<Tuple> L,int a){
        /*
        declaration et allocation de la liste des predesseurs que nous allons retourner
         */
        HashSet<Integer> liste = new HashSet<>();
        Iterator<Tuple> i = L.iterator();
        Tuple k;
        /*
        parcour du hashset de tuples et si son suivant est egale a notre sommet a, ajouter son debut dans la liste
         */
        while(i.hasNext()){
            k=i.next();
            if(k.getTableau()[1] == a){
                liste.add(k.getTableau()[0]);
            }
        }
        return liste;
    }
    /*
    methode retournant le tableau des predesseurs des elements de la liste "list"
     */
    public static HashSet<Integer> getlistpredecesseurs(HashSet<Tuple> L,HashSet<Integer> list){
        /*
        allocation de la liste que nous allons retourner
         */
        HashSet<Integer> A= new HashSet<>();
        Iterator<Integer> it = list.iterator();
        /*
        pour chaque element de la liste "list" ajouter ses predesseurs dans la liste
         */
        while (it.hasNext()){
            A.addAll(getpredecesseurs(L, it.next()));
        }
        return A;
    }
    /*
    TODO: Algorithme de décomposition en niveaux
    retourne un tableau ou l'indice est le sommet et son contenu T[sommet] est son niveau
     */

    public static int[] niveaux(HashSet<Tuple> L,int ordre){
        /*
        declaration et allocation du tableau que nous allons retourner
         */
        int M[] = new int[ordre];
        /*
        declaration de liste des debuts qui contient initalement les sources du graphe
         */
        HashSet<Integer> debuts = sources(L, ordre);
        /*
         Declaration du tableau de liste des suivatns que nous avons fait plutot
         */
        HashSet<Integer>[] suivants = suivants(L, ordre);
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
    /*
    prend en entré un tableau de niveau en fonction du sommet et retourne une liste de sommets par niveaux
         */
    public static HashSet<Integer>[] Tab_to_List(int tableau[],int ordre){
        HashSet<Integer> M[] = new HashSet[ordre];
        for(int i=0; i<ordre; i++){
            M[i]= new HashSet<>();
        }
        for (int j=0;j<ordre;j++){
            M[tableau[j]].add(j);
        }

        return M;
    }
    /*
    TODO: Algorithme qui trouve le noyeau d'un graphe
    retourne une liste de sommets
    le principe est siple, tout les puits sont des elements du noyeau, on les ajoute ensuite eux et leurs precedents dans un buffer
    ensuite on va parcourir les autres niveau, pour chaque niveau, tout les sommets du niveau qui ne sont pas
    dans buffer deviennent elements du noyeau ceux ci sont ensuite ajoutés a buffer ainsi que leurs precedents
     */
    public static HashSet<Integer> noyeau(HashSet<Tuple> L,int ordre){
        /*
        declaration de la liste debuts qui contiendra les elements dont on calculera les precedents
         */
        HashSet<Integer> debuts = new HashSet<>();
        /*
        suiivants contiendra les elements du niveau courent qui ne sont pas dans buffer
         */
        HashSet<Integer> suivants = new HashSet<>();
        /*
        liste contient la liste des elements du noyeau que nous retournerons plus tard
         */
        HashSet<Integer> liste = new HashSet<>();
        /*
        contient le tableau des somets pour chaque niveau
         */
        HashSet<Integer> tab[] =Tab_to_List(niveaux(L, ordre ), ordre);
        /*
        buffer contiendra tout les somets parcourus
         */
        HashSet<Integer> buffer= new HashSet<>();
        /*
        on initialise i a ordre-1 car nous allons parcourir le graphe depuis les puits jusqu'aux sources
         */
        int i = ordre-1;
        /*
        il y'a biensur des niveaux qui ne contiennent au un sommets, nous n'allons pas les traiter
         */
        while (tab[i].isEmpty()){
            i--;
        }
        /*
        on intialise debuts aux sommets puits
         */
        debuts.addAll(tab[i]);
        /*
        on ajoute ces puits au noyeau
         */
        liste.addAll(debuts);
        /*
        on ajoute ces puits au buffer
         */
        buffer.addAll(debuts);
        /*
        tant que nous n'avons parcouru tout lesniveaux nous effectueons le traitement ci dessous
         */
        while (i>=0){
            /*
            bien sur on avance jusqu'a un niveau contenant au moins un niveau
             */
            while (tab[i].isEmpty()){
                i--;
            }
            /*
            vu que le noyeau est un stable, on ajoute tout les predesseurs a buffer afin de ne pas les traiter, car ceux-ci
            sont predesseurs d'elements se trouvant dans le noyeau
             */
            buffer.addAll(getlistpredecesseurs(L,debuts));
            /*
            suivant recevera la liste du niveau i dont on retirera les elements se trouvant dans le buffer
             */
            suivants.addAll(tab[i]);
            suivants.removeAll(buffer);
            /*
            on ajoutera ensuite danss le noyeau les elements restant de la liste des suivants
             */
            liste.addAll(suivants);
            /*
            on ide debuts puis on la remplie par suivants afin de refaire le traitement pour les prochains niveaux
             */
            debuts.clear();
            debuts.addAll(suivants);
            /*
            on vide les suivants car on n'en a plus besoin
             */
            suivants.clear();
            /*
            et bien sur tout les elements que nous avons ajouté qui sont maintenant dans debuts sont ajoutés au buffer
             */
            buffer.addAll(debuts);
            /*
            on  décrémente le niveau
             */
            i--;
        }
        /*
        on retourne enfin la liste des elements du noyeau
         */
        return liste;
    }

    public static void main(String[] arg) {
        HashSet<Tuple> Graphe;
         /*
         generation du graphe et affichage de celui-ci
          */
        Graphe = Main.generergraphe(100,1f);
        System.out.print("le graphe d'ordre " + 15 + ":\n" + Graphe.size() + "\n");
        /*
        affichage des sources du graphe
         */
        //System.out.print("les slurces: " + sources(Graphe, 25) + "\n");
        /*
        décomposition en niveau du graphe et affichage de ces niveaux
         */
        // System.out.print("1: " + System.currentTimeMillis());
        long a = System.currentTimeMillis();
        int tab[]=niveaux(Graphe, 100 );
        System.out.print("\n temp: " + (System.currentTimeMillis() - a) + "\n");
        /*
        affichage du noyeau du graphe
         */
        a = System.currentTimeMillis();
        System.out.print(noyeau(Graphe, 100));
        System.out.print("\n temp: " + (System.currentTimeMillis() - a) + "\n");

    }
}
