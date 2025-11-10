package base.dynamic_programming;

public class AsyncPredictWinnerRun {

    public static final int[] input = new int[]{66, 44, 23, 3, 5, 644, 3, 74, 534, 34, 235, 22, 4};

    public static void main(String[] args) {
        var solver = new AsyncPredictWinner(input);
        System.out.println(solver.isFirstWinner());
    }
}
