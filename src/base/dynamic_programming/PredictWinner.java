package base.dynamic_programming;


import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class PredictWinner {
    public static void main(String[] args) {
        var pv = new PredictWinner();

        var nummmmms = new int[]{1, 5, 233, 7};
        System.out.println(pv.predictTheWinner(nummmmms));
    }

    private int[][] recursiveCache;
    private int[] marks;

    //TODO make parallel with CompletableFuture
    //TODO and then ForkJoinPool and RecursiveAction/RecursiveTask<V>

    public boolean predictTheWinner(int[] nums) {
        recursiveCache = new int[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                recursiveCache[i][j] = Integer.MIN_VALUE;
            }
        }
        marks = nums;
        var res =  score(0, nums.length - 1) >= 0;
        System.out.println(Arrays.deepToString(recursiveCache));
        return res;
    }

    private int score(int start, int finish) {
        if (recursiveCache[start][finish] != Integer.MIN_VALUE) {
            return recursiveCache[start][finish];
        }
        if (finish - start == 1) {
            int res = marks[start] - marks[finish];
            res = res >= 0 ? res : -res;
            recursiveCache[start][finish] = res;
            return res;
        } else {
            int left = marks[start] - score(start + 1, finish);
            int right = marks[finish] - score(start, finish - 1);
            int res = left >= right ? left : right;
            recursiveCache[start][finish] = res;
            return res;
        }
    }
}
