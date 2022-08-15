package com.example.demo.model.common;

public interface HasPersistContext<H extends HasPersistContext<H>> {

    /**
     * Get context.
     *
     * @return persist context
     */
    ProcessPersistContext getContext();

    /**
     * Set new context.
     *
     * @param context new context
     */
    H setContext(ProcessPersistContext context);

    /**
     * Get priority of process. Is used for process scheduling.
     *
     * @return priority value
     */
    int getPriority();

    /**
     * Set priority of process.
     *
     * @param priority priority value
     */
    H setPriority(int priority);
}

