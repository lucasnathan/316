import java.util.ArrayList;

public class Clause {
    ArrayList<Integer> symbols;
    int numVars, numClauses;
    long randomSeed;

    public Clause() {
        symbols = new ArrayList<>();
        numClauses = 0;
        numVars = 0;
        randomSeed = 0;
    }

    public Clause(ArrayList<Integer> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Integer> symbols) {
        this.symbols = symbols;
    }

    public int getNumVars() {
        return numVars;
    }

    public void setNumVars(int numVars) {
        this.numVars = numVars;
    }

    public int getNumClauses() {
        return numClauses;
    }

    public void setNumClauses(int numClauses) {
        this.numClauses = numClauses;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public void addSymbol(int symbol) {
        this.symbols.add(symbol);
    }

    public ArrayList<Integer> getPositiveSymbols() {
        ArrayList<Integer> positives = new ArrayList<>();
        for (Integer symbol : this.symbols) {
            if (symbol > 0) {
                positives.add(symbol);
            }
        }
        return positives;
    }

    public ArrayList<Integer> getNegativeSymbols() {
        ArrayList<Integer> negatives = new ArrayList<>();
        for (Integer symbol : this.symbols) {
            if (symbol < 0) {
                negatives.add(symbol);
            }
        }
        return negatives;
    }
}
