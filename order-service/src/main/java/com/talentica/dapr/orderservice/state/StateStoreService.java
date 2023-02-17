package com.talentica.dapr.orderservice.state;

import org.springframework.stereotype.Service;

@Service
public class StateStoreService {


    public void save(String key, Object value, int ttlInSec) {

    }

    public Object get(String key) {
        return null;
    }

    public void remove(String key) {

    }

    public void save(String key, Object value) {
        save(key, value, -1);
    }
}
