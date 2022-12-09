package org.pp.lang.patterns.behavioral.listener;

import java.util.ArrayList;
import java.util.List;

public class Window {
    private final List<ActionListener> listeners;

    public Window() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void fireAction(Event event) {
        listeners.forEach(listener -> listener.actionPerformed(event));
    }

    @Override
    public String toString() {
        return "Window";
    }
}
