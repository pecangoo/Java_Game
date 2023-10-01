package edu.chaseLogic.models;

public class Pair {
    private int value1;
    private int value2;

    public Pair() {
    }

    public Pair(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public void addValue1(int value1) {
        this.value1 = value1;
    }

    public void addValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}
