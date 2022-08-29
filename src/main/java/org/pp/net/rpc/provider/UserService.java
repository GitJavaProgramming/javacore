package org.pp.net.rpc.provider;

import org.pp.net.rpc.registry.Service;

@Service
public interface UserService {
    public String say(String msg);
}
