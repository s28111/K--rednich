import java.util.Arrays;

public class Argument {
    double[] odleglosci;
    String klasa;
    int staryKlaster;
    int nowyKlaster;
    public Argument(String[] o, String klasa){
        this.klasa = klasa;
        this.odleglosci = new double[o.length];
        int count = 0;
        for(String s : o){
            this.odleglosci[count] = Double.parseDouble(s);
            count++;
        }
        staryKlaster = 0;
        nowyKlaster = 0;
    }
    public String toString(){
        return Arrays.toString(odleglosci) + " " + klasa + " " + staryKlaster + " " + nowyKlaster;
    }
}
