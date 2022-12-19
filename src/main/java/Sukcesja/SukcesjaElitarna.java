package Sukcesja;

import GenerowaniePopulacji.ProgramPierwszy;
import model.DaneWyjsciowe;
import operatoryGenetyczne.Inwersja;
import operatoryGenetyczne.Mutacja;
import operatoryGenetyczne.krzyżowanie.Jednopunktowe;
import selekcja.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SukcesjaElitarna {

    private ArrayList<DaneWyjsciowe> daneWejsciowe;
    private ArrayList<DaneWyjsciowe> wynikiSelekcji;
    private ArrayList<DaneWyjsciowe> wynikiMutacji;
    private ArrayList<DaneWyjsciowe> wynikiInwersji;
    private ArrayList<DaneWyjsciowe> wynikiKrzyzowania;

    public SukcesjaElitarna(ArrayList<DaneWyjsciowe> daneWejsciowe) {
        this.daneWejsciowe = daneWejsciowe;
    }

    public void start() throws IOException {//petla za pomocja rekursji

        Scanner scanner = new Scanner(System.in);


        MinMax minMax = MinMax.MAX;
        boolean czyZwracane = false;

        System.out.println("czy chcesz poszukiwac minimum(min) czy maximum(max)");
        String help = scanner.nextLine();
        if (help.equals("min")) {
            minMax = MinMax.MIN;
        }

        System.out.println("czy chcesz aby dane się powtarzały w grupach(true, false) dla selekcji turniuejowej");
        help = scanner.nextLine();

        if (help.equals("true")) {
            czyZwracane = true;
        }

        System.out.println("Wybierz rodzaj selekcji(rankignowa, turniejowa, ruletka)");
        String typselekcji = scanner.nextLine();

        for(int i = 1; i < 20; i++) {
            System.out.println("--------------------- epoka nr: " + i + " ---------------------");
            System.out.println("Dane wejsciowe");
            for (DaneWyjsciowe dw : daneWejsciowe) {
                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
            }

            //------------------------------------------------------------------------------------
            //selekcja

            if (typselekcji.equals("rankingowa")) {//rankignowa
                wynikiSelekcji = new Rankingowa(daneWejsciowe, minMax).start2();
            } else if (typselekcji.equals("turniejowa")) {//turniejowa
                wynikiSelekcji = new Turniejowa(daneWejsciowe, minMax, czyZwracane).start2();
            } else {//ruletka
                wynikiSelekcji = new Ruletka(daneWejsciowe, minMax).start2();
            }
            //wyniki selekcji
            System.out.println("Wyniki selekcji");
            for (DaneWyjsciowe dw : wynikiSelekcji) {
                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
            }
            //------------------------------------------------------------------------------------
            //mutacja
            wynikiMutacji = new Mutacja().start2(wynikiSelekcji, TypSukcesji.ELITARNA);
            System.out.println("Wyniki mutacji");
            for (DaneWyjsciowe dw : wynikiMutacji) {
                System.out.println(dw.getBinarny());
            }
            //------------------------------------------------------------------------------------
            //inwersja
            wynikiInwersji = new Inwersja().start2(wynikiSelekcji, TypSukcesji.ELITARNA);
            System.out.println("Wyniki inwersji");
            for (DaneWyjsciowe dw : wynikiInwersji) {
                System.out.println(dw.getBinarny());
            }
            //------------------------------------------------------------------------------------
            //krzyzowanie
            wynikiKrzyzowania = new Jednopunktowe().start2(wynikiSelekcji, TypSukcesji.ELITARNA);
            System.out.println("Wyniki krzyzowania");
            for (DaneWyjsciowe dw : wynikiKrzyzowania) {
                System.out.println(dw.getBinarny());
            }
            //------------------------------------------------------------------------------------
            //sukcesja - daneWejsciowe, wynikiMutacji, wynikiInwersji, wynikiKrzyzowania
//            System.out.println(daneWejsciowe.size() + wynikiMutacji.size() + wynikiInwersji.size() + wynikiKrzyzowania.size());

            ArrayList<DaneWyjsciowe> elitarna = new ArrayList<>();
            //laczenie list
            wynikiMutacji = new ProgramPierwszy().ocenaOsobnikow(wynikiMutacji);
            wynikiInwersji = new ProgramPierwszy().ocenaOsobnikow(wynikiInwersji);
            wynikiKrzyzowania = new ProgramPierwszy().ocenaOsobnikow(wynikiKrzyzowania);

            elitarna.addAll(daneWejsciowe);
            elitarna.addAll(wynikiMutacji);
            elitarna.addAll(wynikiInwersji);
            elitarna.addAll(wynikiKrzyzowania);

//            System.out.println("elitarna");
//            for (DaneWyjsciowe dw : elitarna) {
//                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
//            }
            //tutaj zamienic na inny typ sukcesji

            //sortowanie
            if(minMax == MinMax.MAX) {
                for (int j = 0; j < elitarna.size(); j++){
                    for (int k = 1; k < elitarna.size() - j; k++) {
                        if(elitarna.get(k-1).getRastrigina() < elitarna.get(k).getRastrigina()) {
                            Collections.swap(elitarna, k-1, k);
                        }
                    }
                }
            }

            if(minMax == MinMax.MIN) {
                for (int j = 0; j < elitarna.size(); j++){
                    for (int k = 1; k < elitarna.size() - j; k++) {
                        if(elitarna.get(k-1).getRastrigina() > elitarna.get(k).getRastrigina()) {
                            Collections.swap(elitarna, k-1, k);
                        }
                    }
                }
            }

//            System.out.println("elitarna posortowana");
//            for (DaneWyjsciowe dw : elitarna) {
//                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
//            }
            elitarna.subList(daneWejsciowe.size(), elitarna.size()).clear();

            daneWejsciowe = elitarna;
        }

        System.out.println("Dane ostateczne");
        for (DaneWyjsciowe dw : daneWejsciowe) {
            System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
        }

        zapisSredniejDoPliku(daneWejsciowe);
    }
    public void zapisSredniejDoPliku(ArrayList<DaneWyjsciowe> daneWejsciowe) throws IOException {
        double min = daneWejsciowe.get(0).getRastrigina(), max = daneWejsciowe.get(0).getRastrigina(), srednia;
        double sum = 0;
        for (DaneWyjsciowe dw : daneWejsciowe) {
            if(dw.getRastrigina() < min) {
                min = dw.getRastrigina();
            }

            if(dw.getRastrigina() > max) {
                max = dw.getRastrigina();
            }
            sum += dw.getRastrigina();
        }
        srednia = sum/daneWejsciowe.size();

        Writer output = new BufferedWriter(new FileWriter("srednia.txt", true));
        output.write("\n" + min + " " + max + " " + srednia);

        output.close();
    }
}
