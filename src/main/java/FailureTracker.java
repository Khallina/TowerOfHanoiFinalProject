public class FailureTracker {

    private int fails;

    public FailureTracker(int fails) {
        this.fails = fails;
    }

    public int getFails() {
        return fails;
    }

    public void addFail() {
        this.fails += 1;
    }
}