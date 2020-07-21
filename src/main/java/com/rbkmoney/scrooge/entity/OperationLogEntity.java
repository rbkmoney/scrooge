package com.rbkmoney.scrooge.entity;

import com.rbkmoney.scrooge.entity.enums.OperationType;
import com.rbkmoney.scrooge.entity.enums.ValidationError;
import com.rbkmoney.scrooge.entity.naming.PostgresEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operation_log")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
public class OperationLogEntity implements Serializable {

    @EmbeddedId
    private OperationLogPk pk;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private OperationType operationType;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ValidationError validationError;

    private String accountId;
    private Long amountWithSign;
    private String currencySymbolicCode;
    private String description;
    private Long batchHash;
    private LocalDateTime creationTime;

    private String spanId;
    private String parentId;
    private String traceId;
}
