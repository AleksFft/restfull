package com.example.demo.model;

import com.example.demo.model.common.BaseEntity;
import com.example.demo.model.common.HasPersistContext;
import com.example.demo.model.common.HasStatus;
import com.example.demo.model.common.History;
import com.example.demo.model.common.JobsConstants;
import com.example.demo.model.common.Message;
import com.example.demo.model.common.PersistParamsHolder;
import com.example.demo.model.common.ProcessPersistContext;
import com.example.demo.model.enums.TaskStatus;
import com.example.demo.model.enums.TaskType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "migrc_task")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@FieldNameConstants
@Accessors(chain = true)
public class Task extends BaseEntity implements HasPersistContext<Task>, HasStatus<TaskStatus, Task> {

    public static final String EXTERNAL_SYSTEM_ID_PARAM = "EXTERNAL_SYSTEM_ID_PARAM";
    public static final String BEGIN_DATE_PARAM = "BEGIN_DATE_PARAM";
    public static final String END_DATE_PARAM = "END_DATE_PARAM";
    public static final String START_DATE_PARAM = "START_DATE_PARAM";
    public static final String FINISH_SAVE_DATE_PARAM = "FINISH_SAVE_DATE_PARAM";
    public static final String START_PREPARE_DATE_PARAM = "START_PREPARE_DATE_PARAM";
    public static final String FINISH_PREPARE_DATE_PARAM = "FINISH_PREPARE_DATE_PARAM";
    public static final String BY_ID_UNIT_PARAM = "BY_ID_UNIT_PARAM";
    public static final String BLOCK_PARAM = "BLOCK_PARAM";
    public static final String ENABLE_PARAM = "ENABLE_PARAM";
    public static final String MIGRATED_TASK_ID = "MIGRATED_TASK_ID_PARAM";
    public static final String MASSIVE_MIGRATION = "MASSIVE_MIGRATION_PARAM";
    public static final String SKIP_DOCUMENTS = "SKIP_DOCUMENTS";
    public static final String SKIP_SYNCHRONIZATION = "SKIP_SYNCHRONIZATION";
    public static final String RECREATE_DOCUMENTS = "RECREATE_DOCUMENTS";

    /**
     * Task priority. Priority is only used for background migration tasks. All other tasks has default priority.
     */
    @Column(name = "priority")
    private int priority = JobsConstants.DEFAULT_PRIORITY;

    /**
     * Comment to task.
     */
    @Column(name = "comment")
    private String comment;

    /**
     * Task number.
     */
    @Column(name = "number", insertable = false, updatable = false)
    private Long number;

    /**
     * System name of adapter module for external system.
     */
    @Column(name = "module_system_name")
    private String systemName;

    /**
     * Migration task type.
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    /**
     * Task author info.
     */
    @Embedded
    private User user;

    /**
     * Task status.
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;

    /**
     * Change history.
     */
    @Type(type = "jsonb")
    @Column(name = "history", columnDefinition = "jsonb")
    private History history = new History();

    /**
     * Task messages.
     */
    @Type(type = "jsonb")
    @Column(name = "messages", columnDefinition = "jsonb")
    private List<Message> messages = new ArrayList<>();

    /**
     * Persist context.
     */
    @Type(type = "jsonb")
    @Column(name = "process_context", columnDefinition = "jsonb")
    private ProcessPersistContext context;

    /**
     * Unit snapshot.
     */
//    @Type(type = "jsonb")
//    @Column(name = "unit_snapshot", columnDefinition = "jsonb")
//    private UnitSnapshot unitSnapshot;
//
//    /**
//     * Product snapshot.
//     */
//    @Type(type = "jsonb")
//    @Column(name = "product_snapshot", columnDefinition = "jsonb")
//    private ProductSnapshot productSnapshot;
//
//    /**
//     * Validation options.
//     */
//    @Type(type = "jsonb")
//    @Column(name = "validation_options", columnDefinition = "jsonb")
//    private ValidationOptions validationOptions;

    /**
     * Params.
     *
     * <p>These parameters are needed for storing data which can be different for different task types and root types</p>
     */
    @Type(type = "jsonb")
    @Column(name = "params", columnDefinition = "jsonb")
    private PersistParamsHolder params = new PersistParamsHolder();
//
//    /**
//     * Save module info into parameters.
//     *
//     * @param module module
//     */
//    public void saveModuleInfo(@NonNull Module module) {
//        this.setSystemName(module.getSystemName());
//        this.getParams().writeValueByKey(Task.EXTERNAL_SYSTEM_ID_PARAM, module.getExternalSystemId());
//    }

    /**
     * Copy this task.
     *
     * @return new task
     */
    public Task copy() {
        Task newTask = new Task();
        newTask.setComment("Создана копированием задачи №" + this.getNumber());
        newTask.setType(this.getType());
        newTask.setParams(this.getParams().copy());
        newTask.setStatus(TaskStatus.NEW);
        newTask.setSystemName(this.getSystemName());
        return newTask;
    }
}
