package sample.Conception_Moh;

import sample.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphBuilder {
    private Graph G = null;
    private int ordre = 0;
    private final int pourcentage_creux = 50;
    private HashSet<Tuple> tuples = new HashSet<>();
    private float densite = 0;

    public GraphBuilder(int ordre){
        this.ordre = ordre;
    }

    public GraphBuilder withDensite(float densite){

        return this;
    }

    public GraphBuilder withTuples(HashSet<Tuple> tuples){
        this.tuples = tuples;
        return this;
    }

    public Graph buildFromTuples(){
        float densite = 2*tuples.size()/((ordre-1)*ordre);
        if(densite*100<pourcentage_creux)
            G = new Graphe_Creux(ordre,tuples);
        else
            G = new Graphe_Dense(ordre,tuples);
        return G;
    }

    public Graph buildFromDensite(){
        if(densite*100<pourcentage_creux)
            G = new Graphe_Creux(ordre,densite);
        else
            G = new Graphe_Dense(ordre,densite);
        return G;
    }

    public GraphBuilder fromFolder(){
        Path source = Paths.get("graph.txt");
        Pattern reconnaitre_ordre = Pattern.compile("(\\d)");
        Pattern reconnaitre_tuple = Pattern.compile("(\\d),(\\d)");
        try ( BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8) )  {
            String str = reader.readLine();
            Matcher M1 = reconnaitre_ordre.matcher(str);
            M1.find();
            ordre =  Integer.parseInt(M1.group(1));
            Matcher M2;
            str = reader.readLine();
            while(str!=null){
                M2 = reconnaitre_tuple.matcher(str);
                if(str!=null && M2.find())
                {
                    Tuple t = new Tuple(Integer.parseInt(M2.group(1)),Integer.parseInt(M2.group(2)));
                    tuples.add(t);
                }
                str = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


}
