package automaton;

import automaton.utils.TransitionInput;

import java.util.*;
import java.util.stream.Collectors;

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


    /*
     * Convert DFA to NFA just by creating a singleton (one entry set) of transition from every dfa delta transitions.
     */
    public static NFA fromDFA(DFA dfa) {
        // Construct a new delta of singleton transitions (Q_n, w_i) -> Q_m into (Q_n, w_i) -> {Q_M}
        Map<TransitionInput, Set<Integer>> singletonDelta = dfa.getDelta().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Set.of(e.getValue())));

        return new NFA(
                dfa.getQ(),
                dfa.getSigma(),
                singletonDelta,
                dfa.getQ0(),
                dfa.getF()
        );
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

    public Set<Integer> getQ() {
        return q;
    }

    public Set<String> getSigma() {
        return sigma;
    }

    public Map<TransitionInput, Set<Integer>> getDelta() {
        return delta;
    }

    public Integer getQ0() {
        return q0;
    }

    public Set<Integer> getF() {
        return f;
    }
}
