package com.bala.github.cli;

import picocli.CommandLine;

@CommandLine.Command
public class GitCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new GitCommand(), args);
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}