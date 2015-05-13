import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadClauses {

    public static void main(String[] args) {
        load(args[0]);
    }

    public static ArrayList<Clause> load(String fileName) {
        ArrayList<Clause> clauses = new ArrayList<Clause>();

        String[] conf = new String[4];
        String[] numClauses = new String[2];
        String[] randomSeed = new String[2];
        String[] numVars = new String[2];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String CurrentLine = br.readLine();
            conf = CurrentLine.split("=");
            numVars = conf[1].split(" ");
            numClauses = conf[2].split(" ");
            randomSeed = conf[3].split(" ");
            while ((CurrentLine = br.readLine()) != null) {
                String[] clause = CurrentLine.split(" ");
                ArrayList<Integer> symbols = new ArrayList<Integer>();

                for (int i = 0; i < clause.length; i++) {
                    symbols.add(Integer.parseInt(clause[i]));
                }
                Clause temp = new Clause(symbols);
                temp.setNumClauses(Integer.parseInt(numClauses[1]));
                temp.setRandomSeed(Long.parseLong(randomSeed[1]));
                temp.setNumVars(Integer.parseInt(numVars[1]));
                clauses.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*for (Clause s : clauses) {
            System.out.println(s.getSymbols().toString());

        }*/
        return clauses;
    }


}
