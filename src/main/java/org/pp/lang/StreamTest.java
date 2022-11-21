package org.pp.lang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class StreamTest {

    @Test
    public void testStreamMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(8, "8");
        map.put(6, "6");
        map.put(9, "9");
        map.put(4, "4");
        map.put(2, "2");
        map.put(3, "3");
        AtomicReference<Integer> num = new AtomicReference<>(0);
        map.keySet().stream().reduce(num.get(), (a, b) -> {
            if (a.compareTo(b) > 0)
                num.set(a);
            else
                num.set(b);
            return num.get();
        });
        System.out.println(num.get());
    }

    @Test
    public void testStreamSource() {
        // 通过抛出异常来阻止修改数据源！！！
        List<String> list = new ArrayList<>();
//        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(); // java.lang.UnsupportedOperationException
        list.add("4");
        list.add("1");
        list.add("2");
        list.add("6");
        list.stream().forEach(t -> {
            if (t.equals("2")) {
                list.iterator().remove(); // java.lang.IllegalStateException
            }
        });
        System.out.println(list);
    }
}
