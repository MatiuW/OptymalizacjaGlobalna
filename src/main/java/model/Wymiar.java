package model;

import java.util.Arrays;

public class Wymiar {

    private int a;
    private int b;
    private int d;

    private int m;

    private int[][] binarny;
    private int[] dziesietny;
    private double[] x;
    private double rastrigina = 0;

    private double[][] losoweWartosciZeroJeden;
    private int[][] wynikiMutacji;

    private int[][] wynikiInwersji;

    public Wymiar(int a, int b, int d) {
        this.a = a;
        this.b = b;
        this.d = d;
    }

    public Wymiar(int a, int b, int d, int m) {
        this.a = a;
        this.b = b;
        this.d = d;
        this.m = m;
    }

    public Wymiar() {

    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int[][] getBinarny() {
        return binarny;
    }

    public void setBinarny(int[][] b, int n, int m) {
        this.binarny = b;
    }

    public int[] getDziesietny() {
        return dziesietny;
    }

    public void setDziesietny(int[] d) {
        this.dziesietny = d;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x1) {
        this.x = x1;
    }

    public double[][] getLosoweWartosciZeroJeden() {
        return losoweWartosciZeroJeden;
    }

    public void setLosoweWartosciZeroJeden(double[][] lwzj) {
        this.losoweWartosciZeroJeden = lwzj;
    }

    public int[][] getWynikiMutacji() {
        return wynikiMutacji;
    }

    public void setWynikiMutacji(int[][] wynikiMutacji) {
        this.wynikiMutacji = wynikiMutacji;
    }

    public int[][] getWynikiInwersji() {
        return wynikiInwersji;
    }

    public void setWynikiInwersji(int[][] wynikiInwersji) {
        this.wynikiInwersji = wynikiInwersji;
    }

    public double getRastrigina() {
        return rastrigina;
    }

    public void setRastrigina(double rastrigina) {
        this.rastrigina = rastrigina;
    }

    @Override
    public String toString() {
        return "Osobnik{" +
                "a=" + a +
                ", b=" + b +
                ", d=" + d +
                ", m=" + m +
                ", binarny=" + Arrays.toString(binarny) +
                ", dziesietny=" + Arrays.toString(dziesietny) +
                '}';
    }
}
