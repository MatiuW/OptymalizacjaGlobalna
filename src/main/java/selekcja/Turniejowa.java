package selekcja;

import model.DaneWyjsciowe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Turniejowa implements Selekcja{

    private ArrayList<DaneWyjsciowe> daneWejsciowe;
    private ArrayList<DaneWyjsciowe> daneWyjsciowe;
    private MinMax minMax;
    private boolean czyZwracane;

    public Turniejowa(ArrayList<DaneWyjsciowe> daneWejsciowe, MinMax minMax, boolean czyZwracane) {
        this.daneWejsciowe = daneWejsciowe;
        this.minMax = minMax;
        this.czyZwracane = czyZwracane;
        daneWyjsciowe = new ArrayList<>();
    }
    @Override
    public double[] start() {
//        System.out.println("Metoda turniejowa");
//        wyborGrup();
        return null;
    }

    @Override
    public ArrayList<DaneWyjsciowe> start2() {
        wyborGrup();
        return daneWyjsciowe;
    }

    public void wyborGrup() {
        Random random = new Random();
        int p;
        int d;
        int iloscCzlonkowGrupy;
        ArrayList<Integer> arL = new ArrayList<>();
        for(int i = 0 ; i < daneWejsciowe.size(); i++) {

            arL.clear();

            iloscCzlonkowGrupy = random.nextInt(daneWejsciowe.size()) + 1;

//            System.out.println("Grupa: " + (i + 1) + ", ilosc czlonkow grupy: " + iloscCzlonkowGrupy);

            if(minMax == MinMax.MAX && !czyZwracane) { // bez powtorzen

                //tworzenie grupy bez powtorzen
                do {
                    p = random.nextInt(daneWejsciowe.size());
                    if(!czyPowtorzone(arL ,p)){
                        arL.add(p);
//                        System.out.println("dodano osobnika: " + p + ", o wartosci: " + daneWejsciowe.get(p).getRastrigina());
                    }
                }while(arL.size() != iloscCzlonkowGrupy);

                //wybieranie max z grupy
                daneWyjsciowe.add(sprawdzMax(arL));
            }

            if(minMax == MinMax.MAX && czyZwracane) {//moga byc powtorzenia

                //tworzenie grupy z powtorzeniami
                do {
                    p = random.nextInt(daneWejsciowe.size());
                    arL.add(p);
//                    System.out.println("dodano osobnika: " + p + ", o wartosci: " + daneWejsciowe.get(p).getRastrigina());
                }while(arL.size() != iloscCzlonkowGrupy);

                daneWyjsciowe.add(sprawdzMax(arL));
            }
//
            if(minMax == minMax.MIN && !czyZwracane) {

                //tworzenie grupy bez powtorzen
                do {
                    p = random.nextInt(daneWejsciowe.size());
                    if(!czyPowtorzone(arL ,p)){
                        arL.add(p);
//                        System.out.println("dodano osobnika: " + p + ", o wartosci: " + daneWejsciowe.get(p).getRastrigina());
                    }
                }while(arL.size() != iloscCzlonkowGrupy);

                daneWyjsciowe.add(sprawdzMin(arL));
            }

            if(minMax == minMax.MIN && czyZwracane) {

                //tworzenie grupy z powtorzeniami
                do {
                    p = random.nextInt(daneWejsciowe.size());
                    arL.add(p);
//                    System.out.println("dodano osobnika: " + p + ", o wartosci: " + daneWejsciowe.get(p).getRastrigina() + " " + daneWejsciowe.get(p).getBinarny());
                }while(arL.size() != iloscCzlonkowGrupy);

                daneWyjsciowe.add(sprawdzMin(arL));
            }
        }

    }

    private DaneWyjsciowe sprawdzMax(ArrayList<Integer> arL) {
        double max = -1;
        String binarny = "";
        for(int v: arL) {
            if(daneWejsciowe.get(v).getRastrigina() > max) {
                max = daneWejsciowe.get(v).getRastrigina();
                binarny = daneWejsciowe.get(v).getBinarny();
            }
        }

//        System.out.println("Max to: " + max);
        return new DaneWyjsciowe(binarny, max);
    }

    private DaneWyjsciowe sprawdzMin(ArrayList<Integer> arL) {
//        double min = daneWejsciowe[0];
        double min = daneWejsciowe.get(arL.get(0)).getRastrigina();
        String binarny = daneWejsciowe.get(arL.get(0)).getBinarny();
        for(int v: arL) {
            if(daneWejsciowe.get(v).getRastrigina() < min) {
                min = daneWejsciowe.get(v).getRastrigina();
                binarny = daneWejsciowe.get(v).getBinarny();
            }
        }

//        System.out.println("Min to: " + min);
        return new DaneWyjsciowe(binarny, min);
    }

    private boolean czyPowtorzone(ArrayList<Integer> arL, int p) {
        for(int i: arL) {
            if(i == p) {
                return true;
            }
        }

        return false;
    }
}
