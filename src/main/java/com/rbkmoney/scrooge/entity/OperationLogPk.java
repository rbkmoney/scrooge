package com.rbkmoney.scrooge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogPk implements Serializable {

    private String planId;
    private Long batchId;
    private Long sequenceId;
    private Long planOperationsCount;
}
