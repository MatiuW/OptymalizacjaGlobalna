import GenerowaniePopulacji.ProgramPierwszy;
import Sukcesja.SukcesjaElitarna;
import Sukcesja.SukcesjaLosowa;
import Sukcesja.SukcesjaTrywialna;
import Sukcesja.SukcesjaZeSciskiem;
import model.DaneWyjsciowe;
import selekcja.MinMax;
import selekcja.Ruletka;
import selekcja.Turniejowa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//-1 1 6
//-1 1 1

public class Main {
    public static void main(String[] args) throws IOException {

        //lab 1

        ArrayList<DaneWyjsciowe> daneWejsciowe = new ProgramPierwszy().start();

        System.out.println("Dane wejsciowe");
        for (DaneWyjsciowe dw : daneWejsciowe) {
            System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
        }

//                liczSredniaZpliku();

        SukcesjaElitarna sukcesjaElitarna = new SukcesjaElitarna(daneWejsciowe);
        sukcesjaElitarna.start();

//        SukcesjaTrywialna sukcesjaTrywialna = new SukcesjaTrywialna(daneWejsciowe);
//        sukcesjaTrywialna.start();



//        SukcesjaZeSciskiem sukcesjaZeSciskiem = new SukcesjaZeSciskiem(daneWejsciowe);
//        sukcesjaZeSciskiem.start();

//        SukcesjaLosowa sukcesjaLosowa = new SukcesjaLosowa(daneWejsciowe);
//        sukcesjaLosowa.start();
       //lab 2

//        MinMax minMax = MinMax.MAX;
//        boolean czyZwracane = false;
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("czy chcesz poszukiwac minimum(min) czy maximum(max)");
//        String help = scanner.nextLine();
//        if(help.equals("min")) {
//            minMax = MinMax.MIN;
//        }
//
//        System.out.println("czy chcesz aby dane się powtarzały w grupach(true, false)");
//        help = scanner.nextLine();
//        if(help.equals("true")) {
//            czyZwracane = true;
//        }
//
//       Turniejowa t = new Turniejowa(daneWejsciowe, minMax, czyZwracane);
//       ArrayList<DaneWyjsciowe> tWyniki = t.start2();
//
//       System.out.println("Wyniki");
//       for(DaneWyjsciowe dw: tWyniki) {
//           System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
//       }


//        Rankingowa ra = new Rankingowa(daneWejsciowe, minMax);
//        double[] raWyniki = ra.start();
//
//        for(double w: raWyniki) {
//           System.out.println("Rankingowa: " + w);
//       }

//        Ruletka ru = new Ruletka(daneWejsciowe, minMax);
//        ArrayList<DaneWyjsciowe> ruWyniki = ru.start2();
//
//        for(DaneWyjsciowe dw: ruWyniki) {
//            System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
//       }

        //lab 3
//polaczyc wartosci zeby byla wielowymiarowa

//        Mutacja mutacja = new Mutacja();
//        mutacja.start();

//        Inwersja inwersja = new Inwersja();
//        inwersja.start();

        //lab 4 krzyzowanie

//        Jednopunktowe krzyzowanieJednopunktowe = new Jednopunktowe();
//        krzyzowanieJednopunktowe.start();
    }

    public static void liczSredniaZpliku() throws FileNotFoundException {

        double suma = 0;
        int ilosc = 0;
        double min = -1, max = -1;

        File file = new File("srednia.txt");
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(" ");

            suma+=Double.valueOf(values[2]);
            ilosc++;

            if(Double.valueOf(values[0]) < min && min != -1) {
                min = Double.valueOf(values[0]);
            }

            if(min == -1) {
                min = Double.valueOf(values[0]);
            }

            if(Double.valueOf(values[1]) > max && max != -1) {
                max = Double.valueOf(values[1]);
            }

            if(max == -1) {
                max = Double.valueOf(values[1]);
            }
        }
        System.out.println("srednia: " + suma/ilosc);
        System.out.println("min: " + min);
        System.out.println("max: " + max);
    }
}
