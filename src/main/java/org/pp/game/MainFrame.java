package org.pp.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

/**
 * 实现小球下落功能--绘制、双缓冲
 * 按钮控制游戏进度：速度、暂停、关闭、重置...
 * 键盘来控制小球运动
 * 画图片
 */
public class MainFrame extends JFrame implements ActionListener {

    private final LogicPanel logicPanel = new LogicPanel();
    private volatile boolean pause;
    private volatile boolean stop;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        JButton b1 = new JButton("暂停");
        JButton b2 = new JButton("关闭");
        JButton b3 = new JButton("加速");
        JButton b4 = new JButton("减速");
        JButton b5 = new JButton("重置");
        b1.addActionListener(frame);
        b2.addActionListener(frame);
        b3.addActionListener(frame);
        b4.addActionListener(frame);
        b5.addActionListener(frame);
        JPanel panel = new JPanel();
        panel.setSize(500, 80);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);

        frame.setTitle("标题...");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);

        frame.getContentPane().add(frame.logicPanel, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

//        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("暂停".equals(e.getActionCommand())) {
            JButton b1 = (JButton) e.getSource();
            b1.setText("继续");
            suspend();
        } else if ("继续".equals(e.getActionCommand())) {
            JButton b1 = (JButton) e.getSource();
            b1.setText("暂停");
            resume();
        } else if ("关闭".equals(e.getActionCommand())) {
            shutdown();
        } else if ("加速".equals(e.getActionCommand())) {
            logicPanel.addLevel();
        } else if ("减速".equals(e.getActionCommand())) {
            logicPanel.subLevel();
        } else if ("重置".equals(e.getActionCommand())) {
            logicPanel.reset();
        }
    }

    private void suspend() {
        pause = true;
    }

    private void resume() {
        pause = false;
    }

    private void shutdown() {
        stop = true;
        this.setVisible(false);
        this.dispose();
    }

    class LogicPanel extends JPanel implements Runnable, KeyListener {

        private final long MAX_SPEED = 200;
        private final long MIN_SPEED = 1000;
        private int xPos = 100;
        private int yPos = 10;
        private Image iBuffer;
        private Graphics graphics;
        private long speed = MAX_SPEED;

        public LogicPanel() {
            setSize(500, 500);
            setFocusable(true);
            addKeyListener(this);
            new Thread(this).start();
        }

        public void loadImage(Graphics g) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage("E:\\code\\java\\core\\src\\main\\resources\\static\\img\\logo.jpg");
            int w = image.getWidth(this);
            int h = image.getHeight(this);
            MediaTracker mediaTracker = new MediaTracker(this);
            mediaTracker.addImage(image, 0);
            mediaTracker.checkAll(true);
            g.drawImage(image, 0, 0, w * 3, h * 3, this);
        }

        @Override
        public void paint(Graphics g) {
//            paint1(g);
            // 使用双缓冲技术绘制屏幕--保存画笔、图像
            if (iBuffer == null) {
                iBuffer = createImage(getWidth(), getHeight());
                graphics = iBuffer.getGraphics();
            }
            graphics.setColor(getBackground());
            graphics.fillRect(0, 0, getWidth(), getHeight());

            loadImage(graphics);

            graphics.setColor(Color.RED);
            graphics.fillOval(xPos, yPos, 10, 10);
            graphics.drawString("当前速度：" + (MIN_SPEED - speed), 250, 20);

            g.drawImage(iBuffer, 0, 0, this);
        }

        public void paint1(Graphics g) {
            super.paint(g); // 将面板上原来的东西擦除
            g.clearRect(0, 0, getWidth(), getHeight()); // 清屏
            g.setColor(Color.RED);
            g.fillOval(100, yPos, 10, 10);
        }


        @Override
        public void run() {
            while (!stop && !Thread.currentThread().isInterrupted()) {
                if (pause) {
                    Thread.onSpinWait();
                } else {
                    if (speed >= 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(speed);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    yPos += 20;
                    if (yPos > 460) {
                        yPos = 10;
                    }
                    repaint();
                }
            }
        }

        public void addLevel() {
            if (speed <= MIN_SPEED && speed >= MAX_SPEED + 10) {
                speed -= 10;
            }
        }

        public void subLevel() {
            if (speed <= MIN_SPEED - 10 && speed >= MAX_SPEED) {
                speed += 10;
            }
        }

        public void reset() {
            xPos = 100;
            yPos = 10;
            speed = MAX_SPEED;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    xPos -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    xPos += 10;
                    break;
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
