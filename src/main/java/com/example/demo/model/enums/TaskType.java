package com.example.demo.model.enums;

import com.example.demo.model.common.ClientNameable;
import com.example.demo.model.common.LockReasonType;
import lombok.Getter;

@Getter
public enum TaskType implements ClientNameable, LockReasonType {
    PRE_MIGRATION("Задача пред миграции"),
    MIGRATION("Задача основной миграции"),
    ADDITIONAL_MIGRATION_WITH_ID("Задача домиграции по ID"),
    FULL_ROLLBACK("Задача отмены миграции"),
    PARTIAL_ROLLBACK("Задача частичной отмены миграции"),

    BRANCH_MIGRATION("Миграция подразделений"),

    BACKGROUND_MIGRATION("Фоновая миграция документов");

    TaskType(String clientName) {
        this.clientName = clientName;
        LockReasonType.register(this);
    }

    private final String clientName;

    @Override
    public String clientName() {
        return clientName;
    }
}