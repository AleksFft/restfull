package com.example.demo.model.common;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "name")
class LockReasonTypeImpl implements LockReasonType {

    private final String name;
    private final String clientName;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String clientName() {
        return clientName;
    }
}
