package com.ai;

/**
 * Created by lucas on 15/03/15.
 */
public class Table {
    int id;
    int[][] table;
    //produce a empty table and initiate it with a array value
    public void Table(int x,int y,int[][] array) {
        table = new int[x][y];
        this.setTable(array);
    }
    //produce a empty table
    public void Table(int x,int y) {
        table = new int[x][y];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }
}
