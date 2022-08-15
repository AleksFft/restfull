package com.example.demo.model.common;

import lombok.NonNull;
import java.io.Serializable;
import java.util.Optional;

public interface LockReasonType extends ClientNameable, Serializable {

    /**
     * Lock reason system name.
     *
     * @return system name
     */
    String name();

    /**
     * Find reason by system name.
     *
     * @param name system name
     * @return found reason
     */
    static Optional<LockReasonType> findByNameOptional(@NonNull String name) {
        return Optional.ofNullable(LockReasonTypeRegistry.REGISTRY.get(name));
    }

    /**
     * Find reason by system name.
     *
     * @param name system name
     * @return found reason
     */
    static LockReasonType findByName(@NonNull String name) {
        return findByNameOptional(name).orElseThrow(() -> new ReasonTypeNotFound(name));
    }

    /**
     * Create and register new reason instance.
     *
     * @param name       reason system name
     * @param clientName reason client name
     * @return created reason
     */
    static LockReasonType create(@NonNull String name, @NonNull String clientName) {
        LockReasonTypeImpl reason = new LockReasonTypeImpl(name, clientName);
        register(reason);
        return reason;
    }

    /**
     * Register reason instance.
     *
     * @param reason reason instance
     */
    static void register(@NonNull LockReasonType reason) {
        LockReasonTypeRegistry.REGISTRY.putIfAbsent(reason.name(), reason);
    }
}
