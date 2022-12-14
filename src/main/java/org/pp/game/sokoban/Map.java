package org.pp.game.sokoban;

public class Map {
    private int manX = 0;
    private int manY = 0;
    private final byte[][] map;
    private int grade;

    public Map(int manX, int manY, byte[][] map) {
        this.manX = manX;
        this.manY = manY;
        int row = map.length;
        int column = map[0].length;
        byte[][] temp = new byte[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                temp[i][j] = map[i][j];
            }
        }
        this.map = temp;
    }

    public Map(int manX, int manY, byte[][] map, int grade) {
        this(manX, manY, map);
        this.grade = grade;
    }

    public int getManX() {
        return manX;
    }

    public int getManY() {
        return manY;
    }

    public byte[][] getMap() {
        return map;
    }

    public int getGrade() {
        return grade;
    }
}



















