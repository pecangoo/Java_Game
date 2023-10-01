package edu.chaseLogic.enums;

public enum Cell {
    EMPTY(0),
    ENEMY(1),
    WALL(2),
    PLAYER(3),
    GOAL(4);
    private final int val;

    Cell(int val) {
        this.val = val;
    }

    public int get() {
        return val;
    }
}
