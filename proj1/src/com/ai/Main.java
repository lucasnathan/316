package com.ai;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*int[][] foo = new int[][] {
                new int[] { 1, 2, 3 },
                new int[] { 1, 2, 3, 4},
                new int[] { 2, 3}
        };
        System.out.println(foo.length); //2
        System.out.println(foo[0].length); //3
        System.out.println(foo[1].length); //4
        System.out.println(foo[2].length); //4*/
        int[][] result = new int[][] {
                new int[] { 0,1,2,3,4 },
                new int[] { 5,6,7,8,9 },
                new int[] { 10,11,12,13,14 },
                new int[] { 15,16,17,18,19 },
                new int[] { 20,21,22,23,24 }
        };
        PreProcessing getPositions = new PreProcessing();
        String text = getPositions.ReadFile(4);
        Table table2 = new Table(5,5,result);
        table2.setId(1);
        //System.out.println(table2.toString().hashCode());
        //System.out.print(text);
        //System.out.print(text);
        Table table = getPositions.StringToTable(text,1);

        //System.out.println(table.toString().hashCode());
        Map map = new TreeMap();
        /*table2.setTable(result);
        table.setTable(result);
        table.setId(2);
        table2.setId(2);
        map.put(1,table2);
        map.put(2,table);

        System.out.println(map.get(2)+""+table2.toString().hashCode());
        System.out.println(map.get(1)+""+table.toString().hashCode());*/

        SharedTable sharedTable = new SharedTable();
        Thread a = new Thread(new BidirectionalSearch(table2,table,sharedTable,0));
        Thread b =new Thread(new BidirectionalSearch(table,table2,sharedTable,1));
        a.start();
        b.start();
        //String n = (float)6/4+"";

        //System.out.println(n.substring(n.indexOf('.')+1));
    }
}
