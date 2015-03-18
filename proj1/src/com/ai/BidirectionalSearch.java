package com.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lucas on 18/03/15.
 */
public class BidirectionalSearch implements Runnable {// Display a message, preceded by

    private Table table,result;
    private int n,id;
    private SharedTable sharedTable;
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }

    public BidirectionalSearch(Table result, Table table,SharedTable sharedTable,int id){
        this.table = table;
        this.sharedTable = sharedTable;
        this.id = id;
        this.result=result;
    }
    @Override
    public void run() {

        Map map = new TreeMap();
        int index=1;
        int currently=1;

        int[] pos = table.findZero();
        map.put(index,table.toString());
        System.out.println(table.toString().hashCode() + "\n" + map.get(1) + " -" + id + "--first-in" + index);
        index++;
        int[] a=new int[2];
        do{

            if (table.moveUp(pos[0],pos[1])){
                map.put(index,table.toString());

                System.out.println(table.toString().hashCode()+"\n"+map.get(index)+" -"+id+"--up-in"+index);
                if (table.toString().hashCode()==result.toString().hashCode()){
                    System.out.println("eee carai "+index);
                    System.exit(0);
                }
                do {
                    a = sharedTable.Compare(id, table.toString().hashCode());
                    if (a[0]==1){
                        System.out.println("eee carai "+index);
                        System.exit(0);
                    }else if (a[1]==1){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(id+" "+index+"-----false");
                    }
                }while (a[1]==1);
                table = PreProcessing.StringToTable((String) map.get(currently), table.id);
                //System.out.println(table.toString().hashCode()+"\n"+map.get(1)+" ---up-in"+index);
                pos=table.findZero();

                index++;
            }if (table.moveLeft(pos[0], pos[1])){
                map.put(index,table.toString());
                //pos[1]=pos[1]-1;
                System.out.println(table.toString().hashCode()+"\n"+map.get(index)+" -" + id + "--left-in"+index);
                if (table.toString().hashCode()==result.toString().hashCode()){
                    System.out.println("eee carai "+index);
                    System.exit(0);
                }
                do {
                    a = sharedTable.Compare(id, table.toString().hashCode());
                    if (a[0]==1){
                        System.out.println("eee carai "+index);
                        System.exit(0);
                    }else if (a[1]==1){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(id+" "+index+"-----false");
                    }
                }while (a[1]==1);
                table = PreProcessing.StringToTable((String) map.get(currently), table.id);
                //table = (Table) map.get(currently);
                pos=table.findZero();
                index++;
            }if (table.moveDown(pos[0], pos[1])){
                map.put(index,table.toString());

                System.out.println(table.toString().hashCode()+"\n"+map.get(index)+" -" + id + "--down-in"+index);
                if (table.toString().hashCode()==result.toString().hashCode()){
                    System.out.println("eee carai "+index);
                    System.exit(0);
                }
                do {
                    a = sharedTable.Compare(id, table.toString().hashCode());
                    if (a[0]==1){
                        System.out.println("eee carai "+index);
                        System.exit(0);
                    }else if (a[1]==1){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(id+" "+index+"-----false");
                    }
                }while (a[1]==1);
                table = PreProcessing.StringToTable((String) map.get(currently), table.id);
                //table = (Table) map.get(currently);
                pos=table.findZero();
                index++;
                System.out.println(pos[0]+" "+pos[1]);
            }if (table.moveRight(pos[0], pos[1])) {
                map.put(index,table.toString());

                System.out.println(table.toString().hashCode()+"\n"+map.get(index)+" -" + id + "--rigth-in"+index);
                if (table.toString().hashCode()==result.toString().hashCode()){
                    System.out.println("eee carai "+index);
                    System.exit(0);
                }
                do {
                    a = sharedTable.Compare(id, table.toString().hashCode());
                    if (a[0]==1){
                        System.out.println("eee carai "+index);
                        System.exit(0);
                    }else if (a[1]==1){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(id+" "+index+"-----false");
                    }
                }while (a[1]==1);
                table = PreProcessing.StringToTable((String) map.get(currently), table.id);
                pos =table.findZero();
                //table = (Table) map.get(currently);
                index++;
            }
            currently++;
            table = PreProcessing.StringToTable((String) map.get(currently), table.id);
            //table = (Table) map.get(currently);
            pos=table.findZero();
        }while(true);
        /*String hash1 = "2", hash2 = n+"";
        hash1 = String.valueOf(hash1.hashCode());
        hash2 = String.valueOf(hash2.hashCode());
        int j=0;
        int a[] = new int[2];
        do {
            a=sharedTable.Compare(id,Integer.parseInt(hash2));
            j++;
            if (a[0]==1){
                System.out.println("1-----true");
            }else {
                System.out.println("1-----false");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (a[1]==1 && j!=10);

        int i=0;
        int b[] = new int[2];
        do {
            b=sharedTable.Compare(id,Integer.parseInt(hash1));
            i++;
            if (b[0]==1){
                System.out.println("1-----true");
            }else {
                System.out.println("1-----false");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (b[1]==1 && i<10);
*/

    }

    /*private static class MessageLoop
            implements Runnable {
        public void run(Table table) {
            String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
            };
            try {
                for (int i = 0;
                     i < importantInfo.length;
                     i++) {
                    // Pause for 4 seconds
                    Thread.sleep(4000);
                    // Print a message
                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }*/
/*
    public static void main(String args[])
            throws InterruptedException {

        // Delay, in milliseconds before
        // we interrupt MessageLoop
        // thread (default one hour).
        long patience = 1000 * 60 * 60;

        // If command line argument
        // present, gives patience
        // in seconds.
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");
        // loop until MessageLoop
        // thread exits
        while (t.isAlive()) {
            threadMessage("Still waiting...");
            // Wait maximum of 1 second
            // for MessageLoop thread
            // to finish.
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > patience)
                    && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // Shouldn't be long now
                // -- wait indefinitely
                t.join();
            }
        }
        threadMessage("Finally!");
    }*/
    private int parent(int index){
        float calc=(float)index/4;
        String n = calc+"";
        n.substring(n.indexOf('.')+1);
        switch (n){
            case "25":
                calc=calc-0.25f;
                break;
            case "0":
                break;
            case "75":
                calc=calc+0.25f;
                break;
            case "5":
                calc=calc+0.5f;
                break;
            default:
                System.out.println("==================Something unpredictable had Happened===========");
        }
        index = (int) calc;
        return index;
    }
}


/*
float calc=(float)index/4;
String n = calc+"";
n.substring(n.indexOf('.')+1);
        switch (n){
        case "25":
        calc=calc-0.25f;
        break;
        case "0":
        break;
        case "75":
        calc=calc+0.25f;
        break;
        case "5":
        calc=calc+0.5f;
        break;
default:
        System.out.println("==================Something unpredictable had Happened===========");
        }


        if (table.moveUp(pos[0],pos[1])){
                map.put(index,table);
                pos[0]=pos[0]-1;
                System.out.println(table.toString().hashCode()+"\n"+map.get(index)+" ---up-in"+index);
                if (table.toString().hashCode()==result.toString().hashCode()){
                    System.out.println("eee carai "+index);
                    System.exit(0);
                }
                index++;
            }else {
                if (table.moveLeft(pos[0], pos[1])) {
                    map.put(index, table);
                    pos[1] = pos[1] - 1;
                    System.out.println(table.toString().hashCode() + "\n" + map.get(index) + " ---left-in" + index);
                    if (table.toString().hashCode() == result.toString().hashCode()) {
                        System.out.println("eee carai " + index);
                        System.exit(0);
                    }
                    index++;
                } else {
                    if (table.moveDown(pos[0], pos[1])) {
                        map.put(index, table);
                        pos[0] = pos[0] + 1;
                        System.out.println(table.toString().hashCode() + "\n" + map.get(index) + " ---down-in" + index);
                        if (table.toString().hashCode() == result.toString().hashCode()) {
                            System.out.println("eee carai " + index);
                            System.exit(0);
                        }
                        index++;
                    } else {
                        if (table.moveRight(pos[0], pos[1])) {
                            map.put(index, table);
                            pos[1] = pos[1] + 1;
                            System.out.println(table.toString().hashCode() + "\n" + map.get(index) + " ---rigth-in" + index);
                            if (table.toString().hashCode() == result.toString().hashCode()) {
                                System.out.println("eee carai " + index);
                                System.exit(0);
                            }
                            index++;
                        }
                    }
                }
            }*/
