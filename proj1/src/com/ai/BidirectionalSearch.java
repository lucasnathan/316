package com.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lucas on 18/03/15.
 */
public class BidirectionalSearch implements Runnable {// Display a message, preceded by
    final long startTime = System.currentTimeMillis();
    private Table table, result;
    private int id;
    private SharedTable sharedTable;
    private Tree tree = new Tree();
    private int nodesGenerated, depth;

    public BidirectionalSearch(Table result, Table table, SharedTable sharedTable, int id) {
        this.table = table;
        this.sharedTable = sharedTable;
        this.id = id;
        this.result = result;
    }

    @Override
    public void run() {

        int index = 1;
        tree.addNode(index, 0, table.toString());
        int currently = 1;
        int[] pos = table.findZero();
        index++;

        do {
            if (table.moveUp(pos[0], pos[1])) {
                tree.addNode(index, currently, table.toString());
                compare(index);
                table = PreProcessing.StringToTable((String) tree.getNodes().get(currently).getTable(), table.id);
                index++;
                pos = table.findZero();
            }
            if (table.moveLeft(pos[0], pos[1])) {
                tree.addNode(index, currently, table.toString());
                compare(index);
                table = PreProcessing.StringToTable((String) tree.getNodes().get(currently).getTable(), table.id);
                index++;
                pos = table.findZero();
            }
            if (table.moveDown(pos[0], pos[1])) {
                tree.addNode(index, currently, table.toString());
                compare(index);
                table = PreProcessing.StringToTable((String) tree.getNodes().get(currently).getTable(), table.id);
                index++;
                pos = table.findZero();
            }
            if (table.moveRight(pos[0], pos[1])) {
                tree.addNode(index, currently, table.toString());
                compare(index);
                table = PreProcessing.StringToTable((String) tree.getNodes().get(currently).getTable(), table.id);
                index++;
                pos = table.findZero();
            }
            currently++;
            table = PreProcessing.StringToTable((String) tree.getNodes().get(currently).getTable(), table.id);
            pos = table.findZero();

        } while (true);
    }

    private void compare(int index) {
        int a;
        if (table.toString().hashCode() == result.toString().hashCode()) {

            ArrayList<String> path = FindPath(index);
            System.out.println("Thread: "+id+" have generated: "+nodesGenerated+" length: "+depth);
            System.out.println("Thread "+id+" Completed the Task before it could find a middle path");
            for (String str:path){
                System.out.println("id: "+id+"\n"+str);
            }
            System.out.println("---------------------------------------------------------------------------------");
            try {
                final long endTime = System.currentTimeMillis();

                System.out.println("Total execution time: " + (endTime - startTime) );
                System.exit(0);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        a = sharedTable.Compare(id, table.toString(),index);
        if (a != 0) {
            ArrayList<String> path = FindPath(a);
            System.out.println("Thread: "+id+" have generated: "+nodesGenerated+" length: "+depth);
            while (a!=2) {
                a = sharedTable.ReceivePath(id,path);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (id==0){
                sharedTable.MergePath();

                try {
                    final long endTime = System.currentTimeMillis();

                    System.out.println("Total execution time: " + ((endTime - startTime)) );
                    System.exit(0);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }else {
                try {
                    Thread.sleep(2000);
                    System.exit(0);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        }

    }

    private ArrayList<String> FindPath(int index) {
        ArrayList<String> path = new ArrayList<>();
        HashMap<Integer, Node> nodes = tree.getNodes();
        Node node = nodes.get(index);
        depth=0;
        nodesGenerated=index;
        while (node!=null){
            path.add(node.getTable());
            node = node.getParent();
            depth++;
        }
        return path;
    }
}