package com.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lucas on 16/03/15.
 */
public class PreProcessing {
    public ArrayList<String> ReadFile() {
        String text = new String();
        ArrayList<String> texts = new ArrayList<String>();
        //for (int i = 0; i < Text.length; i++) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader ("positions.txt"))) {
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (currentLine.contains("board")){
                        currentLine = bufferedReader.readLine();
                    }else {

                        text += currentLine + "\n";
                        currentLine = bufferedReader.readLine();
                        text += currentLine + "\n";
                        currentLine = bufferedReader.readLine();
                        text += currentLine + "\n";
                        currentLine = bufferedReader.readLine();
                        text += currentLine + "\n";
                        currentLine = bufferedReader.readLine();
                        text += currentLine + "\n";
                        currentLine = bufferedReader.readLine();

                        texts.add(text);
                        text = "";

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
        return texts;
    }
    public ArrayList<Table> StringToTable(ArrayList<String> text){
        ArrayList<Table> tables = new ArrayList<Table>();

        for (int i=0;i<text.size(); i++){

            String[] lines = text.get(i).split(System.getProperty("line.separator"));
            int[][] puzzle= new int[lines.length][lines.length];
            for (int l=0; l<lines.length;l++){
                String[] positions = lines[l].split(" ");
                for (int p=0;p<positions.length;p++){
                    puzzle[l][p] = Integer.parseInt(positions[p]);
                }
            }
            Table table = new Table(lines.length,lines.length,puzzle);
            table.setId(i+1);
            tables.add(table);
        }
        for (Table tab: tables){
            System.out.println(tab.getId());
            System.out.println(tab.toString());
            System.out.println();
        }
        return tables;
    }

}
