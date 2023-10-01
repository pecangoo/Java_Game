package edu.game.application;

import com.beust.jcommander.JCommander;
import edu.chaseLogic.Field;
import edu.chaseLogic.Logic;
import edu.chaseLogic.exceptions.IllegalParametersException;
import edu.game.Viewer;
import edu.game.utils.CommandLineArgs;

public class Main {
    public static void main(String[] args) {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder()
                .addObject(commandLineArgs)
                .build()
                .parse(args);
        checkInputParams(commandLineArgs.size,
                commandLineArgs.enemiesCount,
                commandLineArgs.wallsCount);
        Field field = new Field(commandLineArgs.size, commandLineArgs.size);
        Logic logic = new Logic(field, commandLineArgs.enemiesCount,
                commandLineArgs.wallsCount, commandLineArgs.profile);
        Viewer viewer = new Viewer(logic);
        viewer.startGame();
    }

    public static void checkInputParams(int size, int
            enemiesCount, int wallsCount) {
        if (size * size - (wallsCount + enemiesCount + 2 + 10) < 0) {
            throw new IllegalParametersException("Неверное сочетание " +
                    "параметров. Сделайте больше больше");
        }
    }
}