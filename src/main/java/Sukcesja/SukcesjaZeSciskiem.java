package Sukcesja;

import GenerowaniePopulacji.ProgramPierwszy;
import model.DaneWyjsciowe;
import operatoryGenetyczne.Inwersja;
import operatoryGenetyczne.Mutacja;
import operatoryGenetyczne.krzyżowanie.Jednopunktowe;
import selekcja.MinMax;
import selekcja.Rankingowa;
import selekcja.Ruletka;
import selekcja.Turniejowa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SukcesjaZeSciskiem {
    private ArrayList<DaneWyjsciowe> daneWejsciowe;
    private ArrayList<DaneWyjsciowe> wynikiSelekcji;
    private ArrayList<DaneWyjsciowe> wynikiMutacji;
    private ArrayList<DaneWyjsciowe> wynikiInwersji;
    private ArrayList<DaneWyjsciowe> wynikiKrzyzowania;

    public SukcesjaZeSciskiem(ArrayList<DaneWyjsciowe> daneWejsciowe) {
        this.daneWejsciowe = daneWejsciowe;
    }

    public void start() {//petla za pomocja rekursji

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

        for(int i = 1; i < 11; i++) {
            System.out.println("--------------------- epoka nr: " + i + " ---------------------");
            System.out.println("Dane wejsciowe");
            for (DaneWyjsciowe dw : daneWejsciowe) {
                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
            }

            //------------------------------------------------------------------------------------
            //selekcja
            System.out.println("Wybierz rodzaj selekcji(rankignowa, turniejowa, ruletka)");
            String typselekcji = scanner.nextLine();

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
            System.out.println(daneWejsciowe.size() + wynikiMutacji.size() + wynikiInwersji.size() + wynikiKrzyzowania.size());

            ArrayList<DaneWyjsciowe> zesciskiem = new ArrayList<>();
            //laczenie list
            wynikiMutacji = new ProgramPierwszy().ocenaOsobnikow(wynikiMutacji);
            wynikiInwersji = new ProgramPierwszy().ocenaOsobnikow(wynikiInwersji);
            wynikiKrzyzowania = new ProgramPierwszy().ocenaOsobnikow(wynikiKrzyzowania);

            zesciskiem.addAll(daneWejsciowe);
            zesciskiem.addAll(wynikiMutacji);
            zesciskiem.addAll(wynikiInwersji);
            zesciskiem.addAll(wynikiKrzyzowania);

            System.out.println("ze sciskiem");
            for (DaneWyjsciowe dw : zesciskiem) {
                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
            }
            //tutaj zamienic na inny typ sukcesji

            ArrayList<DaneWyjsciowe> zesciskiemPosortowana = new ArrayList<>();
            for(int j = 0 ; j < zesciskiem.size(); j++) {
                if(!czyposiada(zesciskiemPosortowana, zesciskiem.get(j).getRastrigina())) {
                    zesciskiemPosortowana.add(zesciskiem.get(j));
                }
            }

            daneWejsciowe = zesciskiemPosortowana;
        }

        System.out.println("Dane ostateczne");
        for (DaneWyjsciowe dw : daneWejsciowe) {
            System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
        }
    }

    public boolean czyposiada(ArrayList<DaneWyjsciowe> lista, double rastrigina) {
        for(DaneWyjsciowe dw: lista) {
            if(dw.getRastrigina() == rastrigina) {
                return true;
            }
        }

        return false;
    }
}
