package operatoryGenetyczne;

import Sukcesja.TypSukcesji;
import model.DaneWyjsciowe;
import model.Wymiar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inwersja {
    private static final int n = 10;
    private static final double Pi = 0.1;

    private ArrayList<DaneWyjsciowe> daneWyjsciowe = new ArrayList<>();

    public ArrayList<DaneWyjsciowe> start2(ArrayList<DaneWyjsciowe> daneWejsciowe, TypSukcesji typSukcesji) {
        for(DaneWyjsciowe dw: daneWejsciowe) {
            ustawInwersje2(dw, typSukcesji);
        }

        return daneWyjsciowe;
    }

    public void ustawInwersje2(DaneWyjsciowe dw, TypSukcesji typSukcesji) {
        int r = 0;
        int r2 = 0;
        int r3 = 0;
        Random random = new Random();
        String[] mojbinarny = dw.getBinarny().split("");
        String[] rBinarny=  new String[mojbinarny.length];

        r = (random.nextInt(101));

//        System.out.println("wartosc r rowna:"  + (double)r/100 + " < " + Pi);

        if((double)r/100 < Pi) {
            do{
                r2 = random.nextInt(mojbinarny.length);
                r3 = random.nextInt(mojbinarny.length);
            }while (r2 >= r3);

//            System.out.println(  "r2 - r3 : " + r2 + " - " + r3);
//                System.out.println("Dla: " + i  + " o wartosci r rownym:"  + (double)r/100 + " < " + Pi +  " : " + r2 + " - " + r3);

            //0 - r2
            for(int j = 0; j < r2; j++) {
                rBinarny[j] = mojbinarny[j];
            }

            //r3 - m
            for(int j = r3; j < mojbinarny.length; j++) {
                rBinarny[j] = mojbinarny[j];
            }

            //r2-r3
            for(int j = r3, k = r2; j >= r2; j--, k++) {
                rBinarny[k] = mojbinarny[j];
            }

            StringBuilder wBinarny = new StringBuilder();
            for(String b: rBinarny) {
                wBinarny.append(b);
            }
            daneWyjsciowe.add(new DaneWyjsciowe(wBinarny.toString(), dw.getRastrigina()));

        } else if(typSukcesji == TypSukcesji.TRYWIALNA){
            for(int j = 0; j < mojbinarny.length; j++) {
                rBinarny[j] = mojbinarny[j];
            }

            StringBuilder wBinarny = new StringBuilder();
            for(String b: rBinarny) {
                wBinarny.append(b);
            }
            daneWyjsciowe.add(new DaneWyjsciowe(wBinarny.toString(), dw.getRastrigina()));
        }


    }


}
