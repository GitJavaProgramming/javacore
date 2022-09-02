package org.pp.net.rpc.provider;

import org.pp.net.rpc.consumer.OrderService;
import org.pp.net.rpc.registry.Reference;
import org.pp.net.rpc.registry.Service;

@Service
public class UserServiceImpl implements UserService {

    @Reference
    private OrderService orderService;

    public String say(String msg) {
        orderService.count(10);
        return "nihao:" + msg;
    }
}
