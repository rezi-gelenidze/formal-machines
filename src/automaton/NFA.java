package automaton;

import automaton.utils.TransitionInput;

import java.util.*;

public class NFA {
    private Set<Integer> q; // Finite set of states
    private Set<String> sigma; // Input alphabet
    private Map<TransitionInput, Set<Integer>> delta; // Transition function (multiple possible transitions)
    private Integer q0; // Start state
    private Set<Integer> f; // Set of accepting states

    public NFA(Set<Integer> q, Set<String> sigma, Map<TransitionInput, Set<Integer>> delta, Integer q0, Set<Integer> f) {
        this.q = q;
        this.sigma = sigma;
        this.delta = delta;
        this.q0 = q0;
        this.f = f;
    }

    @Override
    public String toString() {
        return  "NFA (\n" +
                "Q = " + q + ",\n" +
                "Σ = " + sigma + ",\n" +
                "δ = " + delta + ",\n" +
                "q₀ = " + q0 + ",\n" +
                "F = " + f + "\n" +
                ")";
    }


    /*
     *   Run Automaton using given input w
     */
    public boolean run(String[] w) {
        // TODO
        return true;
    }


    /*
     * Minimizes NFA using table-filling algorithm
     */
    public void minimize() {
        // TODO
    }
}
