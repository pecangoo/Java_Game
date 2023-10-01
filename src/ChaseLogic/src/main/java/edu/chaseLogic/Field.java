package edu.chaseLogic;


import edu.chaseLogic.enums.Cell;
import edu.chaseLogic.exceptions.IncorrectPositionException;
import edu.chaseLogic.models.Pair;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int size;
    private final int rows;
    private final int cols;
    private final int[][] array;

    Pair playerPos = new Pair();
    List<Pair> listEnemies = new ArrayList<>();

    public Field(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        size = cols;
        array = new int[rows][cols];
    }

    public void addEnemyToList(Pair enemy) {
        listEnemies.add(enemy);
    }

    public void initPlayerPos(int posX, int posY) {
        playerPos.addValue1(posX);
        playerPos.addValue2(posY);
    }

    public Cell changePlayerPos(char direction) {
        int cellValue = getValueCell(direction,
                playerPos.getValue1(),
                playerPos.getValue2());
        if (cellValue == Cell.ENEMY.get()) {
            return Cell.ENEMY;
        }
        if (cellValue == Cell.GOAL.get()) {
            return Cell.GOAL;
        }
        if (cellValue == Cell.WALL.get()) {
            return Cell.WALL;
        }
        if (cellValue == Cell.EMPTY.get()) {
            changeCells(direction,
                    playerPos.getValue1(),
                    playerPos.getValue2(),
                    playerPos);
            return Cell.EMPTY;
        }
        throw new RuntimeException("Field:changePlayerPos:Error");
    }

    public int changeEnemiesPoses() {
        for (Pair currentEnemy : listEnemies) {
            char direction = whichDirection(currentEnemy);
            int cellValue = getValueCell(direction, currentEnemy.getValue1(),
                    currentEnemy.getValue2());
            if (cellValue == Cell.WALL.get() ||
                    cellValue == Cell.GOAL.get() ||
                    cellValue == Cell.ENEMY.get()) {
                direction = findFreeCell(currentEnemy.getValue1(),
                        currentEnemy.getValue2());
                if (direction == 'x') {
                    System.out.println("Враг не может выйти");
                    continue;
                }
                cellValue = getValueCell(direction,
                        currentEnemy.getValue1(),
                        currentEnemy.getValue2());
            }
            if (cellValue == Cell.PLAYER.get()) {
                return Cell.PLAYER.get();
            }
            if (cellValue == Cell.EMPTY.get()) {
                changeCells(direction, currentEnemy.getValue1(),
                        currentEnemy.getValue2(),
                        currentEnemy);
            }
        }
        return 0;
    }

    private char findFreeCell(int posX, int posY) {
        int current;
        if (posX != 0) {
            current = get(posX - 1, posY);
            if (current == Cell.EMPTY.get() || current == Cell.PLAYER.get()) {
                return 'w';
            }
        } else if (posX == size - 1) {
            current = get(posX + 1, posY);
            if (current == Cell.EMPTY.get() || current == Cell.PLAYER.get()) {
                return 's';
            }
        } else if (posY == 0) {
            current = get(posX, posY - 1);
            if (current == Cell.EMPTY.get() || current == Cell.PLAYER.get()) {
                return 'a';
            }
        } else if (posY == size - 1) {
            current = get(posX, posY + 1);
            if (current == Cell.EMPTY.get() || current == Cell.PLAYER.get()) {
                return 'd';
            }
        }
        return 'x';
    }

    private char whichDirection(Pair currentEnemy) {
        int dy = playerPos.getValue1() - currentEnemy.getValue1();
        int dx = playerPos.getValue2() - currentEnemy.getValue2();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                return 'd';
            }
            if (dx < 0) {
                return 'a';
            }
        } else if (Math.abs(dx) <= Math.abs(dy)) {
            if (dy > 0) {
                return 's';
            } else {
                return 'w';
            }
        }
        throw new RuntimeException("Field: Which Direction Error");
    }

    public int get(int row, int col) {
        if (row > rows || col > cols) {
            throw new IncorrectPositionException("Out of range");
        }
        return array[row][col];
    }


    public void put(int row, int col, int value) {
        array[row][col] = value;
    }

    public int getValueCell(char dir, int posX, int posY) {
        switch (dir) {
            case 'w':
                if (posX == 0) {
                    return Cell.WALL.get();
                }
                return get(posX - 1, posY);
            case 's':
                if (posX == size - 1) {
                    return Cell.WALL.get();
                }
                return get(posX + 1, posY);
            case 'a':
                if (posY == 0) {
                    return Cell.WALL.get();
                }
                return get(posX, posY - 1);
            case 'd':
                if (posY == size - 1) {
                    return Cell.WALL.get();
                }
                return get(posX, posY + 1);
        }
        throw new RuntimeException("Field:GetValueCell:Error");
    }

    public void changeCells(char dir, int posX, int posY, Pair currentItem) {
        int currentCell = get(posX, posY);
        switch (dir) {
            case 'w':
                put(posX, posY, get(posX - 1, posY));
                put(posX - 1, posY, currentCell);
                currentItem.addValue1(posX - 1);
                return;
            case 's':
                put(posX, posY, get(posX + 1, posY));
                put(posX + 1, posY, currentCell);
                currentItem.addValue1(posX + 1);
                return;
            case 'a':
                put(posX, posY, get(posX, posY - 1));
                put(posX, posY - 1, currentCell);
                currentItem.addValue2(posY - 1);
                return;
            case 'd':
                put(posX, posY, get(posX, posY + 1));
                put(posX, posY + 1, currentCell);
                currentItem.addValue2(posY + 1);
                return;
        }
        throw new RuntimeException("Field:ChangeCell:Error");
    }

    public int getSize() {
        return size;
    }
}
