package com.rbkmoney.scrooge.mapper;

import com.rbkmoney.damsel.shumaich.OperationLog;
import com.rbkmoney.scrooge.entity.OperationLogEntity;
import com.rbkmoney.scrooge.entity.OperationLogPk;
import com.rbkmoney.scrooge.entity.enums.OperationType;
import com.rbkmoney.scrooge.entity.enums.ValidationError;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OperationLogMapper {

    public List<OperationLogEntity> fromThriftToEntity(List<OperationLog> operationLogs) {
        return operationLogs.stream()
                .map(this::fromThriftToEntity)
                .collect(toList());
    }

    private OperationLogEntity fromThriftToEntity(OperationLog operationLog) {
        return OperationLogEntity.builder()
                .pk(OperationLogPk.builder()
                        .batchId(operationLog.getBatchId())
                        .planId(operationLog.getPlanId())
                        .planOperationsCount(operationLog.getPlanOperationsCount())
                        .sequenceId(operationLog.getSequenceId())
                        .build())
                .accountId(operationLog.getAccount().getId())
                .amountWithSign(operationLog.getAmountWithSign())
                .batchHash(operationLog.getBatchHash())
                .creationTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(operationLog.getCreationTimeMs()), ZoneOffset.UTC))
                .currencySymbolicCode(operationLog.getCurrencySymbolicCode())
                .description(operationLog.getDescription())
                .operationType(convert(operationLog.getOperationType()))
                .parentId(operationLog.getParentId())
                .spanId(operationLog.getSpanId())
                .traceId(operationLog.getTraceId())
                .validationError(convert(operationLog.getValidationError()))
                .build();
    }

    private OperationType convert(com.rbkmoney.damsel.shumaich.OperationType operationType) {
        switch (operationType) {
            case HOLD:
                return OperationType.hold;
            case COMMIT:
                return OperationType.commit;
            case ROLLBACK:
                return OperationType.rollback;
            default:
                throw new IllegalArgumentException("Unknown operationType: " + operationType);
        }
    }

    private ValidationError convert(com.rbkmoney.damsel.shumaich.ValidationError validationError) {
        switch (validationError) {
            case HOLD_NOT_EXIST:
                return ValidationError.hold_not_exist;
            default:
                throw new IllegalArgumentException("Unknown validationError: " + validationError);
        }
    }
}
