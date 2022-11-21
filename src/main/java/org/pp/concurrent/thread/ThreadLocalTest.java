package org.pp.concurrent.thread;

import org.pp.db.jdbc.User;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/17 23:06
 * ************厚德载物************
 **/
public class ThreadLocalTest {

    /**
     * 可继承的ThreadLocal
     */
    ThreadLocal<User> threadLocal2 = InheritableThreadLocal.withInitial(User::new);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Task()).start();
        }
    }

    static class Task implements Runnable {
        ThreadLocal<User> threadLocal1 = ThreadLocal.withInitial(User::new);

        @Override
        public void run() {
            User user = new User();
//            user.setId(new Random().nextInt(100));
            user.setId(100);
            m1(user);
            m2();
        }

        public void m1(User user) {
            threadLocal1.set(user);
        }

        public void m2() {
            User user = threadLocal1.get();
            user.setName(Thread.currentThread().getName());
            // 使用
            System.out.println(Thread.currentThread().getName() + " hashCode=" + user.hashCode());
            // 使用完清除
            threadLocal1.remove();
        }
    }
}
