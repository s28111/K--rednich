import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static List<Argument> argumenty;
    static List<Klaster> klastry;
    static List<double[]> srednieCentroidow;
    
    static boolean zmiana = false;
    static Set<String> klasy = new HashSet<>();
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        argumenty = new ArrayList<>();
        klastry = new ArrayList<>();
        srednieCentroidow = new ArrayList<>();

        try{
            BufferedReader bf = new BufferedReader(new FileReader("iris_kmeans.txt"));
            String line;
            while((line = bf.readLine()) != null){
                String[] arg = line.split(",");
                String klasa = arg[arg.length - 1];
                String[] odleglosci = new String[arg.length - 1];
                for(int i = 0;i < arg.length - 1; i++){
                    odleglosci[i] = arg[i];
                }
                argumenty.add(new Argument(odleglosci,klasa));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Argument argument : argumenty){
            klasy.add(argument.klasa);
        }

        Collections.shuffle(argumenty);
        for(int i = 0; i < k; i++){
            klastry.add(new Klaster());
        }
        Random random = new Random();
        for(Argument a : argumenty){
            int index = random.nextInt(k);
            a.nowyKlaster = index;
            klastry.get(index).dodajArgument(a);
        }
        zmiana = true;

        while(zmiana == true) {
            zmiana = false;
            srednieCentroidow = new ArrayList<>();
            for (Klaster klaster : klastry) {
                klaster.obliczSrednie();
                srednieCentroidow.add(klaster.srednie);
            }
            for (Argument argument : argumenty) {
                obliczOdleglosc(argument);
            }
            opiszCentroidy();

        };

    }
    public static void obliczOdleglosc(Argument argument){
        double odleglosc = 1000000;
        int index = argument.nowyKlaster;
        int count = 0;
        for(double[] odleglosci : srednieCentroidow){
            double nowaOdleglosc = 0;
            for(int i = 0; i < odleglosci.length; i++){
                nowaOdleglosc += Math.pow(argument.odleglosci[i] - odleglosci[i],2);
            }
            if(nowaOdleglosc < odleglosc){
                odleglosc = nowaOdleglosc;
                index = count;
            }
            count++;
        }
        argument.staryKlaster = argument.nowyKlaster;
        argument.nowyKlaster = index;
        klastry.get(argument.staryKlaster).argumenty.remove(argument);
        klastry.get(argument.nowyKlaster).argumenty.add(argument);
        if(argument.staryKlaster != argument.nowyKlaster){
            zmiana = true;
        }
    }
    public static void opiszCentroidy(){
        Map<String,Integer> opis;
        int count = 1;
        for(Klaster klaster : klastry){
            opis = new HashMap<>();
            for(Argument argument : klaster.argumenty){
                if(!opis.containsKey(argument.klasa)){
                    opis.put(argument.klasa,1);
                }else{
                    int w = opis.get(argument.klasa);
                    w++;
                    opis.replace(argument.klasa, w);
                }
            }
            StringBuilder pelnyOpis = new StringBuilder();
            pelnyOpis.append("Klaster " + count + ":");
            Set<String> klasyKlasteru = opis.keySet();
            for(String klasa : klasyKlasteru){
                double liczba = opis.get(klasa);
                double iloscArgumentow = klaster.argumenty.size();
                double wartoscc = liczba/iloscArgumentow * 100;
                pelnyOpis.append(klasa + " " + wartoscc + "% ");
            }
            System.out.println(pelnyOpis);
            count++;
        }

    }
}