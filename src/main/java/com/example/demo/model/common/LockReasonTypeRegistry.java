package com.example.demo.model.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class LockReasonTypeRegistry {
    static final Map<String, LockReasonType> REGISTRY = new ConcurrentHashMap<>();
}
