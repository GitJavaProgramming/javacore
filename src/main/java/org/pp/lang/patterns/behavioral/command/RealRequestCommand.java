package org.pp.lang.patterns.behavioral.command;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 8:18
 * ************厚德载物************
 **/
public class RealRequestCommand implements Command {

    @Override
    public void execute() {
        System.out.println(Thread.currentThread().getName() + "发送真实请求...");
    }
}
