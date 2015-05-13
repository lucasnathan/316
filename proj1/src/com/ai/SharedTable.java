package com.ai;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lucas on 16/03/15.
 */
public class SharedTable {


    private int ansA = 0, ansB = 0, finish=0;
    private ArrayList<String> pathA, pathB,comparisonListA = new ArrayList<>(),comparisonListB = new ArrayList<>();

    public SharedTable() {

    }

    public synchronized int Compare(int id, String table,int index) {

        if (id == 0) {
            if (ansA != 0) {
                return ansA;
            }
            for (String comparison : comparisonListB) {
                if (comparison.equals(table)) {
                    ansB = comparisonListB.indexOf(comparison);

                    return index;
                }
            }
            comparisonListA.add(table);

            return 0;
        }
        if (id == 1) {
            if (ansB != 0) {
                return ansB;
            }
            for (String comparison : comparisonListA) {
                if (comparison.equals(table)) {
                    ansB = comparisonListA.indexOf(comparison);

                    return index;
                }
            }
            comparisonListB.add(table);

            return 0;
        }
        return 0;
    }

    public synchronized int ReceivePath(int id,ArrayList<String> path){
        if (id==0){
            pathA = path;

            finish++;
        }
        if (id==1){

            pathB = path;
            finish++;
        }
        return finish;
    }
    public synchronized void MergePath(){
        ArrayList<String> newPath = new ArrayList<>();
        Collections.reverse(pathB);
        newPath.addAll(pathB);
        newPath.addAll(pathA);


        for (String path0: newPath){
            System.out.println(path0);
        }
    }
}
