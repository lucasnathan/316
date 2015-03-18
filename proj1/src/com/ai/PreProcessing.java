package com.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lucas on 16/03/15.
 */
public class PreProcessing {
    public String ReadFile(int i) {
        String text = new String();
        ArrayList<String> texts = new ArrayList<String>();
        //for (int i = 0; i < Text.length; i++) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("positions.txt"))) {
            String currentLine = new String();

            int max = i * 8;
            i = max - 7;
            int count = 1;
            while (count < max && (currentLine = bufferedReader.readLine()) != null) {
                if (count < i || currentLine.contains("board") || count == i + 1) {
                    //System.out.println(count + "--------1--\n");
                    count++;
                    //currentLine = bufferedReader.readLine();
                } else {

                    count++;
                    text += currentLine + "\n";
                    //currentLine = bufferedReader.readLine();
                    texts.add(text);
                    //text = "";


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
        return text;
    }
    public static Table StringToTable(String text,int id){

            String[] lines = text.split(System.getProperty("line.separator"));
            int[][] puzzle= new int[lines.length][lines.length];
            for (int l=0; l<lines.length;l++){
                String[] positions = lines[l].split(" ");
                for (int p=0;p<positions.length;p++){
                    puzzle[l][p] = Integer.parseInt(positions[p]);
                }
            }
            Table table = new Table(lines.length,lines.length,puzzle);
            table.setId(id);
            //System.out.println(table.toString());
        return table;
    }

}
