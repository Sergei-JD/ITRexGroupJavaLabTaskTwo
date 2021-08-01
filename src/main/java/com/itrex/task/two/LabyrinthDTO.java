package com.itrex.task.two;

public class LabyrinthDTO {

    private final int lvlSize;
    private final int rowSize;
    private final int columnSize;
    private final int[][][] labyrinth;

    public LabyrinthDTO(int lvlSize, int rowSize, int columnSize) {
        this.lvlSize = lvlSize;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.labyrinth = new int[lvlSize][rowSize][columnSize];
    }

    public void printLabyrinth() {
        System.out.println("labyrinth has " + lvlSize + " levels. Each level size " + rowSize + "x" + columnSize);
        System.out.println("1-Prince position, 2-Princes position, 3-wall, 0-free space");
        System.out.println("Labyrinth:");
        for (int i = 0; i < lvlSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                for (int k = 0; k < columnSize; k++) {
                    System.out.print(" " + labyrinth[i][j][k]);
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }

    public int getLvlSize() {
        return lvlSize;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int[][][] getLabyrinth() {
        return labyrinth;
    }

}
