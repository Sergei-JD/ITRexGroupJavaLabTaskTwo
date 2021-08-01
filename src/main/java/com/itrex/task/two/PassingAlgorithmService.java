package com.itrex.task.two;

import java.util.ArrayList;

public class PassingAlgorithmService {

    public void passingAlgorithm(String fileName) {
        int lvlSize, rowSize, columnSize;
        ArrayList<Integer> labyrinthSize = new ArrayList<>();
        StringBuilder labyrinthString = new StringBuilder();

        LabyrinthReader labyrinthInput = new LabyrinthReader();
        labyrinthInput.input(fileName, labyrinthSize, labyrinthString);

        if (labyrinthSize.size() != 3) {
            System.out.println("invalid size. should be 3 parameters");
            System.exit(-1);
        }

        lvlSize = labyrinthSize.get(0); // count of levels
        rowSize = labyrinthSize.get(1); // count of rows
        columnSize = labyrinthSize.get(2); // count of columns

        LabyrinthDTO labyrinth = new LabyrinthDTO(lvlSize, rowSize, columnSize);

        fillLabyrinth(labyrinthString, labyrinth);
        labyrinth.printLabyrinth();
        findPath(labyrinth);
        printPathTime(labyrinth);
    }

    private void fillLabyrinth(StringBuilder labyrinthString, LabyrinthDTO labyrinth) {
        for (int i = 0; i < labyrinth.getLvlSize(); ++i) {
            for (int j = 0; j < labyrinth.getRowSize(); ++j) {
                for (int k = 0; k < labyrinth.getColumnSize(); ++k) {
                    if (labyrinthString.charAt(0) == '1') {      //1-Prince position
                        labyrinth.getLabyrinth()[i][j][k] = 1;
                        labyrinthString.deleteCharAt(0);
                    } else if (labyrinthString.charAt(0) == '2') { //2-Princes position
                        labyrinth.getLabyrinth()[i][j][k] = 2;
                        labyrinthString.deleteCharAt(0);
                    } else if (labyrinthString.charAt(0) == '.') { //. - free space, equals 0 in our labyrinth
                        labyrinth.getLabyrinth()[i][j][k] = 0;
                        labyrinthString.deleteCharAt(0);
                    } else if (labyrinthString.charAt(0) == 'o') { //o - column, equals 3 in our labyrinth
                        labyrinth.getLabyrinth()[i][j][k] = 3;
                        labyrinthString.deleteCharAt(0);
                    } else {
                        System.out.println("invalid input");
                        System.exit(-1);
                    }
                }
                labyrinthString.deleteCharAt(0);
            }
        }
    }

    private void findPath(LabyrinthDTO labyrinth) {
        //movementCount: number of steps taken (negative value, each next step decreases the value by 1)
        int currentLvl = 0, currentRow = 0, currentColumn = 0, movementCount = -1;
        boolean moveIsMade = false;

        //make first step if available in all directions
        if (labyrinth.getLabyrinth()[0][0][1] == 0) {
            labyrinth.getLabyrinth()[0][0][1] = movementCount;
        }
        if (labyrinth.getLabyrinth()[0][1][0] == 0) {
            labyrinth.getLabyrinth()[0][1][0] = movementCount;
        }
        if (labyrinth.getLabyrinth()[1][0][0] == 0) {
            labyrinth.getLabyrinth()[1][0][0] = movementCount;
        }

        //we take every step in all possible directions, the cycle runs 1 time for each next step
        while (true) {
            for (int i = 0; i < labyrinth.getLvlSize(); ++i) {
                for (int j = 0; j < labyrinth.getRowSize(); ++j) {
                    for (int k = 0; k < labyrinth.getColumnSize(); ++k) {
                        //if the step at the point corresponds to the current step, we try to take a next step in all possible directions
                        if (labyrinth.getLabyrinth()[i][j][k] == movementCount) {
                            currentLvl = i;
                            currentRow = j;
                            currentColumn = k;

                            if (currentLvl + 1 < labyrinth.getLvlSize() && labyrinth.getLabyrinth()[currentLvl + 1][currentRow][currentColumn] == 0) {
                                labyrinth.getLabyrinth()[currentLvl + 1][currentRow][currentColumn] = movementCount - 1;
                                moveIsMade = true;
                            }

                            if (currentRow + 1 < labyrinth.getRowSize() && labyrinth.getLabyrinth()[currentLvl][currentRow + 1][currentColumn] == 0) {
                                labyrinth.getLabyrinth()[currentLvl][currentRow + 1][currentColumn] = movementCount - 1;
                                moveIsMade = true;
                            }

                            if (currentRow - 1 >= 0 && labyrinth.getLabyrinth()[currentLvl][currentRow - 1][currentColumn] == 0) {
                                labyrinth.getLabyrinth()[currentLvl][currentRow - 1][currentColumn] = movementCount - 1;
                                moveIsMade = true;
                            }

                            if (currentColumn + 1 < labyrinth.getColumnSize() && labyrinth.getLabyrinth()[currentLvl][currentRow][currentColumn + 1] == 0) {
                                labyrinth.getLabyrinth()[currentLvl][currentRow][currentColumn + 1] = movementCount - 1;
                                moveIsMade = true;
                            }

                            if (currentColumn - 1 >= 0 && labyrinth.getLabyrinth()[currentLvl][currentRow][currentColumn - 1] == 0) {
                                labyrinth.getLabyrinth()[currentLvl][currentRow][currentColumn - 1] = movementCount - 1;
                                moveIsMade = true;
                            }
                        }
                    }
                }
            }

            // check if we found princess
            if (labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 1][labyrinth.getColumnSize() - 2] < 0  // the position of the prince in a step to the left of the princess, it is possible to perform a step
              | labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 2][labyrinth.getColumnSize() - 1] < 0  // the position of the prince in a step higher from the princess, it is possible to perform a step
              | labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 2][labyrinth.getRowSize() - 1][labyrinth.getColumnSize() - 1] < 0) // the position of the prince on a higher level above the princess, it is possible to perform a step
            {
                break;
            } else if (moveIsMade) { //if a step is taken then we will try make next step
                moveIsMade = false;
                --movementCount;
            } else { //if a step is not taken, then there is no path
                System.out.println("the labyrinth has no path, princess dies!");
                System.exit(-1);
            }
        }

    }

    private void printPathTime(LabyrinthDTO labyrinth) {
        int minPath, pathTime = 0;
        if (labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 1][labyrinth.getColumnSize() - 2] < 0) {
            minPath = Math.abs(labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 1][labyrinth.getColumnSize() - 2]) + 1; // the position of the prince in a step to the left of the princess, it is possible to perform a step
        } else if (labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 2][labyrinth.getColumnSize() - 1] < 0) {
            minPath = Math.abs(labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 1][labyrinth.getRowSize() - 2][labyrinth.getColumnSize() - 1]) + 1; // the position of the prince in a step higher from the princess, it is possible to perform a step
        } else {
            minPath = Math.abs(labyrinth.getLabyrinth()[labyrinth.getLvlSize() - 2][labyrinth.getRowSize() - 1][labyrinth.getColumnSize() - 1]) + 1; // the position of the prince on a higher level above the princess, it is possible to perform a step
        }
        pathTime = minPath * 5;//5 second for 1 step
        System.out.println("Prince found the Princess for " + pathTime + " seconds");
    }

}