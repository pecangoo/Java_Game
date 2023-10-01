package edu.chaseLogic;

import edu.chaseLogic.enums.Cell;
import edu.chaseLogic.models.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Logic {
    private final Field field;
    private final int enemiesCount;
    private final int wallsCount;
    private final String profile;

    public Logic(Field field, int enemiesCount, int wallsCount, String profile) {
        this.field = field;
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        this.profile = profile;
    }

    public int getInputAndChangePlayerPosition() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char ch;
        int result = -1;
        try {
            ch = (char) reader.read();
            switch (ch) {
                case 'w':
                    result = field.changePlayerPos('w').get();
                    break;
                case 's':
                    result = field.changePlayerPos('s').get();
                    break;
                case 'a':
                    result = field.changePlayerPos('a').get();
                    break;
                case 'd':
                    result = field.changePlayerPos('d').get();
                    break;
                case '9':
                    return -2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int moveEnemies() {
        int result = field.changeEnemiesPoses();
        if (result == Cell.PLAYER.get()) {
            return 1;
        }
        return 0;
    }

    public void randomGenerateField() {
        Pair XpairY;
        for (int i = 0; i < enemiesCount; i++) {
            XpairY = random();
            field.put(XpairY.getValue1(), XpairY.getValue2(), Cell.ENEMY.get());
            Pair enemy = new Pair(XpairY.getValue1(), XpairY.getValue2());
            field.addEnemyToList(enemy);
        }

        for (int i = 0; i < wallsCount; i++) {
            XpairY = random();
            field.put(XpairY.getValue1(), XpairY.getValue2(), Cell.WALL.get());
        }

        XpairY = random();
        field.put(XpairY.getValue1(), XpairY.getValue2(), Cell.PLAYER.get());
        field.initPlayerPos(XpairY.getValue1(), XpairY.getValue2());

        XpairY = random();
        field.put(XpairY.getValue1(), XpairY.getValue2(), Cell.GOAL.get());
    }

    public Pair random() {
        Pair pair = new Pair();
        Random random = new Random();
        while (true) {
            int xPos = Math.abs(random.nextInt() % field.getSize());
            int yPos = Math.abs(random.nextInt() % field.getSize());
            System.out.println(xPos + " " + yPos);
            System.out.println(field.get(xPos, yPos));
            if (Cell.EMPTY.get() == (field.get(xPos, yPos))) {
                pair.addValue1(xPos);
                pair.addValue2(yPos);
                break;
            }
        }
        return pair;
    }

    public Field getField() {
        return field;
    }

    public String getProfile() {
        return profile;
    }
}
