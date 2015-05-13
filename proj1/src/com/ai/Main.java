package com.ai;

/**
 * Created by lucas on 15/03/15.
 */

public class Main {

    public static void main(String[] args) {

        int[][] result = new int[][]{
                new int[]{0, 1, 2, 3, 4},
                new int[]{5, 6, 7, 8, 9},
                new int[]{10, 11, 12, 13, 14},
                new int[]{15, 16, 17, 18, 19},
                new int[]{20, 21, 22, 23, 24}
        };
        int i = 24;
        Table tableResult = new Table(5, 5, result);
        tableResult.setId(i);

        PreProcessing getPositions = new PreProcessing();
        String text = getPositions.ReadFile(i);
        Table table = getPositions.StringToTable(text, i);

        SharedTable sharedTable = new SharedTable();

        Thread a = new Thread(new BidirectionalSearch(tableResult, table, sharedTable, 0));
        Thread b = new Thread(new BidirectionalSearch(table, tableResult, sharedTable, 1));
        a.start();
        b.start();


    }
}
