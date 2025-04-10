package automaton;

import automaton.utils.TransitionInput;

import java.util.*;

public class DFA {
    private Set<Integer> q; // Finite set of states
    private Set<String> sigma; // Input alphabet
    private Map<TransitionInput, Integer> delta; // Transition function
    private Integer q0; // Start state
    private Set<Integer> f; // Set of accepting states


    public DFA(Set<Integer> q, Set<String> sigma, Map<TransitionInput, Integer> delta, Integer q0, Set<Integer> f) {
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
        int state = this.q0;
        for (String s : w) {
            // Assert that current input is valid (present in alphabet)
            if (!sigma.contains(s))
                throw new IllegalArgumentException("No such word in the alphabet: " + s);

            TransitionInput transitionInput = new TransitionInput(state, s);

            // Assert that given transition (State, Input) exists in transition function (map)
            if (!delta.containsKey(transitionInput))
                throw new IllegalArgumentException("No such transition: " + s);

            // Perform Transition
            state = this.delta.get(transitionInput);
        }

        // Check if final state is accepting one.
        return f.contains(state);
    }

    @Override
    public String toString() {
        return  "DFA (\n" +
                "Q = " + q + ",\n" +
                "Σ = " + sigma + ",\n" +
                "δ = " + delta + ",\n" +
                "q₀ = " + q0 + ",\n" +
                "F = " + f + "\n" +
                ")";
    }


    public static void main(String[] args) {
        // Build a demo dfa accepting even number of binary characters or epsilon: (00 | 01 | 10 | 11)*
        Map<TransitionInput, Integer> delta = Map.of(
                new TransitionInput(0, "0"), 1,
                new TransitionInput(0, "1"), 1,
                new TransitionInput(1, "0"), 0,
                new TransitionInput(1, "1"), 0
        );


        DFA automaton = new DFA(
                Set.of(0,1),
                Set.of("0", "1"),
                delta,
                0,
                Set.of(0)
        );

        // Print automaton structure
        System.out.println(automaton);

        // Test inputs
        System.out.println(automaton.run(new String[]{"1"})); // false
        System.out.println(automaton.run(new String[]{"1", "0", "1"})); // false
        System.out.println(automaton.run(new String[]{})); // true
        System.out.println(automaton.run(new String[]{"1", "0"})); // true
        System.out.println(automaton.run(new String[]{"1", "0", "0", "1"})); // true
    }
}
