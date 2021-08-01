package com.itrex.task.two;

public class Main {

    public static final String FILE_NAME = "src/main/resources/INPUT.txt";

    public static void main(String arg[]) {
        PassingAlgorithmService passingAlgorithmService = new PassingAlgorithmService();
        passingAlgorithmService.passingAlgorithm(FILE_NAME);
    }
}
