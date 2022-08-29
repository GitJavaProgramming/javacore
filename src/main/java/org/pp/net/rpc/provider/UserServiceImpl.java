package org.pp.net.rpc.provider;

import org.pp.net.rpc.registry.Service;

@Service
public class UserServiceImpl implements UserService {
    public String say(String msg) {
        return "nihao:" + msg;
    }
}
