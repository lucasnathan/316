import java.util.ArrayList;

public class Model {
    ArrayList<Integer> symbols;
    ArrayList<Boolean> value;

    public ArrayList<Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Integer> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Boolean> getValue() {
        return value;
    }

    public void setValue(ArrayList<Boolean> value) {
        this.value = value;
    }


    public String toString() {
        String s = new String();
        for (int i = 0; i < symbols.size(); i++) {
            s += symbols.get(i) + " " + value.get(i).toString() + "\n";
        }
        return s;
    }

    public boolean satisfies(ArrayList<Clause> clauses) {
        for (Clause clause : clauses) {
            if (!Boolean.TRUE.equals(determineValue(clause))) {
                return false;
            }
        }
        return true;
    }

    public boolean determineValue(Clause clause) {
        Boolean result = null;
        Boolean value;

        for (Integer positive : clause.getPositiveSymbols()) {

            value = this.getValue().get(Math.abs(positive - 1));

            if (Boolean.TRUE.equals(value)) {

                result = true;
                break;
            }
        }

        if (result == null) {
            for (Integer negative : clause.getNegativeSymbols()) {
                value = this.getValue().get(Math.abs(negative + 1));

                if (Boolean.FALSE.equals(value)) {
                    result = true;
                    break;
                }
            }
        }
        if (result == null) {
            return false;
        }
        return result;
    }

    public Model flip(int symbol) {
        int sym = Math.abs(symbol);
        Boolean value = this.getValue().get(sym - 1);
        if (Boolean.TRUE.equals(value)) {
            this.getValue().set(sym - 1, false);
        } else {
            this.getValue().set(sym - 1, true);
        }
        return this;
    }
}
