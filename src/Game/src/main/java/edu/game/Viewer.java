package edu.game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.chaseLogic.Logic;
import edu.chaseLogic.enums.Cell;
import edu.game.utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Viewer {
    private final ColoredPrinter coloredPrinter;

    private final char emptySymbol;
    private final char enemySymbol;
    private final char wallSymbol;
    private final char playerSymbol;
    private final char goalSymbol;

    private final Ansi.BColor emptyColor;
    private final Ansi.BColor enemyColor;
    private final Ansi.BColor wallColor;
    private final Ansi.BColor playerColor;
    private final Ansi.BColor goalColor;

    Logic logic;

    public Viewer(Logic logic) {
        this.logic = logic;
        coloredPrinter = new ColoredPrinter.Builder(1, false)
                .foreground(Ansi.FColor.BLACK).build();
        String urlProp = String.valueOf(Viewer.class
                        .getResource("/application-" +
                                logic.getProfile() + ".properties"))
                .substring(5);
        HashMap<String, String> properties =
                Parser.parsePropertiesFile(urlProp, logic.getProfile());
        checkProperties(properties);

        emptySymbol = properties.get("empty.char").charAt(0);
        enemySymbol = properties.get("enemy.char").charAt(0);
        wallSymbol = properties.get("wall.char").charAt(0);
        playerSymbol = properties.get("player.char").charAt(0);
        goalSymbol = properties.get("goal.char").charAt(0);

        emptyColor = Ansi.BColor.valueOf(properties.get("empty.color"));
        enemyColor = Ansi.BColor.valueOf(properties.get("enemy.color"));
        wallColor = Ansi.BColor.valueOf(properties.get("wall.color"));
        playerColor = Ansi.BColor.valueOf(properties.get("player.color"));
        goalColor = Ansi.BColor.valueOf(properties.get("goal.color"));

    }

    private void checkProperties(HashMap<String, String> properties) {
        List<String> listOfPropertiesName = new ArrayList<>();
        listOfPropertiesName.add("enemy.char");
        listOfPropertiesName.add("player.char");
        listOfPropertiesName.add("wall.char");
        listOfPropertiesName.add("goal.char");
        listOfPropertiesName.add("empty.char");
        listOfPropertiesName.add("enemy.color");
        listOfPropertiesName.add("player.color");
        listOfPropertiesName.add("wall.color");
        listOfPropertiesName.add("goal.color");
        listOfPropertiesName.add("empty.color");

        for (String property : listOfPropertiesName) {
            if (properties.get(property) == null) {
                System.err.println("Wrong input property");
                System.exit(-1);
            }
        }
    }

    public void startGame() {
        logic.randomGenerateField();
        draw();
        while (true) {
            int resultInput = logic.getInputAndChangePlayerPosition();
            if (resultInput == -1) {
                draw();
                continue;
            } else if (resultInput == -2) {
                System.exit(0);
            }
            if (resultInput == Cell.ENEMY.get()) {
                System.out.println("Game Over");
                break;
            }
            if (resultInput == Cell.GOAL.get()) {
                System.out.println("You Win!!!");
                break;
            }
            if (logic.moveEnemies() == 1) {
                System.out.println("Game Over");
                break;
            }
            draw();
        }
    }

    public void draw() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < logic.getField().getSize(); i++) {
            for (int y = 0; y < logic.getField().getSize(); y++) {
                if (logic.getField().get(i, y) == Cell.EMPTY.get()) {
                    coloredPrinter.setBackgroundColor(emptyColor);
                    coloredPrinter.print(" " + emptySymbol + " ");
                } else if (logic.getField().get(i, y) == Cell.PLAYER.get()) {
                    coloredPrinter.setBackgroundColor(playerColor);
                    coloredPrinter.print(" " + playerSymbol + " ");
                } else if (logic.getField().get(i, y) == Cell.ENEMY.get()) {
                    coloredPrinter.setBackgroundColor(enemyColor);
                    coloredPrinter.print(" " + enemySymbol + " ");
                } else if (logic.getField().get(i, y) == Cell.WALL.get()) {
                    coloredPrinter.setBackgroundColor(wallColor);
                    coloredPrinter.print(" " + wallSymbol + " ");
                } else if (logic.getField().get(i, y) == Cell.GOAL.get()) {
                    coloredPrinter.setBackgroundColor(goalColor);
                    coloredPrinter.print(" " + goalSymbol + " ");
                }
            }
            System.out.println();
        }
    }

}
