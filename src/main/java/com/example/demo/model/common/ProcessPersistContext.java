package com.example.demo.model.common;

import lombok.Data;
import java.io.Serializable;

@Data
public class ProcessPersistContext implements Serializable {

    /**
     * Process id for resuming.
     */
    private String processId;

    /**
     * This flag means than process in running now.
     */
    private boolean isRunningNow = false;

    /**
     * This flag means than whole process was not stopped yet.
     *
     * <p>Note: process can be running now and temporary paused</p>
     */
    private boolean isWholeProcessRunning = false;

    /**
     * Current phase id.
     */
    private String currentPhaseId;

    /**
     * Context parameters.
     */
    private PersistParamsHolder params = new PersistParamsHolder();
}
