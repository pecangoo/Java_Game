package edu.game.utils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {
    @Parameter(names = "--enemiesCount")
    public int enemiesCount;

    @Parameter(names = "--wallsCount")
    public int wallsCount;

    @Parameter(names = "--size")
    public int size;

    @Parameter(names = "--profile")
    public String profile;
}
