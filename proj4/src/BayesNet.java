import java.util.ArrayList;
import java.util.Random;

/**
 * Simple class for approximate inference based on the Poker-game network.
 */
public class BayesNet {

    /**
     * Inner class for representing a node in the network.
     */
    private class Node {

        // The name of the node
        private String name;

        // The parent nodes
        private Node[] parents;

        // The probabilities for the CPT
        private double[] probs;

        // The current value of the node
        public boolean value;

        /**
         * Initializes the node.
         */
        private Node(String n, Node[] pa, double[] pr) {
            name = n;
            parents = pa;
            probs = pr;
        }

        /**
         * Returns conditional probability of value "true" for the current node
         * based on the values of the parent nodes.
         *
         * @return The conditional probability of this node, given its parents.
         */
        private double conditionalProbability() {

            int index = 0;
            for (int i = 0; i < parents.length; i++) {
                if (parents[i].value == false) {
                    index += Math.pow(2, parents.length - i - 1);
                }
            }
            return probs[index];
        }


    }

    // The list of nodes in the Bayes net
    private Node[] nodes;

    // A collection of examples describing whether Bot B is { cocky, bluffing
    // }
    public static final boolean[][] BBLUFF_EXAMPLES = { { true, true },
            { true, true }, { true, true }, { true, false }, { true, true },
            { false, false }, { false, false }, { false, true },
            { false, false }, { false, false }, { false, false },
            { false, false }, { false, true } };

    /**
     * Constructor that sets up the Poker-game network.
     */
    public BayesNet() {

        nodes = new Node[7];

        nodes[0] = new Node("B.Cocky", new Node[] {}, new double[] { 0.05 });
        nodes[1] = new Node("B.Bluff", new Node[] { nodes[0] },
                calculateBBluffProbabilities(BBLUFF_EXAMPLES));
        nodes[2] = new Node("A.Deals", new Node[] {},
                new double[] { 0.5 });
        nodes[3] = new Node("A.GoodHand", new Node[] { nodes[2] },
                new double[] { 0.75, 0.5 });
        nodes[4] = new Node("B.GoodHand", new Node[] { nodes[2] },
                new double[] { 0.4, 0.5 });
        nodes[5] = new Node("B.Bets", new Node[] { nodes[1], nodes[4] },
                new double[] { 0.95, 0.7, 0.9, 0.01 });
        nodes[6] = new Node("A.Wins", new Node[] { nodes[3], nodes[4] },
                new double[] { 0.45, 0.75, 0.25, 0.55 });
    }

    /**
     * Prints the current state of the network to standard out.
     */
    public void printState() {

        for (int i = 0; i < nodes.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(nodes[i].name + " = " + nodes[i].value);
        }
        System.out.println();
    }

    /**
     * Calculates the probability that Bot B will bluff based on whether it is
     * cocky or not.
     *
     * @param bluffInstances
     *            A set of training examples in the form { cocky, bluff } from
     *            which to compute the probabilities.
     * @return The probability that Bot B will bluff when it is { cocky, !cocky
     *         }.
     */
    public double[] calculateBBluffProbabilities(boolean[][] bluffInstances) {
        double[] probabilities = new double[2];

        // YOUR CODE HERE
        //System.out.println(bluffInstances[4][1]);
        int cCocky = 0;
        int cBluff=0;
        int cockyBluff = 0;
        int nCocky=0;
        for (int i=0;i<bluffInstances.length;i++){
            if (bluffInstances[i][0]==true){
                    cCocky++;
                if (bluffInstances[i][1]==true) {
                    cockyBluff++;
                }
            }else {
                nCocky++;
                if (bluffInstances[i][1] == true) {
                    cBluff++;
                }
            }
        }

        probabilities[0]= (float) cockyBluff / cCocky;
        probabilities[1]=(float) cBluff / nCocky;
        /*System.out.println(nCocky);
        System.out.println(cCocky);*/
        return probabilities;
    }

    /**
     * This method calculates the exact probability of a given event occurring,
     * where all variables are assigned a given evidence value.
     *
     * @param evidenceValues
     *            The values of all nodes.
     * @return -1 if the evidence does not cover every node in the network.
     *         Otherwise a probability between 0 and 1.
     */
    public double calculateExactEventProbability(boolean[] evidenceValues) {
        // Only performs exact calculation for all evidence known.
        if (evidenceValues.length != nodes.length)
            return -1;
        double probability = 1;
        for(int i=0;i<nodes.length;i++){
            if (evidenceValues[i]==true){
                probability*=nodes[i].conditionalProbability();
            }else {
                double fals = -nodes[i].conditionalProbability()+1;
                probability*=fals;
            }
        }
        return probability;
    }

    /**
     * This method assigns new values to the nodes in the network by sampling
     * from the joint distribution (based on PRIOR-SAMPLE method from text
     * book/slides).
     */
    public void priorSample() {
        Random r = new Random();
        for (int i=0;i<nodes.length;i++){
            if (r.nextDouble()<nodes[i].conditionalProbability()){
                nodes[i].value=true;
            }else nodes[i].value=false;
        }
        // YOUR CODE HERE
    }

    /**
     * Rejection sampling. Returns probability of query variable being true
     * given the values of the evidence variables, estimated based on the given
     * total number of samples (see REJECTION-SAMPLING method from text
     * book/slides).
     *
     * The nodes/variables are specified by their indices in the nodes array.
     * The array evidenceValues has one value for each index in
     * indicesOfEvidenceNodes. See also examples in main().
     *
     * @param queryNode
     *            The variable for which rejection sampling is calculating.
     * @param indicesOfEvidenceNodes
     *            The indices of the evidence nodes.
     * @param evidenceValues
     *            The values of the indexed evidence nodes.
     * @param N
     *            The number of iterations to perform rejection sampling.
     * @return The probability that the query variable is true given the
     *         evidence.
     */

    public double rejectionSampling(int queryNode,
                                    int[] indicesOfEvidenceNodes, boolean[] evidenceValues, int N) {

        int evidenceTrue = 0,queryTrue=0;

        for (int i=0;i<N;i++){
            priorSample();
            int consistent =0;
                for (int j = 0; j < indicesOfEvidenceNodes.length; j++) {
                    //System.out.println(evidenceValues[j]+" "+nodes[j].value+" "+j);
                    if (evidenceValues[j] == nodes[indicesOfEvidenceNodes[j]].value) {
                        consistent++;
                    }
                }
                if (consistent == indicesOfEvidenceNodes.length) {
                    evidenceTrue++;
                    if (nodes[queryNode].value){
                        queryTrue++;
                    }
                }
        }
        return (float) queryTrue / evidenceTrue; // REPLACE THIS LINE BY YOUR CODE
    }

    /**
     * This method assigns new values to the non-evidence nodes in the network
     * and computes a weight based on the evidence nodes (based on
     * WEIGHTED-SAMPLE method from text book/slides).
     *
     * The evidence is specified as in the case of rejectionSampling().
     *
     * @param indicesOfEvidenceNodes
     *            The indices of the evidence nodes.
     * @param evidenceValues
     *            The values of the indexed evidence nodes.
     * @return The weight of the event occurring.
     *
     */
    public double weightedSample(int[] indicesOfEvidenceNodes,
                                 boolean[] evidenceValues) {
        double weight=1;
        for (int i=0;i<evidenceValues.length;i++){
            if (nodes[indicesOfEvidenceNodes[i]].value==evidenceValues[i]){
                weight *= nodes[i].conditionalProbability();
            }
        }
        return weight; // REPLACE THIS LINE BY YOUR CODE
    }

    /**
     * Likelihood weighting. Returns probability of query variable being true
     * given the values of the evidence variables, estimated based on the given
     * total number of samples (see LIKELIHOOD-WEIGHTING method from text
     * book/slides).
     *
     * The parameters are the same as in the case of rejectionSampling().
     *
     * @param queryNode
     *            The variable for which rejection sampling is calculating.
     * @param indicesOfEvidenceNodes
     *            The indices of the evidence nodes.
     * @param evidenceValues
     *            The values of the indexed evidence nodes.
     * @param N
     *            The number of iterations to perform rejection sampling.
     * @return The probability that the query variable is true given the
     *         evidence.
     */
    public double likelihoodWeighting(int queryNode,
                                      int[] indicesOfEvidenceNodes, boolean[] evidenceValues, int N) {

        for (int i=0;i<N;i++){

        }
        return 0; // REPLACE THIS LINE BY YOUR CODE
    }

    /**
     * MCMC inference. Returns probability of query variable being true given
     * the values of the evidence variables, estimated based on the given total
     * number of samples (see MCMC-ASK method from text book/slides).
     *
     * The parameters are the same as in the case of rejectionSampling().
     *
     * @param queryNode
     *            The variable for which rejection sampling is calculating.
     * @param indicesOfEvidenceNodes
     *            The indices of the evidence nodes.
     * @param evidenceValues
     *            The values of the indexed evidence nodes.
     * @param N
     *            The number of iterations to perform rejection sampling.
     * @return The probability that the query variable is true given the
     *         evidence.
     */
    public double MCMCask(int queryNode, int[] indicesOfEvidenceNodes,
                          boolean[] evidenceValues, int N) {

        int[] mB = markovBlanket(indexOf(nodes[queryNode]));

        //assign random values for elements on the Markov Blanket of queryNode.
        for (int k=0;k<mB.length;k++){
            Random random = new Random();
            for (int i=0;i<indicesOfEvidenceNodes.length;i++) {
                if (!(nodes[mB[k]].equals(nodes[indicesOfEvidenceNodes[i]]))) {
                    if (random.nextDouble() < mcmcProbability(nodes[mB[k]])) {
                        nodes[mB[k]].value = true;
                    } else nodes[mB[k]].value = false;
                }else
                    nodes[k].value=evidenceValues[i];
            }
        }
        int state = 0,queryTrue=0;
        //testing for the first state
        state++;
        if (nodes[queryNode].value==true){
            queryTrue++;
        }
        for (int i=0;i<N;i++){

            //change values on the querynode Markov blanket according to their own Markov Blankets
            for (int j=0;j<mB.length;j++){
                for (int k = 0;k<indicesOfEvidenceNodes.length;k++) {
                    if (!(nodes[mB[j]].equals(nodes[indicesOfEvidenceNodes[k]]))) {
                        Random random = new Random();
                        if (random.nextDouble() < mcmcProbability(nodes[mB[j]])) {
                            nodes[mB[j]].value = true;
                        } else nodes[mB[j]].value = false;
                        if (random.nextDouble() < mcmcProbability(nodes[queryNode])) {
                            nodes[queryNode].value = true;
                        } else nodes[queryNode].value = false;

                        //testing for the new state
                        state++;
                        if (nodes[queryNode].value == true) {
                            queryTrue++;
                        }
                    }
                }
            }
        }
        return (float) queryTrue / state; // REPLACE THIS LINE BY YOUR CODE
    }

    /**
     * Returns conditional probability of value "true" for the current node
     * based on the values of its Markov blanket.
     *
     * @return The conditional probability of this node, given its Markov blanket.
     */
    private double mcmcProbability(Node node) {

        double prob = 1;
        int nodeIndex = indexOf(node);
        if (nodeIndex<0){
            return -1;
        }
        int[] markovBlanket = markovBlanket(nodeIndex);
        for (int i=0;i<markovBlanket.length;i++){
            prob*=nodes[i].conditionalProbability();
        }
        return prob;
    }
    /**
     * Calculate the Markov blanket for the current node
     *
     * @param node
     *            The variable for which Markov Blanket is been calculated.
     *
     * @return the Markov blanket for a given node.
     */
    private int[] markovBlanket(int node) {

        ArrayList<Node> mB = new ArrayList<Node>();
        //search for parents.
        for (int i = 0; i < nodes[node].parents.length; i++) {
            Node parent =nodes[node].parents[i];
            mB.add(parent);
        }
        //search for its own children
        for (int j=0;j<nodes.length;j++){
            if (!(nodes[node].equals(nodes[j]))){
                for (int k=0;k<nodes[j].parents.length;k++){
                    if (nodes[j].parents[k].equals(nodes[node])){
                        Node children = nodes[j];
                        mB.add(children);
                        //Add children's parents
                        for (int l=0;l<children.parents.length;l++){
                            if (!(nodes[node].equals(children.parents[l]))) {
                                mB.add(children.parents[l]);
                            }
                        }
                    }
                }
            }
        }
        int markovInt[] = new int[mB.size()];
        for (int i =0;i<mB.size();i++){
            markovInt[i]=indexOf(mB.get(i));
        }
        return markovInt;
    }
    /**
     * find the index on a array of Nodes
     *
     * @return the the index of a given Node on a array of nodes
     *
     * @param node
     *            The variable you search on the array.
     */
    public int indexOf(Node node){
        for (int i=0;i<nodes.length;i++){
            if (nodes[i].name.equals(node.name)){
                return i;
            }
        }
        return -1;
    }
    /**
     * The main method, with some example method calls.
     */
    public static void main(String[] ops) {

        // Create network.
        BayesNet b = new BayesNet();

        double[] bluffProbabilities = b
                .calculateBBluffProbabilities(BBLUFF_EXAMPLES);
        System.out.println("When Bot B is cocky, it bluffs "
                + (bluffProbabilities[0] * 100) + "% of the time.");
        System.out.println("When Bot B is not cocky, it bluffs "
                + (bluffProbabilities[1] * 100) + "% of the time.");

        System.out.println();
        double bluffWinProb = b.calculateExactEventProbability(new boolean[] {
                true, true, true, false, false, true, false });
        System.out
                .println("The probability of Bot B winning on a cocky bluff "
                        + "(with bet) and both bots have bad hands (A dealt) is: "
                        + bluffWinProb);
        System.out.println();
        // Sample five states from joint distribution and print them
        for (int i = 0; i < 5; i++) {
            b.priorSample();
            b.printState();
        }

        // Print out results of some example queries based on rejection
        // sampling.
        // Same should be possible with likelihood weighting and MCMC inference.

        System.out.println();
        // Probability of B.GoodHand given bet and A not win.
        System.out.println(b.rejectionSampling(4, new int[] { 5, 6 },
                new boolean[] { true, false }, 10000));

        // Probability of betting given a cocky
        System.out.println(b.rejectionSampling(1, new int[] { 0 },
                new boolean[] { true }, 10000));

        //Probability of B.Goodhand given B.Bluff and A.Deal
        System.out.println(b.rejectionSampling(4, new int[] { 1, 2 },
                new boolean[] { true, true }, 10000));
        System.out.println();

       /* // Probability of B.GoodHand given bet and A not win.
        System.out.println(b.weightedSample(new int[]{5, 6},
                new boolean[]{true, false}));*/

        System.out.println();
        // Probability of B.GoodHand given bet and A not win.
        System.out.println(b.likelihoodWeighting(4, new int[]{5, 6},
                new boolean[]{true, false}, 10000));

        // Test for Markov Blanket code
        int[] markov = b.markovBlanket(3);
        for (int i=0;i<markov.length;i++){
            System.out.println(markov[i]);
        }

        System.out.println();
        // Probability of B.GoodHand given bet and A not win.
        System.out.println(b.MCMCask(4, new int[]{5, 6},
                new boolean[]{true, false}, 10000));
    }
}
