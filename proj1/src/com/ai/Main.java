package com.ai;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[][] foo = new int[][] {
                new int[] { 1, 2, 3 },
                new int[] { 1, 2, 3, 4},
                new int[] { 2, 3}
        };
        System.out.println(foo.length); //2
        System.out.println(foo[0].length); //3
        System.out.println(foo[1].length); //4
        System.out.println(foo[2].length); //4
        PreProcessing getPositions = new PreProcessing();
        ArrayList<String> text = getPositions.ReadFile();
        System.out.println(text.get(0).length());
        ArrayList<Table> tables = getPositions.StringToTable(text);

    }
}
