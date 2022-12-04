package org.pp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    /**
     * 在某数字区间内按指定概率生成随机数字
     *
     * @param min         区间最小值
     * @param max         区间最大值
     * @param splitList   每个区间段的分隔点
     * @param percentList 从每个区间段取数的概率
     * @return 生成的随机数
     */
    public static int random(int min, int max, List<Integer> splitList, List<Integer> percentList) {
        class Range {
            private final int min;
            private final int max;
            private final int percentLower;
            private final int percentUpper;

            public Range(int min, int max, int percentLower, int percentUpper) {
                this.min = min;
                this.max = max;
                this.percentLower = percentLower;
                this.percentUpper = percentUpper;
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("Range{");
                sb.append("min=").append(min);
                sb.append(", max=").append(max);
                sb.append(", percentLower=").append(percentLower);
                sb.append(", percentUpper=").append(percentUpper);
                sb.append('}');
                return sb.toString();
            }
        }
        List<Range> rangeList = new ArrayList<>();
        Integer p = 0;
        for (int i = 0; i < splitList.size(); i++) {
            Integer x = splitList.get(i);
            if (p == 0) {
                p = percentList.get(i);
                rangeList.add(new Range(min, x, min, p));
            } else {
                Range range2 = rangeList.get(i - 1);
                p += percentList.get(i);
                rangeList.add(new Range(min, x, range2.percentUpper + 1, p));
            }
            min += x;
        }
        Range range2 = rangeList.get(rangeList.size() - 1);
        rangeList.add(new Range(range2.max + 1, max, range2.percentUpper + 1, range2.percentUpper + percentList.get(percentList.size() - 1)));

//        rangeList.forEach(System.out::println);

        int x = nextInt(1, 11); // 生成[1,10]之间的随机整数
        AtomicInteger result = new AtomicInteger();
        rangeList.forEach(range -> {
            if (x >= range.percentLower && x <= range.percentUpper) {
                result.set(nextInt(range.min, range.max));
            }
        });
        return result.get();
    }

    public static int nextInt(final int startInclusive, final int endExclusive/*不包含*/) {
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }
}
