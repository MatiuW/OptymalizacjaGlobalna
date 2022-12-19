package operatoryGenetyczne.krzyżowanie;

import Sukcesja.TypSukcesji;
import model.DaneWyjsciowe;
import model.Wymiar;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Jednopunktowe {

    private static final int n = 20;
    private static final double Pk = 0.5;
    private ArrayList<Integer> listD = new ArrayList<>();
    private ArrayList<Integer> listAk = new ArrayList<>();
    private ArrayList<Integer> listBk = new ArrayList<>();

    private ArrayList<Wymiar> wymiar = new ArrayList<>();

    private int[][] binarny;
    private String[] daneWejsciowe = new String[n];
    private String[] r = new String[n];
    private Random random = new Random();
    private double[] randomZeroJeden = new double[n];
    private ArrayList<Integer> wybraniOsobnicy = new ArrayList<>();
    private HashMap<Integer, Integer> pary = new HashMap<>();
    private ArrayList<String> paryDoKrzyzowania = new ArrayList<>();
    private ArrayList<String> daneWyjsciowe2 = new ArrayList<>();

    private ArrayList<DaneWyjsciowe> daneWyjsciowe = new ArrayList<>();

    public ArrayList<DaneWyjsciowe> start2(ArrayList<DaneWyjsciowe> daneWejsciowem, TypSukcesji typSukcesji) {

        for(int i =0; i< daneWejsciowem.size(); i++) {
            randomZeroJeden[i] = (double)random.nextInt(101)/100;
            if(randomZeroJeden[i] <= Pk) {
                wybraniOsobnicy.add(i);
            }
        }
        //jest liczba osobnikow jest nieparzysta, wtedy dobierany jest nowy osobnik
        if(wybraniOsobnicy.size()%2!=0) {
            int p;
            do{
                p = random.nextInt(daneWejsciowe.length);
            }while(czyPowtorzone(wybraniOsobnicy ,p));
            wybraniOsobnicy.add(p);
        }

        //jesli liczba wybranych osobnikow jest rowna 0
        //todo
        if(wybraniOsobnicy.size() == 0) {
            wybraniOsobnicy.add(0);
            wybraniOsobnicy.add(1);
        }

//        System.out.print("Wylosowani osobnicy: ");
//        for(int wO: wybraniOsobnicy) {
//            System.out.print(wO + ", ");
//        }

//        System.out.println();
        //laczenie losowo w pary
        for(int i = 0; i < wybraniOsobnicy.size()/2; i++) {
            int k;
            int v;
            do {
                k = random.nextInt(wybraniOsobnicy.size());
                v = random.nextInt(wybraniOsobnicy.size());
            }while(czyPowtorzone2(k, v));

            pary.put(k, v);
        }

        //wylosowane pary
//        System.out.println("Wylosowane pary");
//        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
//            System.out.println(wybraniOsobnicy.get(set.getKey()) + " " + wybraniOsobnicy.get(set.getValue()));
//        }

        ArrayList<Integer> mojePary = new ArrayList<>();
        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
//            System.out.println(wybraniOsobnicy.get(set.getKey()) + " " + wybraniOsobnicy.get(set.getValue()));
            mojePary.add(wybraniOsobnicy.get(set.getKey()));
            mojePary.add(wybraniOsobnicy.get(set.getValue()));
        }


        //dodanie do danych wyjsciowych pozostalych osobnikow
        if(typSukcesji == TypSukcesji.TRYWIALNA) {
            for(int i = 0; i < daneWejsciowem.size(); i++) {

                if(!mojePary.contains(i)) {
//                    System.out.println("nie zawiera: " + i);
                    daneWyjsciowe.add(daneWejsciowem.get(i));
                }
            }
        }


        //tworzenie danych wejsciowych do krzyzowania

//        System.out.println("tworzenie danych wejsciowych do krzyzowania");
        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
//            System.out.println(daneWejsciowe[wybraniOsobnicy.get(set.getKey())] + " " + daneWejsciowe[wybraniOsobnicy.get(set.getValue())]);
            paryDoKrzyzowania.add(daneWejsciowem.get(wybraniOsobnicy.get(set.getKey())).getBinarny() + " " + daneWejsciowem.get(wybraniOsobnicy.get(set.getValue())).getBinarny());
        }

        krzyzowanieWielopunktowe2(daneWejsciowem.get(0).getBinarny().length());


        return daneWyjsciowe;
    }

    public void krzyzowanieWielopunktowe2(int sumaWymiarow) {
        for(String daneKrzyowanie: paryDoKrzyzowania) {
            String[] chromosom = daneKrzyowanie.split(" ");
//            System.out.println("-------------------------------------------------------------------");
//            System.out.println("para wejsciowa chromosomow: " + chromosom[0] + ":" + chromosom[1]);

            int iloscPunktow = random.nextInt(sumaWymiarow) + 1;//tutaj ilosc punktow przeciecia
//            int iloscPunktow = 4;

            ArrayList<Integer> punktyPrzeciecia = new ArrayList<>();
            int punktPrzeciecia;
            for(int i = 0; i < iloscPunktow; i++) {
                do {
                    punktPrzeciecia = random.nextInt(chromosom[0].length());

                }while(czyPowtorzone(punktyPrzeciecia, punktPrzeciecia));
                punktyPrzeciecia.add(punktPrzeciecia);
            }
            Collections.sort(punktyPrzeciecia);

            StringBuilder potomekPierwszy = new StringBuilder();
            StringBuilder potomekDrugi = new StringBuilder();

//            System.out.print("ilosc punktów przecięcia: " + iloscPunktow + ". Pumkty przecięcia: ");
//            for(int p: punktyPrzeciecia) {
//                System.out.print(p + ", ");
//            }
//            System.out.println();
            for(int i = 0; i < punktyPrzeciecia.size(); i++) {
                //dodanie pierwszego
                if(i == 0) {
//                    System.out.println("chromosom 1 - od " + 0 + " do " + (punktyPrzeciecia.get(i) ) + " ,czyli: " + chromosom[0].substring(0, punktyPrzeciecia.get(i) + 1));
                    potomekPierwszy.append(chromosom[0].substring(0, punktyPrzeciecia.get(i) + 1));
                    potomekDrugi.append(chromosom[1].substring(0, punktyPrzeciecia.get(i) + 1));
                }

                if(i%2 == 0 && i != 0 ) {//lista 1
//                    System.out.println("chromosom 1 - od " + (punktyPrzeciecia.get(i - 1) + 1) + " do " + (punktyPrzeciecia.get(i)) + " ,czyli: " + chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));

                    potomekPierwszy.append(chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                    potomekDrugi.append(chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                } else if( i%2 != 0 && i != 0){//lista 2
//                    System.out.println("chromosom 2 - od " + (punktyPrzeciecia.get(i - 1) + 1)  + " do " + (punktyPrzeciecia.get(i)) + " ,czyli: " + chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));

                    potomekPierwszy.append(chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                    potomekDrugi.append(chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                }

                //dodanie ostatniego
                if(punktyPrzeciecia.get(punktyPrzeciecia.size() - 1) < chromosom[0].length() -1 && i == punktyPrzeciecia.size() - 1) {
//                    System.out.print("Trzeba dodac ostatniego tutaj:");
                    if(i%2 != 0) {//odwrotnie
//                        System.out.println("chromosom 1 - od " + (punktyPrzeciecia.get(i) + 1) + " do " + (chromosom[0].length() - 1) + " ,czyli: " + chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ) );

                        potomekPierwszy.append(chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ));
                        potomekDrugi.append(chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ));
                    } else if(i%2 == 0) {
//                        System.out.println("chromosom 2 - od " + (punktyPrzeciecia.get(i) + 1) + " do " + (chromosom[0].length() - 1) + " ,czyli: " + chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ) );

                        potomekPierwszy.append(chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ));
                        potomekDrugi.append(chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((chromosom[0].length() )) ));
                    }
                }
//
            }

            daneWyjsciowe2.add(potomekPierwszy.toString() + " " + potomekDrugi.toString());
//            System.out.println("Potomek pierwszy: " + potomekPierwszy + "/Potomek drugi: " + potomekDrugi);

            daneWyjsciowe.add(new DaneWyjsciowe(potomekPierwszy.toString(), 0.0));
            daneWyjsciowe.add(new DaneWyjsciowe(potomekDrugi.toString(), 0.0));
        }
    }

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
        }

        //polaczyc wszystkie wymiary w jeden chromosom

        for(int i = 0; i < daneWejsciowe.length; i++) {
            daneWejsciowe[i] = "";
        }

        for(Wymiar w: wymiar) {
            int[][] h = w.getBinarny();

            for(int i = 0; i < n; i++) {
                for(int j = 0; j < w.getM(); j++) {
                    daneWejsciowe[i] += h[i][j];
                }
            }
        }

        //wypisanie danych wejsciowych
        System.out.println("dane wejsciowe");
        for(String w: daneWejsciowe) {
            System.out.println(w);
        }

        //losowanie dla kazdego chromosomu liczbe z przedzialu 0,1
        //i wybor indeksow osobnikow
        for(int i =0; i< n; i++) {
            randomZeroJeden[i] = (double)random.nextInt(101)/100;
            if(randomZeroJeden[i] <= Pk) {
                wybraniOsobnicy.add(i);
            }
        }
        //jest liczba osobnikow jest nieparzysta, wtedy dobierany jest nowy osobnik
        if(wybraniOsobnicy.size()%2!=0) {
            int p;
            do{
                p = random.nextInt(daneWejsciowe.length);
            }while(czyPowtorzone(wybraniOsobnicy ,p));
            wybraniOsobnicy.add(p);
        }

        System.out.print("Wylosowani osobnicy: ");
        for(int wO: wybraniOsobnicy) {
            System.out.print(wO + ", ");
        }

        System.out.println();
        //laczenie losowo w pary
        for(int i = 0; i < wybraniOsobnicy.size()/2; i++) {
            int k;
            int v;
            do {
                k = random.nextInt(wybraniOsobnicy.size());
                v = random.nextInt(wybraniOsobnicy.size());
            }while(czyPowtorzone2(k, v));

            pary.put(k, v);
        }

        //wylosowane pary
        System.out.println("Wylosowane pary");
        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
            System.out.println(wybraniOsobnicy.get(set.getKey()) + " " + wybraniOsobnicy.get(set.getValue()));
        }

        //tworzenie danych wejsciowych do krzyzowania

//        System.out.println("tworzenie danych wejsciowych do krzyzowania");
        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
//            System.out.println(daneWejsciowe[wybraniOsobnicy.get(set.getKey())] + " " + daneWejsciowe[wybraniOsobnicy.get(set.getValue())]);
            paryDoKrzyzowania.add(daneWejsciowe[wybraniOsobnicy.get(set.getKey())] + " " + daneWejsciowe[wybraniOsobnicy.get(set.getValue())]);
        }
        //krzyzowanie jednopunktowe
        int sumaWymiarow = 0;
        for(Wymiar w: wymiar) {
            sumaWymiarow+=w.getM();
        }

        //krzyzowanie jednopunktowe
//        krzyzowanieJednopunktowe(sumaWymiarow);

        //krzyzowanie wielopunktowe
        krzyzowanieWielopunktowe(sumaWymiarow);

        //krzyzowanie rownomierne
//        krzyzowanieRownomierne(sumaWymiarow);
    }

    public void krzyzowanieRownomierne(int sumaWymiarow) {
        for(String daneKrzyowanie: paryDoKrzyzowania) {
            System.out.println("-------------------------------------------------------------------");
            String[] chromosom = daneKrzyowanie.split(" ");

            int[] wzorzec = new int[sumaWymiarow];
            StringBuilder potomekPierwszy = new StringBuilder();
            StringBuilder potomekDrugi = new StringBuilder();

            for(int i = 0 ; i < sumaWymiarow; i++) {
                wzorzec[i]  = random.nextInt(2);
            }

            System.out.println("Rodzic 1: " + chromosom[0]);
            System.out.println("Rodzic 2: " + chromosom[1]);

            System.out.print("wzorzec: ");
            for(int i = 0; i < sumaWymiarow; i++) {
                System.out.print(wzorzec[i]);
            }
            System.out.println();

            for(int i = 0; i < sumaWymiarow; i++) {
                if(wzorzec[i] == 0) {
                    potomekPierwszy.append(chromosom[0].substring(i, i+1));
                    potomekDrugi.append(chromosom[1].substring(i, i+1));
                } else if(wzorzec[i] == 1) {
                    potomekPierwszy.append(chromosom[1].substring(i, i+1));
                    potomekDrugi.append(chromosom[0].substring(i, i+1));
                }
            }

            System.out.println("Potomek 1: " + potomekPierwszy + "/Potomek 2: " + potomekDrugi);

            daneWyjsciowe2.add(potomekPierwszy.toString() + " " + potomekDrugi.toString());
        }
    }

    public void krzyzowanieWielopunktowe(int sumaWymiarow) {
        for(String daneKrzyowanie: paryDoKrzyzowania) {
            String[] chromosom = daneKrzyowanie.split(" ");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("para wejsciowa chromosomow: " + chromosom[0] + ":" + chromosom[1]);

            int iloscPunktow = random.nextInt(sumaWymiarow) + 1;//tutaj ilosc punktow przeciecia
//            int iloscPunktow = 1;

            ArrayList<Integer> punktyPrzeciecia = new ArrayList<>();
            int punktPrzeciecia;
            for(int i = 0; i < iloscPunktow; i++) {
                do {
                    punktPrzeciecia = random.nextInt(sumaWymiarow);

                }while(czyPowtorzone(punktyPrzeciecia, punktPrzeciecia));
                punktyPrzeciecia.add(punktPrzeciecia);
            }
            Collections.sort(punktyPrzeciecia);

            StringBuilder potomekPierwszy = new StringBuilder();
            StringBuilder potomekDrugi = new StringBuilder();

            System.out.print("ilosc punktów przecięcia: " + iloscPunktow + ". Pumkty przecięcia: ");
            for(int p: punktyPrzeciecia) {
                System.out.print(p + ", ");
            }
            System.out.println();

            for(int i = 0; i < punktyPrzeciecia.size(); i++) {
                //dodanie pierwszego
                if(i == 0) {
                    System.out.println("chromosom 1 - od " + 0 + " do " + (punktyPrzeciecia.get(i) ) + " ,czyli: " + chromosom[0].substring(0, punktyPrzeciecia.get(i) + 1));
                    potomekPierwszy.append(chromosom[0].substring(0, punktyPrzeciecia.get(i) + 1));
                    potomekDrugi.append(chromosom[1].substring(0, punktyPrzeciecia.get(i) + 1));
                }

                if(i%2 == 0 && i != 0 ) {//lista 1
                    System.out.println("chromosom 1 - od " + (punktyPrzeciecia.get(i - 1) + 1) + " do " + (punktyPrzeciecia.get(i)) + " ,czyli: " + chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));

                    potomekPierwszy.append(chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                    potomekDrugi.append(chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                } else if( i%2 != 0 && i != 0){//lista 2
                    System.out.println("chromosom 2 - od " + (punktyPrzeciecia.get(i - 1) + 1)  + " do " + (punktyPrzeciecia.get(i)) + " ,czyli: " + chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));

                    potomekPierwszy.append(chromosom[1].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                    potomekDrugi.append(chromosom[0].substring((punktyPrzeciecia.get(i - 1) + 1) , (punktyPrzeciecia.get(i) + 1)));
                }

                //dodanie ostatniego
                if(punktyPrzeciecia.get(punktyPrzeciecia.size() - 1) < sumaWymiarow -1 && i == punktyPrzeciecia.size() - 1) {
//                    System.out.print("Trzeba dodac ostatniego tutaj:");
                    if(i%2 != 0) {//odwrotnie
                        System.out.println("chromosom 1 - od " + (punktyPrzeciecia.get(i) + 1) + " do " + (sumaWymiarow - 1) + " ,czyli: " + chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ) );

                        potomekPierwszy.append(chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ));
                        potomekDrugi.append(chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ));
                    } else if(i%2 == 0) {
                        System.out.println("chromosom 2 - od " + (punktyPrzeciecia.get(i) + 1) + " do " + (sumaWymiarow - 1) + " ,czyli: " + chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ) );

                        potomekPierwszy.append(chromosom[1].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ));
                        potomekDrugi.append(chromosom[0].substring( (punktyPrzeciecia.get(i) + 1), ((sumaWymiarow )) ));
                    }
                }

            }

            daneWyjsciowe2.add(potomekPierwszy.toString() + " " + potomekDrugi.toString());
            System.out.println("Potomek pierwszy: " + potomekPierwszy + "/Potomek drugi: " + potomekDrugi);
        }
    }

    public void krzyzowanieJednopunktowe(int sumaWymiarow) {
        for(String daneKrzyowanie: paryDoKrzyzowania) {
            String[] s = daneKrzyowanie.split(" ");
             int v = random.nextInt(sumaWymiarow);
             System.out.println("Wylosowany punkt: " + v);
             daneWyjsciowe2.add(s[0].substring(0, v) + s[1].substring(v) + " " + s[1].substring(0, v) + s[0].substring(v));
        }

        System.out.println("dane wyjsciowe krzyzowania jednopunktowego");
        for(String dW: daneWyjsciowe2) {
            System.out.println(dW);
        }
    }

    private boolean czyPowtorzone2(int k, int v) {
        List<Integer> l = new ArrayList<>();
        for(Map.Entry<Integer, Integer> set : pary.entrySet()) {
            l.add(set.getKey());
            l.add(set.getValue());
        }

        if(l.contains(k)) {
//            System.out.println("Lista zwiera element: " + k);
            return true;
        }

        if(l.contains(v)) {
//            System.out.println("Lista zwiera element: " + v);
            return true;
        }

        if( k==v) {
//            System.out.println("tutaj");
            return true;
        }

//        System.out.println(k + " " + v);
        return false;
    }

    private boolean czyPowtorzone(ArrayList<Integer> arL, int p) {
        for(int i: arL) {
            if(i == p) {
                return true;
            }
        }

        return false;
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
}
