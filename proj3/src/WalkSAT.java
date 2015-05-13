
import java.util.ArrayList;
import java.util.Random;

public class WalkSAT {

    Random random = new Random();

    public static void main(String[] args) {

        try {
            if (args.length < 3) {
                System.out.println("Usage: WalkSAT ClausesFile p max_flips");
            }
            WalkSAT walkSAT = new WalkSAT();
            ArrayList<Clause> clauses = LoadClauses.load(args[0]);

            float p = Float.parseFloat(args[1]);
            long max_flips = Long.parseLong(args[2]);
            System.out.println(walkSAT.walkSAT(clauses, p, max_flips).toString());

        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("P and MAX_FLIPS must be a valid number");
        } catch (NullPointerException e) {
            //e.printStackTrace();
            System.out.println("WalkSAT couldn't find a valid model" + "\n" + e.getMessage());
        }
    }

    public Model walkSAT(ArrayList<Clause> clauses, double p, long maxFlips) {

        Model model = randomAssignmentToSymbolsInClauses(clauses.get(0).getNumVars());
        long flip = 0;
        for (long i = 0; i < maxFlips || maxFlips < 0; i++) {

            if (model.satisfies(clauses)) {
                return model;
            }
            Clause clause = randomlySelectFalseClause(clauses, model);
            //System.out.println(clause.getSymbols().toString());
            if (random.nextDouble() < p) {
                model = model.flip(randomlySelectSymbolFromClause(clause));
                flip++;
                System.out.println(flip);
            } else {
                model = flipSymbolInClauseMaximizesNumberSatisfiedClauses(
                        clause, clauses, model);
                flip++;
                System.out.println(flip);
            }
        }

        /*System.out.println(model.determineValue(clauses.get(0)));
        System.out.println(model.toString());
        model.satisfies(clauses);
        model.flip(2);
        model.flip(1);
        model.flip(5);
        model.flip(4);
        System.out.println(model.toString());*/

        return null;
    }

    public Clause randomlySelectFalseClause(ArrayList<Clause> clauses, Model model) {
        ArrayList<Clause> falseOnes = new ArrayList<>();
        for (Clause c : clauses) {
            if (Boolean.FALSE.equals(model.determineValue(c))) ;
            falseOnes.add(c);
        }
        Clause result = falseOnes.get(random.nextInt(falseOnes.size()));
        return result;
    }

    public Model randomAssignmentToSymbolsInClauses(int numVal) {
        Model model = new Model();
        ArrayList<Integer> symbols = new ArrayList<>();
        ArrayList<Boolean> values = new ArrayList<>();

        for (int i = 1; i <= numVal; i++) {
            symbols.add(i);
            values.add(random.nextBoolean());
        }
        model.setSymbols(symbols);
        model.setValue(values);
        //System.out.println(model.toString());
        return model;
    }

    public int randomlySelectSymbolFromClause(Clause clause) {
        ArrayList<Integer> symbols = clause.getSymbols();
        int result = (new ArrayList<Integer>(symbols))
                .get(random.nextInt(symbols.size()));
        return result;
    }

    public Model flipSymbolInClauseMaximizesNumberSatisfiedClauses(Clause clause, ArrayList<Clause> clauses, Model model) {
        Model result = model;

        // all the symbols in clause
        ArrayList<Integer> symbols = clause.getSymbols();
        int maxClausesSatisfied = -1;
        for (int symbol : symbols) {
            Model flippedModel = result.flip(symbol);
            int numberClausesSatisfied = 0;
            for (Clause c : clauses) {
                if (Boolean.TRUE.equals(flippedModel.determineValue(c))) {
                    numberClausesSatisfied++;
                }
            }
            if (numberClausesSatisfied > maxClausesSatisfied) {
                result = flippedModel;
                maxClausesSatisfied = numberClausesSatisfied;
                if (numberClausesSatisfied == clauses.size()) {
                    break;
                }
            }
        }

        return result;
    }
}
