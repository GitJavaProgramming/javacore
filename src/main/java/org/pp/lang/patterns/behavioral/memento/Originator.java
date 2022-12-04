package org.pp.lang.patterns.behavioral.memento;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 9:32
 * ************厚德载物************
 **/
public class Originator {
    private String state;
    private Memento memento; // hashmap存储历史记录

    public Originator() {
        this.memento = Memento.newInstance(); // 创建一个备忘录
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        memento.putState(state); // 更新备忘录
    }

    // 从备忘录恢复
    public void restoreMemento(LocalDateTime localDateTime) {
        this.setState(memento.getState(localDateTime));
    }

    public Map<LocalDateTime, String> history() {
        return memento.getMap();
    }

    public void showHistory() {
        memento.showHistory();
    }
}
