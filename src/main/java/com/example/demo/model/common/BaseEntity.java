package com.example.demo.model.common;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@Setter
@MappedSuperclass
@TypeDefs(
        @TypeDef(name = BaseEntity.JSONB, typeClass = JsonBinaryType.class)
)
@FieldNameConstants
public class BaseEntity {

    public static final String JSONB = "jsonb";

    public BaseEntity() {
        id = UUID.randomUUID().toString();
        modifyDate = createDate = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @Column(name = "id")
    private String id;

    @Version
    @Column(name = "opt_lock")
    private Long version;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "modify_date")
    private Timestamp modifyDate;
}
