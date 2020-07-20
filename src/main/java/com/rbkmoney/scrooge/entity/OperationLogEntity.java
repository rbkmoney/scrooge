package com.rbkmoney.scrooge.entity;

import com.rbkmoney.scrooge.entity.enums.OperationType;
import com.rbkmoney.scrooge.entity.enums.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operation_log")
public class OperationLogEntity implements Serializable {

    @EmbeddedId
    private OperationLogPk pk;

    private OperationType operationType;
    private String accountId;
    private Long amountWithSign;
    private String currencySymbolicCode;
    private String description;
    private Long batchHash;
    private ValidationError validationError;
    private LocalDateTime creationTime;

    private String spanId;
    private String parentId;
    private String traceId;
}
