package selekcja;

import model.DaneWyjsciowe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ruletka implements Selekcja{

    private ArrayList<DaneWyjsciowe> daneWejsciowe;
    private ArrayList<DaneWyjsciowe> daneWyjsciowe;
    private MinMax minMax;

    public Ruletka(ArrayList<DaneWyjsciowe> daneWejsciowe, MinMax minMax) {
        this.daneWejsciowe = daneWejsciowe;
        daneWyjsciowe = new ArrayList<>();
        this.minMax = minMax;
    }

    @Override
    public double[] start() {
        return null;
//        Random random = new Random();
//
//        double suma = 0;
//        double[] p = new double[daneWejsciowe.length];
//        double[] q = new double[daneWejsciowe.length];
//
//        for(double dwe: daneWejsciowe) {
//            suma += dwe;
//        }
//
//        for(int i = 0; i < daneWejsciowe.length; i++) {
//            p[i] = daneWejsciowe[i]/suma;
//            if(i == 0) {
//                q[i] = p[i];
//            } else {
//                q[i] = p[i] + q[i - 1];
//            }
//        }
//
//        for(int i = 0; i < daneWejsciowe.length; i++) {
////            System.out.println("prawdopodobienstwo: " + p[i]);
//            System.out.println("dystrybuanta: " + q[i]);
//        }
//
//        double r;
//
//        for(int i = 0; i < daneWejsciowe.length; i++) {
//            //losowanie liczby[0,1] 2 miejsca po przecinku
//            r = (random.nextInt(101));
//            System.out.println("result: " + r/100);
//
////            random.nextDouble(0,1);
//
//            if(minMax.equals(MinMax.MAX)) {
//                for(int j = 0; j < q.length; j++) {
//                    if(r/100 <= q[1]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j];
//                    } else if(j >= 1&& r/100 > q[j-1] && r/100 <= q[j]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j];
//                    }
//                }
//            }
//
//            if(minMax.equals(MinMax.MIN)) {
//                for(int j = 0; j < q.length; j++) {
//                    if(r/100 <= q[1]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j];
//                    } else if(j >= 1&& r/100 > q[j-1] && r/100 <= q[j]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j - 1];
//                    }
//                }
//            }
//        }
//
//        //wypisanie danych wyjsciowych
////        for(double dw: daneWyjsciowe) {
////            System.out.println("dane wyjsciowe: " + dw);
////        }
//
//        return daneWyjsciowe;
    }

    @Override
    public ArrayList<DaneWyjsciowe> start2() {

        Random random = new Random();

        double suma = 0;
        double[] p = new double[daneWejsciowe.size()];
        double[] q = new double[daneWejsciowe.size()];

        for(DaneWyjsciowe dw: daneWejsciowe) {
            suma += dw.getRastrigina();
        }

        for(int i = 0; i < daneWejsciowe.size(); i++) {
            p[i] = daneWejsciowe.get(i).getRastrigina()/suma;
            if(i == 0) {
                q[i] = p[i];
            } else {
                q[i] = p[i] + q[i - 1];
            }
        }

//        for(int i = 0; i < daneWejsciowe.size(); i++) {
//            System.out.println("prawdopodobienstwo: " + p[i]);
//            System.out.println("dystrybuanta: " + q[i]);
//        }

        double r;

        for(int i = 0; i < daneWejsciowe.size(); i++) {
            //losowanie liczby[0,1] 2 miejsca po przecinku
            r = (random.nextInt(101));
//            System.out.println("prawdopodobienstwo: " + r/100);

//            random.nextDouble(0,1);

            if(minMax.equals(MinMax.MAX)) {

                for(int j = 0; j < daneWejsciowe.size(); j++) {
                    if(r/100 <= q[0] && j == 0) {
                         daneWyjsciowe.add(new DaneWyjsciowe(daneWejsciowe.get(0).getBinarny(), daneWejsciowe.get(0).getRastrigina()));
                    } else if(j >= 1&& r/100 > q[j-1] && r/100 <= q[j]) {
                        daneWyjsciowe.add(new DaneWyjsciowe(daneWejsciowe.get(j).getBinarny(), daneWejsciowe.get(j).getRastrigina()));
                    }
                }

            }

//            if(minMax.equals(MinMax.MIN)) {
//                for(int j = 0; j < q.length; j++) {
//                    if(r/100 <= q[1]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j];
//                    } else if(j >= 1&& r/100 > q[j-1] && r/100 <= q[j]) {
//                        daneWyjsciowe[i] = daneWejsciowe[j - 1];
//                    }
//                }
//            }
        }

        //wypisanie danych wyjsciowych
//        for(double dw: daneWyjsciowe) {
//            System.out.println("dane wyjsciowe: " + dw);
//        }

        return daneWyjsciowe;
    }
}
