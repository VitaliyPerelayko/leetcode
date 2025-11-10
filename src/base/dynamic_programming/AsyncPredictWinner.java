package base.dynamic_programming;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AsyncPredictWinner {

    private final AtomicIntegerArray cache;
    private final int[] nums;
    private final int n;

    public AsyncPredictWinner(int[] nums) {
        n = nums.length;
        int[] baseForCache = new int[n * n];
        Arrays.fill(baseForCache, Integer.MIN_VALUE);
        cache = new AtomicIntegerArray(baseForCache);
        this.nums = nums;
    }

    public int isFirstWinner() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        var res = commonPool.invoke(new ScoreRecursiveTask(0, n - 1));

        commonPool.shutdown();

        return res;
    }

    private class ScoreRecursiveTask extends RecursiveTask<Integer> {

        private final int s;
        private final int f;

        private ScoreRecursiveTask(int s, int f) {
            this.s = s;
            this.f = f;
        }

        @Override
        protected Integer compute() {
            int index = s * n + f;

            if (cache.get(index) != Integer.MIN_VALUE) {
                return cache.get(index);
            }
            if (f - s == 1) {
                int res = nums[s] - nums[f];
                res = res >= 0 ? res : -res;
                cache.set(index, res);
                return res;
            } else {
                var rightScore = new ScoreRecursiveTask(s + 1, f);
                var leftScore = new ScoreRecursiveTask(s, f - 1);
                invokeAll(leftScore, rightScore);

                int left = nums[s] - rightScore.getRawResult();
                int right = nums[f] - leftScore.getRawResult();
                int res = left >= right ? left : right;
                cache.set(index, res);
                return res;
            }
        }
    }
}
