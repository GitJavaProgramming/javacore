package org.pp.net.rpc.consumer;

import org.pp.net.rpc.registry.Service;

@Service
public interface OrderService {
    public int count(int num);
}
