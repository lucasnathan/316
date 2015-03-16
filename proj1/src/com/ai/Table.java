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

    public boolean moveUp(int x,int y){
        if(x > 0){
            int aux = table[x][y];
            table[x][y] = table[x-1][y];
            table[x-1][y]=aux;
            return true;
        }
        return false;
    }

    public boolean moveDown(int x,int y){
        if(x < table.length){
            int aux = table[x][y];
            table[x][y] = table[x+1][y];
            table[x+1][y]=aux;
            return true;
        }
        return false;
    }

    public boolean moveLeft(int x,int y){
        if(y > 0){
            int aux = table[x][y];
            table[x][y] = table[x][y-1];
            table[x][y-1]=aux;
            return true;
        }
        return false;
    }

    public boolean moveRight(int x,int y){
        if(y < table[0].length){
            int aux = table[x][y];
            table[x][y] = table[x][y+1];
            table[x][y+1]=aux;
            return true;
        }
        return false;
    }

    public boolean findZero(int[] pos){
        for(int x=0; x<table.length;x++){
            for (int y=0; y<table[0].length;y++){
                if (table[x][y]==0){
                    pos[0]=x;
                    pos[1]=y;
                    return true;
                }
            }
        }
        return false;
    }
}
