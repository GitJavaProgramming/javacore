package org.pp.lang.patterns.behavioral.memento;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 9:34
 * ************厚德载物************
 **/
public class Memento {
    private static Memento instance = null;
    private final Map<LocalDateTime, String> map = new TreeMap<>();
    private final List<Inner> mementoList = new ArrayList<>();

    private Memento() {
//        synchronized (Memento.class) {}
        if (instance != null) {
            throw new RuntimeException("实例已存在，不允许创建多个实例!");
        }
    }

    /**
     * 单例
     */
    public static Memento newInstance() {
        if (instance == null) {
            synchronized (Memento.class) {
                if (instance == null) {
                    instance = new Memento();
                }
            }
        }
        return instance;
    }

    public String getState(LocalDateTime localDateTime) {
        return map.get(localDateTime);
    }

    public String getState(int index) {
        return mementoList.get(index).state;
    }

    public void putState(String state) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "->" + state);
        map.put(now, state);
        mementoList.add(new Inner(now, state));
    }

    public Map<LocalDateTime, String> getMap() {
        return Collections.unmodifiableMap(map);
    }

    public List<Inner> getMementoList() {
        return Collections.unmodifiableList(mementoList);
    }

    public void showHistory() {
        mementoList.forEach(System.out::println);
    }

    private class Inner {
        private LocalDateTime localDateTime;
        private String state;

        public Inner(LocalDateTime localDateTime, String state) {
            this.localDateTime = localDateTime;
            this.state = state;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append(localDateTime).append("->").append(state);
            return sb.toString();
        }
    }
}
