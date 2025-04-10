package automaton.utils;

public record TransitionInput(int state, String symbol) {
    @Override
    public String toString() {
        return "δ(" + state + ", " + symbol + ")";
    }
}
