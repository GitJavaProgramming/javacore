package org.pp.lang.patterns.behavioral.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 监听器模式
 */
public class Client {
    public static void main(String[] args) {
        // 创建事件源
        Window window = new Window();
        // 向事件源注册监听器 有多个监听接口时排队处理 可以用适配器
        window.addListener(event -> System.out.println(event.getSource()));
        // 构造事件
        Event event = new Event(window);
        // 手动触发事件 由监听器获取事件对象并调用
        window.fireAction(event);

        testJButton();
    }

    private static void testJButton() {
        SwingUtilities.invokeLater(() -> {
            // 事件源
            JButton b = new JButton("按钮");
            // 监听事件
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());
                }
            });
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setSize(new Dimension(100, 100));
            frame.add(b);
            frame.setVisible(true);
        });
    }
}
