package model;

public class DaneWyjsciowe {

    private String binarny;
    private double rastrigina;


    public String getBinarny() {
        return binarny;
    }

    public void setBinarny(String binarny) {
        this.binarny = binarny;
    }

    public double getRastrigina() {
        return rastrigina;
    }

    public void setRastrigina(double rastrigina) {
        this.rastrigina = rastrigina;
    }

    public DaneWyjsciowe(String binarny, double rastrigina) {
        this.binarny = binarny;
        this.rastrigina = rastrigina;
    }


}
