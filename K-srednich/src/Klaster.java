import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Klaster {
    List<Argument> argumenty;

    double[] srednie;
    public Klaster(){
        argumenty = new ArrayList<>();
    }
    public void obliczSrednie(){
        if(argumenty.size() > 0){
            srednie = new double[argumenty.get(0).odleglosci.length];
        }else{
            srednie = new double[1];
        }

        for(int i = 0; i < srednie.length; i++){
            srednie[i] = 0;
        }
        for(Argument a : argumenty){
            int count = 0;
            for(double d : a.odleglosci){
                srednie[count] += d;
                count++;
            }
        }
        for(int i = 0; i < srednie.length; i++){
            srednie[i] /= argumenty.size();
        }
    }
    public void dodajArgument(Argument argument){
        argumenty.add(argument);
    }
    public String toString(){
        return Arrays.toString(srednie) + " " + argumenty;
    }
}
