package selekcja;

import model.DaneWyjsciowe;

import java.util.*;
import java.util.stream.Collectors;

public class Rankingowa implements Selekcja{

    private ArrayList<DaneWyjsciowe> daneWejsciowe;
    private ArrayList<DaneWyjsciowe> daneWyjsciowe;
    private MinMax minMax;
//    private ArrayList<DaneWyjsciowe> daneWyjscioweMoje = new ArrayList<>();

    public Rankingowa(ArrayList<DaneWyjsciowe> daneWejsciowe, MinMax minMax) {
        this.daneWejsciowe = daneWejsciowe;
        daneWyjsciowe = new ArrayList<>();
        this.minMax = minMax;
    }


    @Override
    public double[] start() {
        return new double[5];
//        Random random = new Random();
//
//        List<Double> mojaLista = new ArrayList<>();
//        List<Double> listaPosortowana = new ArrayList<>();
//
//        for(double i: daneWejsciowe) {
//            mojaLista.add(i);
//        }
//
//        //sortowanie
//        if(minMax == MinMax.MAX) {
//            listaPosortowana = mojaLista.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//        } else if(minMax == MinMax.MIN) {
//            listaPosortowana = mojaLista.stream().sorted().collect(Collectors.toList());
//        }
//
//        System.out.println("Lista posortowana");
//        for(double lp: listaPosortowana) {
//            System.out.println(lp);
//        }
//
//        System.out.println();
//
//        int random1 = 0;
//        int random2 = 0;
//        for(int i = 0; i < listaPosortowana.size(); i++) {
//            random1 = random.nextInt(listaPosortowana.size());
//            if(random1 != 0) {
//                random2 = random.nextInt(random1);
//            }else {
//                random2 = 0;
//            }
//
//
//            System.out.println(random1 + " " + random2 + " " + listaPosortowana.get(random2));
//
//            daneWyjsciowe[i] = listaPosortowana.get(random2);
    }

    @Override
    public ArrayList<DaneWyjsciowe> start2() {

        Random random = new Random();

        if(minMax == MinMax.MIN) {
            for(int i = 0; i < daneWejsciowe.size(); i++) {
                for(int j = 1; j < daneWejsciowe.size()- i; j++) {
                    if(daneWejsciowe.get(j - 1).getRastrigina() > daneWejsciowe.get(j).getRastrigina()) {
                        Collections.swap(daneWejsciowe, j - 1, j);
                    }
                }
            }
        } else if(minMax == MinMax.MAX) {
            for(int i = 0; i < daneWejsciowe.size(); i++) {
                for(int j = 1; j < daneWejsciowe.size()- i; j++) {
                    if(daneWejsciowe.get(j - 1).getRastrigina() < daneWejsciowe.get(j).getRastrigina()) {
                        Collections.swap(daneWejsciowe, j - 1, j);
                    }
                }
            }
        }

        //posortowane
//        for(DaneWyjsciowe dw: daneWejsciowe) {
//            System.out.println(dw.getRastrigina());
//        }

        int random1 = 0;
        int random2 = 0;
        for(int i = 0; i < daneWejsciowe.size(); i++) {
            random1 = random.nextInt(daneWejsciowe.size());
            if (random1 != 0) {
                random2 = random.nextInt(random1);
            } else {
                random2 = 0;
            }

//            System.out.println("wybrano wartosc: " + random2);
            daneWyjsciowe.add(daneWejsciowe.get(random2));
        }

        return daneWyjsciowe;
    }
}
