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


    private Set<Integer> performEpsilonTransitions(Set<Integer> q) {
        // Set of explored nodes (initialized with current states)
        Set<Integer> explored = new HashSet<>(q);

        // Create DFS Stack and add all current states for depth exploration
        Stack<Integer> stack = new Stack<>();
        q.forEach(stack::push);

        while (!stack.isEmpty()) {
            Integer current = stack.pop();

            // Get all epsilon transitions from stack current head
            Set<Integer> epsilonTransitions = this.delta.get(new TransitionInput(current, null));

            if (epsilonTransitions != null) {
                for (Integer transition : epsilonTransitions) {
                    if (!explored.contains(transition)) {
                        explored.add(transition);
                        stack.push(transition);
                    }
                }
            }
        }

        return explored;
    }

    /*
     *   Run Automaton using given input w
     */
    public boolean run(String[] w) {
        Set<Integer> currentStates = performEpsilonTransitions(Set.of(this.q0));

        for (String s : w) {
            if (!this.sigma.contains(s))
                throw new IllegalArgumentException("No such element in alphabet: " + s);

            Set<Integer> nextStates = new HashSet<>();

            currentStates.forEach(state -> {
                Set<Integer> possibleTransitions = this.delta.get(new TransitionInput(state, s));

                // If there are possible transitions (not stuck state) then append to new states
                if (possibleTransitions != null) {
                    nextStates.addAll(possibleTransitions);
                }
            });

            // Replace the states (new sets to explore)
            currentStates = performEpsilonTransitions(nextStates);
        }

        // NFA accepts an input if any of the final states are accepting
        return currentStates.stream().anyMatch(f::contains);
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
        return "NFA (\n" +
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

    public static void main(String[] args) {
        // Build a demo dfa accepting even number of binary characters or epsilon: (00 | 01 | 10 | 11)*
        Map<TransitionInput, Set<Integer>> delta = Map.of(
                new TransitionInput(0, null), Set.of(1, 3),
                new TransitionInput(1, "0"), Set.of(2),
                new TransitionInput(2, "1"), Set.of(2),
                new TransitionInput(3, "1"), Set.of(3)
        );

        NFA automaton = new NFA(
                Set.of(0, 1, 2, 3),
                Set.of("0", "1"),
                delta,
                0,
                Set.of(2, 3)
        );

        // Print automaton structure
        System.out.println(automaton);


        String[][] tests = {
                {"0"},                  // Accept (q1 -> 0 -> q2)
                {"0", "1"},             // Accept
                {"0", "1", "1"},        // Accept
                {"1"},                  // Accept (q3)
                {"1", "1", "1"},        // Accept (q3 looping)
                {"1", "0"},             // Reject
                {},                     // Reject
        };

        for (String[] w : tests)
            System.out.println("Input: " + Arrays.toString(w) + " -> " + automaton.run(w));
    }
}
