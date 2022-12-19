package operatoryGenetyczne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Sukcesja.TypSukcesji;
import model.DaneWyjsciowe;
import model.Wymiar;

public class Mutacja {

    private static final int n = 10;
    private static final double Pm = 0.1;
    private ArrayList<Integer> listD = new ArrayList<>();
    private ArrayList<Integer> listAk = new ArrayList<>();
    private ArrayList<Integer> listBk = new ArrayList<>();

    private ArrayList<Wymiar> wymiar = new ArrayList<>();

    private int[][] binarny;

    private ArrayList<DaneWyjsciowe> daneWyjsciowe = new ArrayList<>();


    public void start() {
        //odczytuja dane i wpisuje je do list lismM, listBk, listAk
        readFile();

        //tworzy nowe osobniki na podstawie wczesniejszych danych
        for(int i = 0; i < listD.size(); i++) {
            wymiar.add(new Wymiar(listAk.get(i), listBk.get(i), listD.get(i)));
        }

        for(Wymiar w: wymiar) {
            int iloscLiczbWZbiorze = (int)obliczIloscLiczbWZbiorze2(w);
            w.setM((int)obliczM(iloscLiczbWZbiorze));

            wypelnijTabliceLosowo2(w);
            wypiszBinarny2(w);

            stworzTabliceZWartosciamiZeroJeden(w);
            wypiszTabliceZWartosciamiZeroJeden(w);

            zamienWartosci(w);
            wypiszZamienioneWartosci(w);
//            binarnyNaDziesietny2(w);
//            wypiszDziesietny2(w);
//            obliczX2(w);
//            wypiszX2(w);
//            obliczFunkcjaRastrigina2(w);
        }
    }

    public ArrayList<DaneWyjsciowe> start2(ArrayList<DaneWyjsciowe> daneWejsciowe, TypSukcesji typSukcesji) {
        for(DaneWyjsciowe dw: daneWejsciowe) {
            zamienWartosci2(dw, typSukcesji);
        }

        return daneWyjsciowe;
    }

    public void zamienWartosci2(DaneWyjsciowe dw, TypSukcesji typSukcesji) {
        Random random = new Random();
        String[] mojbinarny = dw.getBinarny().split("");
        String rBinarny= "";

        for (int i = 0; i < mojbinarny.length; i++) {
            if ((double)random.nextInt(101)/100 < Pm) {
                mojbinarny[i] = String.valueOf((Integer.parseInt(mojbinarny[i]) == 1) ? 0 : 1);
            } else if(typSukcesji == TypSukcesji.TRYWIALNA){
                mojbinarny[i] = String.valueOf(mojbinarny[i]);
            }
        }

        for(String mb: mojbinarny) {
            rBinarny+=mb;
        }

        daneWyjsciowe.add(new DaneWyjsciowe(rBinarny, dw.getRastrigina()));
    }

    public void zamienWartosci(Wymiar w) {

        int[][] zamienioneWartosci = new int[n][w.getM()];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < w.getM(); j++) {
                if(w.getLosoweWartosciZeroJeden()[i][j] < Pm) {
                    zamienioneWartosci[i][j] = (w.getBinarny()[i][j] == 1) ? 0 : 1;
                } else {
                    zamienioneWartosci[i][j] = w.getBinarny()[i][j];
                }
            }
        }

        w.setWynikiMutacji(zamienioneWartosci);
    }

    public void wypiszZamienioneWartosci(Wymiar w) {

        System.out.println("Zamienione wartosci");
        int[][] tablica = w.getWynikiMutacji();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < w.getM(); j++){
                System.out.print(tablica[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void stworzTabliceZWartosciamiZeroJeden(Wymiar w) {
        Random random = new Random();
        int r = 0;
//        System.out.println("result: " + r/100);

        double[][] losoweWartosciZeroJeden = new double[n][w.getM()];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < w.getM(); j++){
                r = (random.nextInt(101));
                losoweWartosciZeroJeden[i][j] = (double)r/100;
            }
        }

        w.setLosoweWartosciZeroJeden(losoweWartosciZeroJeden);

    }

    public void wypiszTabliceZWartosciamiZeroJeden(Wymiar w) {

        System.out.println("Losowe wartosci r:");
        double[][] tablica = w.getLosoweWartosciZeroJeden();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < w.getM(); j++){
                System.out.print(tablica[i][j] + " ");
            }
            System.out.println();
        }

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

    public double obliczM(double iloscLiczbWZbiorze) {
        int i = 1;
        while(iloscLiczbWZbiorze > Math.pow(2, i)) {
            i++;
        }

        return i;
    }

    public double obliczIloscLiczbWZbiorze2(Wymiar wymiar) {
        return (wymiar.getB() - wymiar.getA()) * Math.pow(10, wymiar.getD()) + 1;
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
