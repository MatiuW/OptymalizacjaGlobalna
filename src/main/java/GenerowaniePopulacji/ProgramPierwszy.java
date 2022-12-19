package GenerowaniePopulacji;

import model.DaneWyjsciowe;
import model.Wymiar;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ProgramPierwszy {

    private static final int a = -1, b = 1, d = 1, n = 20;
    //do 5 pkt
    private ArrayList<Integer> listD = new ArrayList<>();
    private ArrayList<Integer> listAk = new ArrayList<>();
    private ArrayList<Integer> listBk = new ArrayList<>();

    private ArrayList<Wymiar> wymiar = new ArrayList<>();

    private int[][] binarny;
    private int[] dziesietny;
    private double[] wyniki;

    //wykorzystane
    private double[] rastrigina;

    private ArrayList<DaneWyjsciowe> daneWyjsciowe = new ArrayList<>();

    private String[] pomocDoZapisuBinarnego = new String[n];

    public ArrayList<DaneWyjsciowe> ocenaOsobnikow(ArrayList<DaneWyjsciowe> daneWejsciowe) {

        rastrigina= new double[daneWejsciowe.size()];

        //odczytuja dane i wpisuje je do list lismM, listBk, listAk
        readFile();

        //tworzy nowe osobniki na podstawie wczesniejszych danych
        for(int i = 0; i < listD.size(); i++) {
            wymiar.add(new Wymiar(listAk.get(i), listBk.get(i), listD.get(i)));
        }
        int hm = 0;
        int wym = 0;
        for(Wymiar w: wymiar) {
            int iloscLiczbWZbiorze = (int)obliczIloscLiczbWZbiorze2(w);
            w.setM((int)obliczM(iloscLiczbWZbiorze));

            //rozlozyc binarny na czesci
            ArrayList<Integer> dzie = new ArrayList<>();

            String[] bin = new String[daneWejsciowe.size()];
            int i = 0;
            for(DaneWyjsciowe dw: daneWejsciowe) {
                bin[i] = dw.getBinarny().substring(hm, hm+w.getM());
                i++;
            }
            hm+=w.getM();

            int[][] bin2 = new int[daneWejsciowe.size()][w.getM()];
            for(int j = 0; j < bin.length; j++) {
                for(int k = 0; k < w.getM(); k++) {
                    bin2[j][k] = Integer.valueOf(bin[j].split("")[k]);
                }
            }

            w.setBinarny(bin2, 0, 0);
            wym++;

//            wypiszBinarny3(w, daneWejsciowe.size());
            //koniec

            binarnyNaDziesietny3(w, daneWejsciowe.size());
//            wypiszDziesietny3(w, daneWejsciowe.size());

//            obliczX2(w);
            obliczX3(w, daneWejsciowe.size());
//            wypiszX3(w, daneWejsciowe.size());
            obliczFunkcjaRastrigina3(w, daneWejsciowe.size());
        }

        //ostateczne wyniki
        //dodane 10*w do wynikow rastrigina
//        System.out.println("Wynik obliczen rastrigina");
        for(int i = 0 ; i < rastrigina.length; i++) {
            rastrigina[i]+=10 * wymiar.size();
        }

        //dane do seleckji - rastrigina[n]
//        for(double r: rastrigina) {
//            System.out.println(r);
//        }


        //stworzenie danych wyjsciowych
        for(int i = 0 ; i < daneWejsciowe.size(); i++) {
            daneWyjsciowe.add(new DaneWyjsciowe(daneWejsciowe.get(i).getBinarny(), rastrigina[i]));
        }

        return daneWyjsciowe;
    }

    public ArrayList<DaneWyjsciowe> start() {

        //na 5 punktow ------------------------------------------------
        rastrigina= new double[n];

        //odczytuja dane i wpisuje je do list lismM, listBk, listAk
        readFile();

        //tworzy nowe osobniki na podstawie wczesniejszych danych
        for(int i = 0; i < listD.size(); i++) {
            wymiar.add(new Wymiar(listAk.get(i), listBk.get(i), listD.get(i)));
        }

//        for(double r: rastrigina) {
//            r = 0;
//        }
        for(int i = 0 ; i < rastrigina.length; i++) {
            for(int j = 0; j < 2; j++) {
                rastrigina[i] = 0;
            }
        }

        for(Wymiar w: wymiar) {
            int iloscLiczbWZbiorze = (int)obliczIloscLiczbWZbiorze2(w);
            w.setM((int)obliczM(iloscLiczbWZbiorze));

            wypelnijTabliceLosowo2(w);
            wypiszBinarny2(w);
            binarnyNaDziesietny2(w);
//            wypiszDziesietny2(w);
            obliczX2(w);
//            wypiszX2(w);
            obliczFunkcjaRastrigina2(w);
        }

        //ostateczne wyniki
        //dodane 10*w do wynikow rastrigina
//        System.out.println("Wynik obliczen rastrigina");
        for(int i = 0 ; i < rastrigina.length; i++) {
            rastrigina[i]+=10 * wymiar.size();
        }

        //dane do seleckji - rastrigina[n]
//        for(double r: rastrigina) {
//            System.out.println(r);
//        }


        //stworzenie danych wyjsciowych

        for(int i = 0; i < pomocDoZapisuBinarnego.length; i++) {
            pomocDoZapisuBinarnego[i] = "";
        }

        for(Wymiar w: wymiar) {
            int[][] h = w.getBinarny();

            for(int i = 0; i < n; i++) {
                for(int j = 0; j < w.getM(); j++) {
                    pomocDoZapisuBinarnego[i] += h[i][j];
                }
            }
        }

        for(int i = 0 ; i < pomocDoZapisuBinarnego.length; i++) {
            daneWyjsciowe.add(new DaneWyjsciowe(pomocDoZapisuBinarnego[i], rastrigina[i]));
        }


        return daneWyjsciowe;
    }

    public void obliczFunkcjaRastrigina2(Wymiar o) {

        for(int i = 0; i < n; i++) {
            rastrigina[i] +=  Math.pow(o.getX()[i], 2) - 10 * Math.cos(20 * Math.PI * o.getX()[i]);
        }

    }

    public void obliczFunkcjaRastrigina3(Wymiar o, int n) {

        for(int i = 0; i < n; i++) {
            rastrigina[i] +=  Math.pow(o.getX()[i], 2) - 10 * Math.cos(20 * Math.PI * o.getX()[i]);
        }

    }

    public double obliczIloscLiczbWZbiorze2(Wymiar wymiar) {
        return (wymiar.getB() - wymiar.getA()) * Math.pow(10, wymiar.getD()) + 1;
    }

    public double obliczM(double iloscLiczbWZbiorze) {
        int i = 1;
        while(iloscLiczbWZbiorze > Math.pow(2, i)) {
            i++;
        }

        return i;
    }


    public void wypelnijTabliceLosowo2(Wymiar o) {
        binarny= new int[n][o.getM()];

        Random random = new Random();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < o.getM(); j++) {
                binarny[i][j] = random.nextInt(2);

            }
        }
        o.setBinarny(binarny, n , o.getM());
    }

    public void wypiszX2(Wymiar o) {

        System.out.println("Wyniki dla Xk");
        for(int i = 0; i < n; i++) {
            System.out.println(o.getX()[i]);
        }
        System.out.println();
    }

    public void wypiszX3(Wymiar o, int n) {

        System.out.println("Wyniki dla Xk");
        for(int i = 0; i < n; i++) {
            System.out.println(o.getX()[i]);
        }
        System.out.println();
    }

    public void wypiszBinarny2(Wymiar o) {

        System.out.println("Wynik binarny");
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < o.getM(); j++) {
                System.out.print(o.getBinarny()[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void wypiszBinarny3(Wymiar o, int n) {

        System.out.println("Wynik binarny");
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < o.getM(); j++) {
                System.out.print(o.getBinarny()[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void wypiszDziesietny2(Wymiar o) {
        System.out.println("Wynik dziesietny");
        for(int i = 0; i < n; i++) {
            System.out.println(o.getDziesietny()[i]);
        }
        System.out.println();
    }

    public void wypiszDziesietny3(Wymiar o, int n) {
        System.out.println("Wynik dziesietny");
        for(int i = 0; i < n; i++) {
            System.out.println(o.getDziesietny()[i]);
        }
        System.out.println();
    }

    public void binarnyNaDziesietny2(Wymiar o) {
        dziesietny = new int[n];
        for (int i =0; i < o.getBinarny().length; i++){
            int sum = 0;
            for (int j = o.getBinarny()[i].length-1; j>= 0; j--){
                sum += o.getBinarny()[i][o.getBinarny()[i].length-1-j]*(Math.pow(2, j));
            }
            dziesietny[i] = sum;
        }

        o.setDziesietny(dziesietny);
    }

    public void binarnyNaDziesietny3(Wymiar o, int n) {
        dziesietny = new int[n];
        for (int i =0; i < o.getBinarny().length; i++){
            int sum = 0;
            for (int j = o.getBinarny()[i].length-1; j>= 0; j--){
                sum += o.getBinarny()[i][o.getBinarny()[i].length-1-j]*(Math.pow(2, j));
            }
            dziesietny[i] = sum;
        }

        o.setDziesietny(dziesietny);
    }

    public void obliczX2(Wymiar o) {
        wyniki = new double[n];
        for(int i = 0; i < n; i++) {
            wyniki[i] = o.getA() + ((o.getB()-o.getA()) * o.getDziesietny()[i])/(Math.pow(2, o.getM()) - 1);
        }

        o.setX(wyniki);
    }

    public void obliczX3(Wymiar o, int n) {
        wyniki = new double[n];
        for(int i = 0; i < n; i++) {
            wyniki[i] = o.getA() + ((o.getB()-o.getA()) * o.getDziesietny()[i])/(Math.pow(2, o.getM()) - 1);
        }

        o.setX(wyniki);
    }

    public void readFile() {
        try {
            File myObj = new File("Osobniki.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                createOsobniki(data);

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createOsobniki(String data) {
        int count = 0;
        for(String s: data.split(" ")) {

             if(count == 0) {
                listAk.add(Integer.parseInt(s));
                count++;
            } else if(count == 1) {
                listBk.add(Integer.parseInt(s));
                count++;
            } else if(count == 2) {
                 listD.add(Integer.parseInt(s));
             }
        }
    }
}
